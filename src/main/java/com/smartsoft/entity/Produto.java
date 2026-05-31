package com.smartsoft.entity;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "produtos",
    uniqueConstraints = @UniqueConstraint(columnNames = {"id_tenant", "codigo_produto"}),
    indexes = {
        @Index(name = "idx_produtos_tenant", columnList = "id_tenant"),
        @Index(name = "idx_produtos_categoria", columnList = "id_categoria"),
        @Index(name = "idx_produtos_codigo", columnList = "codigo_produto"),
        @Index(name = "idx_produtos_nome", columnList = "nome_produto")
    })
public class Produto {

    @Id
    @GeneratedValue
    @Column(name = "id_produto", updatable = false, nullable = false)
    private UUID idProduto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tenant", nullable = false)
    private Tenant tenant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_categoria", nullable = false)
    private CategoriaProduto categoria;

    @NotBlank
    @Size(max = 50)
    @Column(name = "codigo_produto", nullable = false, length = 50)
    private String codigoProduto;

    @NotBlank
    @Size(max = 255)
    @Column(name = "nome_produto", nullable = false)
    private String nomeProduto;

    @Column(name = "descricao", columnDefinition = "TEXT")
    private String descricao;

    @DecimalMin("0.00")
    @Column(name = "preco_custo", precision = 12, scale = 2)
    private BigDecimal precoCusto;

    @NotNull
    @DecimalMin("0.01")
    @Column(name = "preco_venda", nullable = false, precision = 12, scale = 2)
    private BigDecimal precoVenda;

    @Pattern(regexp = "0%|7%|14%|17%")
    @Column(name = "taxa_iva", length = 10, columnDefinition = "VARCHAR(10) DEFAULT '17%'")
    private String taxaIva = "17%";

    @Size(max = 20)
    @Column(name = "unidade_medida", length = 20, columnDefinition = "VARCHAR(20) DEFAULT 'unidade'")
    private String unidadeMedida = "unidade";

    @Column(name = "imagem")
    private byte[] imagem;

    @Size(max = 50)
    @Column(name = "sku", length = 50)
    private String sku;

    @Size(max = 100)
    @Column(name = "codigo_barras", length = 100)
    private String codigoBarras;

    @Column(name = "ativo", columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean ativo = true;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    @PrePersist
    protected void onCreate() {
        dataCriacao = LocalDateTime.now();
        dataAtualizacao = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        dataAtualizacao = LocalDateTime.now();
    }

    public Produto() {}

    public UUID getIdProduto() { return idProduto; }
    public void setIdProduto(UUID idProduto) { this.idProduto = idProduto; }

    public Tenant getTenant() { return tenant; }
    public void setTenant(Tenant tenant) { this.tenant = tenant; }

    public CategoriaProduto getCategoria() { return categoria; }
    public void setCategoria(CategoriaProduto categoria) { this.categoria = categoria; }

    public String getCodigoProduto() { return codigoProduto; }
    public void setCodigoProduto(String codigoProduto) { this.codigoProduto = codigoProduto; }

    public String getNomeProduto() { return nomeProduto; }
    public void setNomeProduto(String nomeProduto) { this.nomeProduto = nomeProduto; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public BigDecimal getPrecoCusto() { return precoCusto; }
    public void setPrecoCusto(BigDecimal precoCusto) { this.precoCusto = precoCusto; }

    public BigDecimal getPrecoVenda() { return precoVenda; }
    public void setPrecoVenda(BigDecimal precoVenda) { this.precoVenda = precoVenda; }

    public String getTaxaIva() { return taxaIva; }
    public void setTaxaIva(String taxaIva) { this.taxaIva = taxaIva; }

    public String getUnidadeMedida() { return unidadeMedida; }
    public void setUnidadeMedida(String unidadeMedida) { this.unidadeMedida = unidadeMedida; }

    public byte[] getImagem() { return imagem; }
    public void setImagem(byte[] imagem) { this.imagem = imagem; }

    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }

    public String getCodigoBarras() { return codigoBarras; }
    public void setCodigoBarras(String codigoBarras) { this.codigoBarras = codigoBarras; }

    public Boolean getAtivo() { return ativo; }
    public void setAtivo(Boolean ativo) { this.ativo = ativo; }

    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }

    public LocalDateTime getDataAtualizacao() { return dataAtualizacao; }
    public void setDataAtualizacao(LocalDateTime dataAtualizacao) { this.dataAtualizacao = dataAtualizacao; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Produto produto = (Produto) o;
        return Objects.equals(idProduto, produto.idProduto);
    }

    @Override
    public int hashCode() { return Objects.hash(idProduto); }

    @Override
    public String toString() {
        return "Produto{idProduto=" + idProduto + ", codigoProduto='" + codigoProduto + "', nomeProduto='" + nomeProduto + "'}";
    }
}
