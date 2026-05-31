package com.smartsoft.controller;

import com.smartsoft.config.TenantContext;
import com.smartsoft.dto.ApiResponse;
import com.smartsoft.service.RelatorioService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/relatorios")
public class RelatorioController {

    private final RelatorioService relatorioService;

    public RelatorioController(RelatorioService relatorioService) {
        this.relatorioService = relatorioService;
    }

    @GetMapping("/vendas/diario")
    public ResponseEntity<ApiResponse<Map<String, Object>>> vendasDiarias(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
        String tenantNif = TenantContext.getCurrentTenant();
        Map<String, Object> relatorio = relatorioService.relatorioVendasDiarias(tenantNif, data);
        return ResponseEntity.ok(ApiResponse.sucesso("Relatório diário", relatorio));
    }

    @GetMapping("/vendas/periodo")
    public ResponseEntity<ApiResponse<Map<String, Object>>> vendasPeriodo(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {
        String tenantNif = TenantContext.getCurrentTenant();
        Map<String, Object> relatorio = relatorioService.relatorioVendasPeriodo(tenantNif, inicio, fim);
        return ResponseEntity.ok(ApiResponse.sucesso("Relatório por período", relatorio));
    }
}
