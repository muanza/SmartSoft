package com.smartsoft.controller;

import com.smartsoft.config.TenantContext;
import com.smartsoft.dto.ApiResponse;
import com.smartsoft.dto.PagamentoRequest;
import com.smartsoft.entity.PagamentoFactura;
import com.smartsoft.service.PagamentoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/facturas/{idFactura}/pagamentos")
public class PagamentoController {

    private final PagamentoService pagamentoService;

    public PagamentoController(PagamentoService pagamentoService) {
        this.pagamentoService = pagamentoService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<PagamentoFactura>> registarPagamento(
            @PathVariable UUID idFactura,
            @Valid @RequestBody PagamentoRequest request,
            @AuthenticationPrincipal String email) {
        String tenantNif = TenantContext.getCurrentTenant();
        PagamentoFactura pagamento = pagamentoService.registarPagamento(idFactura, request, tenantNif, email);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.sucesso("Pagamento registado com sucesso", pagamento));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PagamentoFactura>>> listarPagamentos(
            @PathVariable UUID idFactura) {
        String tenantNif = TenantContext.getCurrentTenant();
        List<PagamentoFactura> pagamentos = pagamentoService.listarPagamentos(idFactura, tenantNif);
        return ResponseEntity.ok(ApiResponse.sucesso("Pagamentos listados", pagamentos));
    }
}
