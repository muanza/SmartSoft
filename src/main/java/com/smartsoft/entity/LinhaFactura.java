package com.smartsoft.entity;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "linhas_factura", indexes = {
    @Index(name = "idx_linhas_factura", columnList = "id_factura"),
    @Index(name = "idx_linhas_produto", columnList = "id_produto")
})
public class LinhaFactura {

    @Id
    @GeneratedValue
    @Column(name = "id_linha", updatable = false, nullable = false)
    private UUID idLinha;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_factura", nullable = false)
    private Factura factura;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_produto")
    private Produto produto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tenant", nullable = false)
    private Tenant tenant;

    @NotBlank
    @Size(max = 255)
    @Column(name = "descricao_linha", nullable = false, length = 255)
    private String descricaoLinha;

    @NotNull
    @DecimalMin("0.01")
    @Column(name = "quantidade", nullable = false, precision = 10, scale = 2)
    private BigDecimal quantidade;

    @NotNull
    @DecimalMin("0.00")
    @Column(name = "preco_unitario", nullable = false, precision = 12, scale = 2)
    private BigDecimal precoUnitario;

    @Pattern(regexp = "0%|7%|14%|17%")
    @Column(name = "taxa_iva", length = 10, columnDefinition = "VARCHAR(10) DEFAULT '17%'")
    private String taxaIva = "17%";

    @NotNull
    @DecimalMin("0.00")
    @Column(name = "montante_iva", nullable = false, precision = 12, scale = 2, columnDefinition = "DECIMAL(12,2) DEFAULT 0.00")
    private BigDecimal montanteIva = BigDecimal.ZERO;

    @NotNull
    @DecimalMin("0.00")
    @Column(name = "total_linha", nullable = false, precision = 12, scale = 2)
    private BigDecimal totalLinha;

    @Min(1)
    @Column(name = "numero_serie_linha")
    private Integer numeroSerieLinha;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @PrePersist
    protected void onCreate() {
        dataCriacao = LocalDateTime.now();
    }

    public LinhaFactura() {}

    public UUID getIdLinha() { return idLinha; }
    public void setIdLinha(UUID idLinha) { this.idLinha = idLinha; }

    public Factura getFactura() { return factura; }
    public void setFactura(Factura factura) { this.factura = factura; }

    public Produto getProduto() { return produto; }
    public void setProduto(Produto produto) { this.produto = produto; }

    public Tenant getTenant() { return tenant; }
    public void setTenant(Tenant tenant) { this.tenant = tenant; }

    public String getDescricaoLinha() { return descricaoLinha; }
    public void setDescricaoLinha(String descricaoLinha) { this.descricaoLinha = descricaoLinha; }

    public BigDecimal getQuantidade() { return quantidade; }
    public void setQuantidade(BigDecimal quantidade) { this.quantidade = quantidade; }

    public BigDecimal getPrecoUnitario() { return precoUnitario; }
    public void setPrecoUnitario(BigDecimal precoUnitario) { this.precoUnitario = precoUnitario; }

    public String getTaxaIva() { return taxaIva; }
    public void setTaxaIva(String taxaIva) { this.taxaIva = taxaIva; }

    public BigDecimal getMontanteIva() { return montanteIva; }
    public void setMontanteIva(BigDecimal montanteIva) { this.montanteIva = montanteIva; }

    public BigDecimal getTotalLinha() { return totalLinha; }
    public void setTotalLinha(BigDecimal totalLinha) { this.totalLinha = totalLinha; }

    public Integer getNumeroSerieLinha() { return numeroSerieLinha; }
    public void setNumeroSerieLinha(Integer numeroSerieLinha) { this.numeroSerieLinha = numeroSerieLinha; }

    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LinhaFactura that = (LinhaFactura) o;
        return Objects.equals(idLinha, that.idLinha);
    }

    @Override
    public int hashCode() { return Objects.hash(idLinha); }

    @Override
    public String toString() {
        return "LinhaFactura{idLinha=" + idLinha + ", descricaoLinha='" + descricaoLinha + "', totalLinha=" + totalLinha + "}";
    }
}
