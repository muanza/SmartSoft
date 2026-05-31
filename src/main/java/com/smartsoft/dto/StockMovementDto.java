package com.smartsoft.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

public class StockMovementDto {
    @NotNull
    private UUID produtoId;
    @NotBlank
    private String tipoMovimento;
    @NotNull
    private BigDecimal quantidade;

    public UUID getProdutoId() { return produtoId; }
    public void setProdutoId(UUID produtoId) { this.produtoId = produtoId; }
    public String getTipoMovimento() { return tipoMovimento; }
    public void setTipoMovimento(String tipoMovimento) { this.tipoMovimento = tipoMovimento; }
    public BigDecimal getQuantidade() { return quantidade; }
    public void setQuantidade(BigDecimal quantidade) { this.quantidade = quantidade; }
}
