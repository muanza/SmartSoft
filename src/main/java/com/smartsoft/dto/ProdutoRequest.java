package com.smartsoft.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

public class ProdutoRequest implements Serializable {

    @NotBlank
    @Size(max = 50)
    private String codigoProduto;

    @NotBlank
    @Size(max = 255)
    private String nomeProduto;

    private String descricao;

    @DecimalMin("0.00")
    private java.math.BigDecimal precoCusto;

    @NotNull
    @DecimalMin("0.01")
    private java.math.BigDecimal precoVenda;

    @Pattern(regexp = "0%|7%|14%|17%")
    private String taxaIva = "17%";

    @Size(max = 20)
    private String unidadeMedida = "unidade";

    @Size(max = 50)
    private String sku;

    @Size(max = 100)
    private String codigoBarras;

    @Size(max = 36)
    private String categoriaId;

    public ProdutoRequest() {}

    public String getCodigoProduto() { return codigoProduto; }
    public void setCodigoProduto(String codigoProduto) { this.codigoProduto = codigoProduto; }

    public String getNomeProduto() { return nomeProduto; }
    public void setNomeProduto(String nomeProduto) { this.nomeProduto = nomeProduto; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public java.math.BigDecimal getPrecoCusto() { return precoCusto; }
    public void setPrecoCusto(java.math.BigDecimal precoCusto) { this.precoCusto = precoCusto; }

    public java.math.BigDecimal getPrecoVenda() { return precoVenda; }
    public void setPrecoVenda(java.math.BigDecimal precoVenda) { this.precoVenda = precoVenda; }

    public String getTaxaIva() { return taxaIva; }
    public void setTaxaIva(String taxaIva) { this.taxaIva = taxaIva; }

    public String getUnidadeMedida() { return unidadeMedida; }
    public void setUnidadeMedida(String unidadeMedida) { this.unidadeMedida = unidadeMedida; }

    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }

    public String getCodigoBarras() { return codigoBarras; }
    public void setCodigoBarras(String codigoBarras) { this.codigoBarras = codigoBarras; }

    public String getCategoriaId() { return categoriaId; }
    public void setCategoriaId(String categoriaId) { this.categoriaId = categoriaId; }
}
