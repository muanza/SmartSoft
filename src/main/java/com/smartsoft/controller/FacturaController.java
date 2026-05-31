package com.smartsoft.controller;

import com.smartsoft.config.TenantContext;
import com.smartsoft.dto.ApiResponse;
import com.smartsoft.dto.FacturaRequest;
import com.smartsoft.dto.FacturaResponse;
import com.smartsoft.service.FacturaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/facturas")
public class FacturaController {

    private final FacturaService facturaService;

    public FacturaController(FacturaService facturaService) {
        this.facturaService = facturaService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<FacturaResponse>> criarFactura(
            @Valid @RequestBody FacturaRequest request,
            @AuthenticationPrincipal String email) {

        String tenantNif = TenantContext.getCurrentTenant();
        FacturaResponse response = facturaService.criarFactura(request, tenantNif, email);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.sucesso("Factura criada com sucesso", response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<FacturaResponse>>> listarFacturas(
            @PageableDefault(size = 20) Pageable pageable) {

        String tenantNif = TenantContext.getCurrentTenant();
        Page<FacturaResponse> page = facturaService.listarFacturas(tenantNif, pageable);
        return ResponseEntity.ok(ApiResponse.sucesso("Facturas listadas", page));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<FacturaResponse>> buscarFactura(@PathVariable UUID id) {
        String tenantNif = TenantContext.getCurrentTenant();
        FacturaResponse response = facturaService.buscarFactura(id, tenantNif);
        return ResponseEntity.ok(ApiResponse.sucesso("Factura encontrada", response));
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<ApiResponse<FacturaResponse>> cancelarFactura(
            @PathVariable UUID id,
            @RequestBody Map<String, String> body) {

        String tenantNif = TenantContext.getCurrentTenant();
        String motivo = body.getOrDefault("motivo", "Cancelamento solicitado pelo utilizador");
        FacturaResponse response = facturaService.cancelarFactura(id, motivo, tenantNif);
        return ResponseEntity.ok(ApiResponse.sucesso("Factura cancelada", response));
    }
}
