package com.smartsoft.service;

import com.smartsoft.dto.FacturaRequest;
import com.smartsoft.dto.FacturaResponse;
import com.smartsoft.entity.*;
import com.smartsoft.exception.BusinessException;
import com.smartsoft.exception.ResourceNotFoundException;
import com.smartsoft.repository.*;
import com.smartsoft.util.FacturaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FacturaService {

    private static final Logger log = LoggerFactory.getLogger(FacturaService.class);

    private final FacturaRepository facturaRepository;
    private final TenantRepository tenantRepository;
    private final UtilizadorRepository utilizadorRepository;
    private final CaixaRepository caixaRepository;
    private final ProdutoRepository produtoRepository;
    private final StockRepository stockRepository;
    private final ConfiguracaoEmpresaRepository configuracaoRepository;

    public FacturaService(FacturaRepository facturaRepository,
                          TenantRepository tenantRepository,
                          UtilizadorRepository utilizadorRepository,
                          CaixaRepository caixaRepository,
                          ProdutoRepository produtoRepository,
                          StockRepository stockRepository,
                          ConfiguracaoEmpresaRepository configuracaoRepository) {
        this.facturaRepository = facturaRepository;
        this.tenantRepository = tenantRepository;
        this.utilizadorRepository = utilizadorRepository;
        this.caixaRepository = caixaRepository;
        this.produtoRepository = produtoRepository;
        this.stockRepository = stockRepository;
        this.configuracaoRepository = configuracaoRepository;
    }

    @Transactional
    public FacturaResponse criarFactura(FacturaRequest request, String tenantNif, String emailUtilizador) {
        Tenant tenant = tenantRepository.findByNifAndAtivoTrue(tenantNif)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant", "nif", tenantNif));

        Utilizador utilizador = utilizadorRepository.findByEmailAndTenantNif(emailUtilizador, tenantNif)
                .orElseThrow(() -> new ResourceNotFoundException("Utilizador", "email", emailUtilizador));

        // Check daily invoice limit
        ConfiguracaoEmpresa config = configuracaoRepository.findByTenant(tenant).orElse(null);
        long countHoje = facturaRepository.countByTenantAndData(tenant, LocalDate.now());
        if (countHoje >= 1000) {
            throw new BusinessException("Limite diário de facturas atingido");
        }

        Factura factura = new Factura();
        factura.setTenant(tenant);
        factura.setUtilizador(utilizador);
        factura.setTipoFactura(request.getTipoFactura());
        factura.setClienteNome(request.getClienteNome());
        factura.setClienteNif(request.getClienteNif());
        factura.setClienteEmail(request.getClienteEmail());
        factura.setClienteTelefone(request.getClienteTelefone());
        factura.setClienteMorada(request.getClienteMorada());
        factura.setMetodoPagamento(request.getMetodoPagamento());
        factura.setObservacoes(request.getObservacoes());
        if (request.getMoeda() != null) factura.setMoeda(request.getMoeda());
        if (request.getDataFactura() != null) factura.setDataFactura(request.getDataFactura());

        if (request.getCaixaId() != null) {
            caixaRepository.findById(UUID.fromString(request.getCaixaId()))
                    .ifPresent(factura::setCaixa);
        }

        // Generate invoice number
        String numeroFactura = gerarNumeroFactura(tenant, config);
        factura.setNumeroFactura(numeroFactura);

        // Process lines
        List<LinhaFactura> linhas = new ArrayList<>();
        BigDecimal subtotal = BigDecimal.ZERO;
        BigDecimal totalIva = BigDecimal.ZERO;
        int numeroLinha = 1;

        for (FacturaRequest.LinhaFacturaRequest linhaReq : request.getLinhas()) {
            LinhaFactura linha = new LinhaFactura();
            linha.setFactura(factura);
            linha.setTenant(tenant);
            linha.setDescricaoLinha(linhaReq.getDescricaoLinha());
            linha.setQuantidade(linhaReq.getQuantidade());
            linha.setPrecoUnitario(linhaReq.getPrecoUnitario());
            linha.setNumeroSerieLinha(numeroLinha++);

            if (linhaReq.getProdutoId() != null) {
                produtoRepository.findById(UUID.fromString(linhaReq.getProdutoId()))
                        .ifPresent(p -> {
                            linha.setProduto(p);
                            if (linhaReq.getTaxaIva() == null) linha.setTaxaIva(p.getTaxaIva());
                        });
            }
            if (linhaReq.getTaxaIva() != null) linha.setTaxaIva(linhaReq.getTaxaIva());

            BigDecimal totalBruto = linhaReq.getPrecoUnitario().multiply(linhaReq.getQuantidade());
            BigDecimal iva = FacturaUtil.calcularIva(totalBruto, linha.getTaxaIva());
            linha.setMontanteIva(iva);
            linha.setTotalLinha(totalBruto.add(iva));

            subtotal = subtotal.add(totalBruto);
            totalIva = totalIva.add(iva);
            linhas.add(linha);
        }

        factura.setLinhas(linhas);
        factura.setSubtotal(subtotal);
        factura.setTotalIva(totalIva);

        // Apply discount
        BigDecimal desconto = BigDecimal.ZERO;
        if (request.getDescontoPercentual() != null && request.getDescontoPercentual().compareTo(BigDecimal.ZERO) > 0) {
            desconto = subtotal.multiply(request.getDescontoPercentual())
                    .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
            factura.setDescontoPercentual(request.getDescontoPercentual());
            factura.setDescontoMontante(desconto);
        }

        BigDecimal totalLiquido = subtotal.add(totalIva).subtract(desconto);
        factura.setTotalLiquido(totalLiquido);

        if (request.getMontantePago() != null) {
            factura.setMontantePago(request.getMontantePago());
            BigDecimal troco = request.getMontantePago().subtract(totalLiquido);
            factura.setTroco(troco.compareTo(BigDecimal.ZERO) > 0 ? troco : BigDecimal.ZERO);
            if (request.getMontantePago().compareTo(totalLiquido) >= 0) {
                factura.setStatus("paga");
            }
        }

        // Generate integrity hash
        String hashContent = numeroFactura + tenant.getNif() + totalLiquido.toString();
        factura.setHashIntegridade(FacturaUtil.gerarHashIntegridade(hashContent));

        // Update next invoice number in config
        if (config != null) {
            config.setProximoNumeroFactura(config.getProximoNumeroFactura() + 1);
            configuracaoRepository.save(config);
        }

        Factura saved = facturaRepository.save(factura);
        log.info("Factura criada: {} para tenant: {}", saved.getNumeroFactura(), tenantNif);

        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public Page<FacturaResponse> listarFacturas(String tenantNif, Pageable pageable) {
        Tenant tenant = tenantRepository.findByNifAndAtivoTrue(tenantNif)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant", "nif", tenantNif));
        return facturaRepository.findByTenantOrderByDataFacturaDesc(tenant, pageable)
                .map(this::toResponse);
    }

    @Transactional(readOnly = true)
    public FacturaResponse buscarFactura(UUID id, String tenantNif) {
        Tenant tenant = tenantRepository.findByNifAndAtivoTrue(tenantNif)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant", "nif", tenantNif));
        Factura factura = facturaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Factura", "id", id));
        if (!factura.getTenant().getIdTenant().equals(tenant.getIdTenant())) {
            throw new BusinessException("Acesso negado a esta factura");
        }
        return toResponse(factura);
    }

    @Transactional
    public FacturaResponse cancelarFactura(UUID id, String motivoCancelamento, String tenantNif) {
        Tenant tenant = tenantRepository.findByNifAndAtivoTrue(tenantNif)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant", "nif", tenantNif));
        Factura factura = facturaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Factura", "id", id));

        if (!factura.getTenant().getIdTenant().equals(tenant.getIdTenant())) {
            throw new BusinessException("Acesso negado a esta factura");
        }
        if ("cancelada".equals(factura.getStatus())) {
            throw new BusinessException("Factura já se encontra cancelada");
        }

        factura.setStatus("cancelada");
        factura.setMotivoCancelamento(motivoCancelamento);
        factura.setDataCancelamento(java.time.LocalDateTime.now());

        return toResponse(facturaRepository.save(factura));
    }

    private String gerarNumeroFactura(Tenant tenant, ConfiguracaoEmpresa config) {
        int numero = config != null ? config.getProximoNumeroFactura() : 1;
        String formato = config != null ? config.getFormatoNumeroFactura() : "NNNNNNNN/YYYY";
        return FacturaUtil.formatarNumeroFactura(numero, formato);
    }

    private FacturaResponse toResponse(Factura factura) {
        FacturaResponse resp = new FacturaResponse();
        resp.setIdFactura(factura.getIdFactura());
        resp.setNumeroFactura(factura.getNumeroFactura());
        resp.setNumeroSerie(factura.getNumeroSerie());
        resp.setDataFactura(factura.getDataFactura());
        resp.setHoraFactura(factura.getHoraFactura());
        resp.setTipoFactura(factura.getTipoFactura());
        resp.setClienteNome(factura.getClienteNome());
        resp.setClienteNif(factura.getClienteNif());
        resp.setClienteEmail(factura.getClienteEmail());
        resp.setClienteTelefone(factura.getClienteTelefone());
        resp.setClienteMorada(factura.getClienteMorada());
        resp.setSubtotal(factura.getSubtotal());
        resp.setTotalIva(factura.getTotalIva());
        resp.setDescontoPercentual(factura.getDescontoPercentual());
        resp.setDescontoMontante(factura.getDescontoMontante());
        resp.setTotalLiquido(factura.getTotalLiquido());
        resp.setMoeda(factura.getMoeda());
        resp.setMetodoPagamento(factura.getMetodoPagamento());
        resp.setMontantePago(factura.getMontantePago());
        resp.setTroco(factura.getTroco());
        resp.setStatus(factura.getStatus());
        resp.setObservacoes(factura.getObservacoes());
        resp.setDataCriacao(factura.getDataCriacao());

        if (factura.getLinhas() != null) {
            List<FacturaResponse.LinhaFacturaResponse> linhasResp = factura.getLinhas().stream().map(l -> {
                FacturaResponse.LinhaFacturaResponse lr = new FacturaResponse.LinhaFacturaResponse();
                lr.setIdLinha(l.getIdLinha());
                lr.setDescricaoLinha(l.getDescricaoLinha());
                lr.setQuantidade(l.getQuantidade());
                lr.setPrecoUnitario(l.getPrecoUnitario());
                lr.setTaxaIva(l.getTaxaIva());
                lr.setMontanteIva(l.getMontanteIva());
                lr.setTotalLinha(l.getTotalLinha());
                return lr;
            }).collect(Collectors.toList());
            resp.setLinhas(linhasResp);
        }
        return resp;
    }
}
