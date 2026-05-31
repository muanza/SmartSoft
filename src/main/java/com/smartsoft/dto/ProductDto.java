package com.smartsoft.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

public class ProductDto {
    @NotBlank
    private String nomeProduto;
    @NotBlank
    private String codigoProduto;
    @NotNull
    private UUID categoriaId;
    @NotNull @Min(0)
    private BigDecimal precoUnitario;

    public String getNomeProduto() { return nomeProduto; }
    public void setNomeProduto(String nomeProduto) { this.nomeProduto = nomeProduto; }
    public String getCodigoProduto() { return codigoProduto; }
    public void setCodigoProduto(String codigoProduto) { this.codigoProduto = codigoProduto; }
    public UUID getCategoriaId() { return categoriaId; }
    public void setCategoriaId(UUID categoriaId) { this.categoriaId = categoriaId; }
    public BigDecimal getPrecoUnitario() { return precoUnitario; }
    public void setPrecoUnitario(BigDecimal precoUnitario) { this.precoUnitario = precoUnitario; }
}
