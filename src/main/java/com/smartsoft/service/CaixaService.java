package com.smartsoft.service;

import com.smartsoft.dto.AbrirCaixaRequest;
import com.smartsoft.dto.FecharCaixaRequest;
import com.smartsoft.dto.MovimentoCaixaRequest;
import com.smartsoft.entity.Caixa;
import com.smartsoft.entity.MovimentoCaixa;
import com.smartsoft.entity.Tenant;
import com.smartsoft.entity.Utilizador;
import com.smartsoft.exception.BusinessException;
import com.smartsoft.exception.ResourceNotFoundException;
import com.smartsoft.repository.CaixaRepository;
import com.smartsoft.repository.TenantRepository;
import com.smartsoft.repository.UtilizadorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class CaixaService {

    private static final Logger log = LoggerFactory.getLogger(CaixaService.class);

    private final CaixaRepository caixaRepository;
    private final TenantRepository tenantRepository;
    private final UtilizadorRepository utilizadorRepository;

    public CaixaService(CaixaRepository caixaRepository,
                        TenantRepository tenantRepository,
                        UtilizadorRepository utilizadorRepository) {
        this.caixaRepository = caixaRepository;
        this.tenantRepository = tenantRepository;
        this.utilizadorRepository = utilizadorRepository;
    }

    @Transactional
    public Caixa abrirCaixa(AbrirCaixaRequest request, String tenantNif, String emailUtilizador) {
        Tenant tenant = tenantRepository.findByNifAndAtivoTrue(tenantNif)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant", "nif", tenantNif));

        Utilizador utilizador = utilizadorRepository.findByEmailAndTenantNif(emailUtilizador, tenantNif)
                .orElseThrow(() -> new ResourceNotFoundException("Utilizador", "email", emailUtilizador));

        Optional<Caixa> caixaAberta = caixaRepository.findCaixaAbertaByTenant(tenant);
        if (caixaAberta.isPresent()) {
            throw new BusinessException("Já existe uma caixa aberta para este tenant");
        }

        Caixa caixa = new Caixa();
        caixa.setTenant(tenant);
        caixa.setUtilizador(utilizador);
        caixa.setDataAbertura(LocalDateTime.now());
        caixa.setSaldoInicial(request.getSaldoInicial() != null ? request.getSaldoInicial() : BigDecimal.ZERO);
        caixa.setStatus("aberta");
        caixa.setNumeroCaixa(gerarNumeroCaixa(tenant));

        if (request.getObservacoes() != null) caixa.setObservacoes(request.getObservacoes());

        Caixa saved = caixaRepository.save(caixa);
        log.info("Caixa aberta: {} para tenant: {}", saved.getIdCaixa(), tenantNif);
        return saved;
    }

    @Transactional
    public Caixa fecharCaixa(UUID idCaixa, FecharCaixaRequest request, String tenantNif) {
        Caixa caixa = caixaRepository.findById(idCaixa)
                .orElseThrow(() -> new ResourceNotFoundException("Caixa", "id", idCaixa));
        verificarTenant(caixa.getTenant(), tenantNif);

        if (!"aberta".equals(caixa.getStatus())) {
            throw new BusinessException("Esta caixa não está aberta");
        }

        caixa.setStatus("fechada");
        caixa.setDataFecho(LocalDateTime.now());
        if (request.getSaldoFinal() != null) caixa.setSaldoFinal(request.getSaldoFinal());
        if (request.getObservacoes() != null) caixa.setObservacoes(request.getObservacoes());

        log.info("Caixa fechada: {} para tenant: {}", idCaixa, tenantNif);
        return caixaRepository.save(caixa);
    }

    @Transactional
    public MovimentoCaixa registarMovimento(UUID idCaixa, MovimentoCaixaRequest request, String tenantNif) {
        Caixa caixa = caixaRepository.findById(idCaixa)
                .orElseThrow(() -> new ResourceNotFoundException("Caixa", "id", idCaixa));
        verificarTenant(caixa.getTenant(), tenantNif);

        if (!"aberta".equals(caixa.getStatus())) {
            throw new BusinessException("Não é possível registar movimentos numa caixa fechada");
        }

        MovimentoCaixa movimento = new MovimentoCaixa();
        movimento.setCaixa(caixa);
        movimento.setTenant(caixa.getTenant());
        movimento.setTipoMovimento(request.getTipoMovimento());
        movimento.setMontante(request.getMontante());
        movimento.setDescricao(request.getDescricao());
        movimento.setDataMovimento(LocalDateTime.now());

        if (caixa.getMovimentos() == null) caixa.setMovimentos(new java.util.ArrayList<>());
        caixa.getMovimentos().add(movimento);

        caixaRepository.save(caixa);
        return movimento;
    }

    @Transactional(readOnly = true)
    public Optional<Caixa> buscarCaixaAberta(String tenantNif) {
        Tenant tenant = tenantRepository.findByNifAndAtivoTrue(tenantNif)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant", "nif", tenantNif));
        return caixaRepository.findCaixaAbertaByTenant(tenant);
    }

    private void verificarTenant(Tenant caixaTenant, String tenantNif) {
        if (!caixaTenant.getNif().equals(tenantNif)) {
            throw new BusinessException("Acesso negado a esta caixa");
        }
    }

    private String gerarNumeroCaixa(Tenant tenant) {
        String data = LocalDate.now().toString().replace("-", "");
        return "CX-" + tenant.getNif() + "-" + data + "-" + UUID.randomUUID().toString().substring(0, 4).toUpperCase();
    }
}
