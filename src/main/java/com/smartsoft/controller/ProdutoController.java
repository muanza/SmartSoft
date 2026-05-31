package com.smartsoft.controller;

import com.smartsoft.config.TenantContext;
import com.smartsoft.dto.ApiResponse;
import com.smartsoft.dto.ProdutoRequest;
import com.smartsoft.entity.Produto;
import com.smartsoft.service.ProdutoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Produto>> criarProduto(@Valid @RequestBody ProdutoRequest request) {
        String tenantNif = TenantContext.getCurrentTenant();
        Produto produto = produtoService.criarProduto(request, tenantNif);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.sucesso("Produto criado com sucesso", produto));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<Produto>>> listarProdutos(
            @PageableDefault(size = 20) Pageable pageable) {
        String tenantNif = TenantContext.getCurrentTenant();
        Page<Produto> page = produtoService.listarProdutos(tenantNif, pageable);
        return ResponseEntity.ok(ApiResponse.sucesso("Produtos listados", page));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Produto>> buscarProduto(@PathVariable UUID id) {
        String tenantNif = TenantContext.getCurrentTenant();
        Produto produto = produtoService.buscarProduto(id, tenantNif);
        return ResponseEntity.ok(ApiResponse.sucesso("Produto encontrado", produto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Produto>> atualizarProduto(
            @PathVariable UUID id,
            @Valid @RequestBody ProdutoRequest request) {
        String tenantNif = TenantContext.getCurrentTenant();
        Produto produto = produtoService.atualizarProduto(id, request, tenantNif);
        return ResponseEntity.ok(ApiResponse.sucesso("Produto atualizado", produto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> desativarProduto(@PathVariable UUID id) {
        String tenantNif = TenantContext.getCurrentTenant();
        produtoService.desativarProduto(id, tenantNif);
        return ResponseEntity.ok(ApiResponse.sucesso("Produto desativado", null));
    }
}
