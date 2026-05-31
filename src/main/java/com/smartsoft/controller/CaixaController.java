package com.smartsoft.controller;

import com.smartsoft.config.TenantContext;
import com.smartsoft.dto.*;
import com.smartsoft.entity.Caixa;
import com.smartsoft.entity.MovimentoCaixa;
import com.smartsoft.service.CaixaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/caixa")
public class CaixaController {

    private final CaixaService caixaService;

    public CaixaController(CaixaService caixaService) {
        this.caixaService = caixaService;
    }

    @PostMapping("/abrir")
    public ResponseEntity<ApiResponse<Caixa>> abrirCaixa(
            @Valid @RequestBody AbrirCaixaRequest request,
            @AuthenticationPrincipal String email) {
        String tenantNif = TenantContext.getCurrentTenant();
        Caixa caixa = caixaService.abrirCaixa(request, tenantNif, email);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.sucesso("Caixa aberta com sucesso", caixa));
    }

    @PatchMapping("/{id}/fechar")
    public ResponseEntity<ApiResponse<Caixa>> fecharCaixa(
            @PathVariable UUID id,
            @Valid @RequestBody FecharCaixaRequest request) {
        String tenantNif = TenantContext.getCurrentTenant();
        Caixa caixa = caixaService.fecharCaixa(id, request, tenantNif);
        return ResponseEntity.ok(ApiResponse.sucesso("Caixa fechada com sucesso", caixa));
    }

    @PostMapping("/{id}/movimentos")
    public ResponseEntity<ApiResponse<MovimentoCaixa>> registarMovimento(
            @PathVariable UUID id,
            @Valid @RequestBody MovimentoCaixaRequest request) {
        String tenantNif = TenantContext.getCurrentTenant();
        MovimentoCaixa movimento = caixaService.registarMovimento(id, request, tenantNif);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.sucesso("Movimento registado com sucesso", movimento));
    }

    @GetMapping("/aberta")
    public ResponseEntity<ApiResponse<Caixa>> caixaAberta() {
        String tenantNif = TenantContext.getCurrentTenant();
        return caixaService.buscarCaixaAberta(tenantNif)
                .map(c -> ResponseEntity.ok(ApiResponse.sucesso("Caixa aberta encontrada", c)))
                .orElse(ResponseEntity.ok(ApiResponse.sucesso("Não há caixa aberta", null)));
    }
}
