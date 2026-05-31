package com.smartsoft.service;

import com.smartsoft.dto.PagamentoRequest;
import com.smartsoft.entity.Factura;
import com.smartsoft.entity.PagamentoFactura;
import com.smartsoft.entity.Tenant;
import com.smartsoft.entity.Utilizador;
import com.smartsoft.exception.BusinessException;
import com.smartsoft.exception.ResourceNotFoundException;
import com.smartsoft.repository.FacturaRepository;
import com.smartsoft.repository.TenantRepository;
import com.smartsoft.repository.UtilizadorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class PagamentoService {

    private final FacturaRepository facturaRepository;
    private final TenantRepository tenantRepository;
    private final UtilizadorRepository utilizadorRepository;

    public PagamentoService(FacturaRepository facturaRepository,
                             TenantRepository tenantRepository,
                             UtilizadorRepository utilizadorRepository) {
        this.facturaRepository = facturaRepository;
        this.tenantRepository = tenantRepository;
        this.utilizadorRepository = utilizadorRepository;
    }

    @Transactional
    public PagamentoFactura registarPagamento(UUID idFactura, PagamentoRequest request,
                                               String tenantNif, String emailUtilizador) {
        Tenant tenant = tenantRepository.findByNifAndAtivoTrue(tenantNif)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant", "nif", tenantNif));

        Factura factura = facturaRepository.findById(idFactura)
                .orElseThrow(() -> new ResourceNotFoundException("Factura", "id", idFactura));

        if (!factura.getTenant().getIdTenant().equals(tenant.getIdTenant())) {
            throw new BusinessException("Acesso negado a esta factura");
        }
        if ("cancelada".equals(factura.getStatus())) {
            throw new BusinessException("Não é possível registar pagamento numa factura cancelada");
        }

        Utilizador utilizador = utilizadorRepository.findByEmailAndTenantNif(emailUtilizador, tenantNif)
                .orElse(null);
        // utilizador is fetched for audit purposes; not stored on PagamentoFactura entity directly

        PagamentoFactura pagamento = new PagamentoFactura();
        pagamento.setFactura(factura);
        pagamento.setTenant(tenant);
        pagamento.setMontantePagamento(request.getMontantePagamento());
        pagamento.setMetodoPagamento(request.getMetodoPagamento());
        pagamento.setReferenciaPagamento(request.getReferenciaPagamento());
        pagamento.setDataPagamento(LocalDateTime.now());

        if (factura.getPagamentos() == null) factura.setPagamentos(new java.util.ArrayList<>());
        factura.getPagamentos().add(pagamento);

        // Update total paid
        BigDecimal totalPago = factura.getPagamentos().stream()
                .map(PagamentoFactura::getMontantePagamento)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        factura.setMontantePago(totalPago);
        if (totalPago.compareTo(factura.getTotalLiquido()) >= 0) {
            factura.setStatus("paga");
        }

        facturaRepository.save(factura);
        return pagamento;
    }

    @Transactional(readOnly = true)
    public List<PagamentoFactura> listarPagamentos(UUID idFactura, String tenantNif) {
        Tenant tenant = tenantRepository.findByNifAndAtivoTrue(tenantNif)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant", "nif", tenantNif));
        Factura factura = facturaRepository.findById(idFactura)
                .orElseThrow(() -> new ResourceNotFoundException("Factura", "id", idFactura));
        if (!factura.getTenant().getIdTenant().equals(tenant.getIdTenant())) {
            throw new BusinessException("Acesso negado a esta factura");
        }
        return factura.getPagamentos();
    }
}
