package com.smartsoft.controller;

import com.smartsoft.config.TenantContext;
import com.smartsoft.dto.ApiResponse;
import com.smartsoft.dto.AlterarSenhaRequest;
import com.smartsoft.dto.UtilizadorRequest;
import com.smartsoft.entity.Utilizador;
import com.smartsoft.service.UtilizadorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/utilizadores")
public class UtilizadorController {

    private final UtilizadorService utilizadorService;

    public UtilizadorController(UtilizadorService utilizadorService) {
        this.utilizadorService = utilizadorService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Utilizador>> criarUtilizador(
            @Valid @RequestBody UtilizadorRequest request) {
        String tenantNif = TenantContext.getCurrentTenant();
        Utilizador u = utilizadorService.criarUtilizador(request, tenantNif);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.sucesso("Utilizador criado com sucesso", u));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Utilizador>>> listarUtilizadores() {
        String tenantNif = TenantContext.getCurrentTenant();
        List<Utilizador> lista = utilizadorService.listarUtilizadores(tenantNif);
        return ResponseEntity.ok(ApiResponse.sucesso("Utilizadores listados", lista));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Utilizador>> buscarUtilizador(@PathVariable UUID id) {
        String tenantNif = TenantContext.getCurrentTenant();
        Utilizador u = utilizadorService.buscarUtilizador(id, tenantNif);
        return ResponseEntity.ok(ApiResponse.sucesso("Utilizador encontrado", u));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Utilizador>> atualizarUtilizador(
            @PathVariable UUID id,
            @Valid @RequestBody UtilizadorRequest request) {
        String tenantNif = TenantContext.getCurrentTenant();
        Utilizador u = utilizadorService.atualizarUtilizador(id, request, tenantNif);
        return ResponseEntity.ok(ApiResponse.sucesso("Utilizador atualizado", u));
    }

    @PatchMapping("/{id}/alterar-senha")
    public ResponseEntity<ApiResponse<Void>> alterarSenha(
            @PathVariable UUID id,
            @Valid @RequestBody AlterarSenhaRequest request) {
        String tenantNif = TenantContext.getCurrentTenant();
        utilizadorService.alterarSenha(id, request.getSenhaAtual(), request.getNovaSenha(), tenantNif);
        return ResponseEntity.ok(ApiResponse.sucesso("Senha alterada com sucesso", null));
    }

    @PatchMapping("/{id}/desbloquear")
    public ResponseEntity<ApiResponse<Void>> desbloquearUtilizador(@PathVariable UUID id) {
        String tenantNif = TenantContext.getCurrentTenant();
        utilizadorService.desbloquearUtilizador(id, tenantNif);
        return ResponseEntity.ok(ApiResponse.sucesso("Utilizador desbloqueado", null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> desativarUtilizador(@PathVariable UUID id) {
        String tenantNif = TenantContext.getCurrentTenant();
        utilizadorService.desativarUtilizador(id, tenantNif);
        return ResponseEntity.ok(ApiResponse.sucesso("Utilizador desativado", null));
    }
}
