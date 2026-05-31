package com.smartsoft.entity;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "stock",
    uniqueConstraints = @UniqueConstraint(columnNames = {"id_tenant", "id_produto"}),
    indexes = {
        @Index(name = "idx_stock_tenant", columnList = "id_tenant"),
        @Index(name = "idx_stock_produto", columnList = "id_produto")
    })
public class Stock {

    @Id
    @GeneratedValue
    @Column(name = "id_stock", updatable = false, nullable = false)
    private UUID idStock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_produto", nullable = false)
    private Produto produto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tenant", nullable = false)
    private Tenant tenant;

    @NotNull
    @Min(0)
    @Column(name = "quantidade_atual", nullable = false, columnDefinition = "INT DEFAULT 0")
    private Integer quantidadeAtual = 0;

    @Min(0)
    @Column(name = "quantidade_minima", columnDefinition = "INT DEFAULT 10")
    private Integer quantidadeMinima = 10;

    @Min(0)
    @Column(name = "quantidade_maxima", columnDefinition = "INT DEFAULT 1000")
    private Integer quantidadeMaxima = 1000;

    @Column(name = "data_ultima_contagem")
    private LocalDateTime dataUltimaContagem;

    @Size(max = 100)
    @Column(name = "localizacao_armazem", length = 100)
    private String localizacaoArmazem;

    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    @PrePersist
    @PreUpdate
    protected void onUpdate() {
        dataAtualizacao = LocalDateTime.now();
    }

    public Stock() {}

    public UUID getIdStock() { return idStock; }
    public void setIdStock(UUID idStock) { this.idStock = idStock; }

    public Produto getProduto() { return produto; }
    public void setProduto(Produto produto) { this.produto = produto; }

    public Tenant getTenant() { return tenant; }
    public void setTenant(Tenant tenant) { this.tenant = tenant; }

    public Integer getQuantidadeAtual() { return quantidadeAtual; }
    public void setQuantidadeAtual(Integer quantidadeAtual) { this.quantidadeAtual = quantidadeAtual; }

    public Integer getQuantidadeMinima() { return quantidadeMinima; }
    public void setQuantidadeMinima(Integer quantidadeMinima) { this.quantidadeMinima = quantidadeMinima; }

    public Integer getQuantidadeMaxima() { return quantidadeMaxima; }
    public void setQuantidadeMaxima(Integer quantidadeMaxima) { this.quantidadeMaxima = quantidadeMaxima; }

    public LocalDateTime getDataUltimaContagem() { return dataUltimaContagem; }
    public void setDataUltimaContagem(LocalDateTime dataUltimaContagem) { this.dataUltimaContagem = dataUltimaContagem; }

    public String getLocalizacaoArmazem() { return localizacaoArmazem; }
    public void setLocalizacaoArmazem(String localizacaoArmazem) { this.localizacaoArmazem = localizacaoArmazem; }

    public LocalDateTime getDataAtualizacao() { return dataAtualizacao; }
    public void setDataAtualizacao(LocalDateTime dataAtualizacao) { this.dataAtualizacao = dataAtualizacao; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stock stock = (Stock) o;
        return Objects.equals(idStock, stock.idStock);
    }

    @Override
    public int hashCode() { return Objects.hash(idStock); }

    @Override
    public String toString() {
        return "Stock{idStock=" + idStock + ", quantidadeAtual=" + quantidadeAtual + "}";
    }
}
