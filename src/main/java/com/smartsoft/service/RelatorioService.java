package com.smartsoft.service;

import com.smartsoft.entity.Tenant;
import com.smartsoft.exception.BusinessException;
import com.smartsoft.exception.ResourceNotFoundException;
import com.smartsoft.repository.FacturaRepository;
import com.smartsoft.repository.TenantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
public class RelatorioService {

    private final FacturaRepository facturaRepository;
    private final TenantRepository tenantRepository;

    public RelatorioService(FacturaRepository facturaRepository,
                             TenantRepository tenantRepository) {
        this.facturaRepository = facturaRepository;
        this.tenantRepository = tenantRepository;
    }

    @Transactional(readOnly = true)
    public Map<String, Object> relatorioVendasDiarias(String tenantNif, LocalDate data) {
        Tenant tenant = tenantRepository.findByNifAndAtivoTrue(tenantNif)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant", "nif", tenantNif));

        LocalDate dataConsulta = data != null ? data : LocalDate.now();

        long totalFacturas = facturaRepository.countByTenantAndData(tenant, dataConsulta);
        BigDecimal totalVendas = facturaRepository.sumTotalLiquidoByTenantAndData(tenant, dataConsulta);

        Map<String, Object> relatorio = new HashMap<>();
        relatorio.put("data", dataConsulta.toString());
        relatorio.put("totalFacturas", totalFacturas);
        relatorio.put("totalVendas", totalVendas != null ? totalVendas : BigDecimal.ZERO);
        relatorio.put("tenantNif", tenantNif);
        return relatorio;
    }

    @Transactional(readOnly = true)
    public Map<String, Object> relatorioVendasPeriodo(String tenantNif, LocalDate inicio, LocalDate fim) {
        Tenant tenant = tenantRepository.findByNifAndAtivoTrue(tenantNif)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant", "nif", tenantNif));

        if (inicio == null) inicio = LocalDate.now().withDayOfMonth(1);
        if (fim == null) fim = LocalDate.now();
        if (inicio.isAfter(fim)) throw new BusinessException("Data de início deve ser anterior à data de fim");

        BigDecimal totalVendas = facturaRepository.sumTotalLiquidoByTenantAndPeriodo(tenant, inicio, fim);
        long totalFacturas = facturaRepository.countByTenantAndPeriodo(tenant, inicio, fim);

        Map<String, Object> relatorio = new HashMap<>();
        relatorio.put("dataInicio", inicio.toString());
        relatorio.put("dataFim", fim.toString());
        relatorio.put("totalFacturas", totalFacturas);
        relatorio.put("totalVendas", totalVendas != null ? totalVendas : BigDecimal.ZERO);
        relatorio.put("tenantNif", tenantNif);
        return relatorio;
    }
}
