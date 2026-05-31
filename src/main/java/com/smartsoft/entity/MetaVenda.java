package com.smartsoft.entity;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "metas_vendas",
    uniqueConstraints = @UniqueConstraint(columnNames = {"id_tenant", "id_utilizador", "ano", "mes"}),
    indexes = {
        @Index(name = "idx_metas_vendas_tenant", columnList = "id_tenant"),
        @Index(name = "idx_metas_vendas_utilizador", columnList = "id_utilizador")
    })
public class MetaVenda {

    @Id
    @GeneratedValue
    @Column(name = "id_meta", updatable = false, nullable = false)
    private UUID idMeta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tenant", nullable = false)
    private Tenant tenant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_utilizador")
    private Utilizador utilizador;

    @NotNull
    @Min(2000)
    @Column(name = "ano", nullable = false)
    private Integer ano;

    @NotNull
    @Min(1) @Max(12)
    @Column(name = "mes", nullable = false)
    private Integer mes;

    @NotNull
    @DecimalMin("0.00")
    @Column(name = "valor_meta", nullable = false, precision = 12, scale = 2)
    private BigDecimal valorMeta;

    @DecimalMin("0.00")
    @Column(name = "valor_realizado", precision = 12, scale = 2, columnDefinition = "DECIMAL(12,2) DEFAULT 0.00")
    private BigDecimal valorRealizado = BigDecimal.ZERO;

    @DecimalMin("0.00")
    @Column(name = "percentagem_atingimento", precision = 5, scale = 2, columnDefinition = "DECIMAL(5,2) DEFAULT 0.00")
    private BigDecimal percentagemAtingimento = BigDecimal.ZERO;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @PrePersist
    protected void onCreate() {
        dataCriacao = LocalDateTime.now();
    }

    public MetaVenda() {}

    public UUID getIdMeta() { return idMeta; }
    public void setIdMeta(UUID idMeta) { this.idMeta = idMeta; }

    public Tenant getTenant() { return tenant; }
    public void setTenant(Tenant tenant) { this.tenant = tenant; }

    public Utilizador getUtilizador() { return utilizador; }
    public void setUtilizador(Utilizador utilizador) { this.utilizador = utilizador; }

    public Integer getAno() { return ano; }
    public void setAno(Integer ano) { this.ano = ano; }

    public Integer getMes() { return mes; }
    public void setMes(Integer mes) { this.mes = mes; }

    public BigDecimal getValorMeta() { return valorMeta; }
    public void setValorMeta(BigDecimal valorMeta) { this.valorMeta = valorMeta; }

    public BigDecimal getValorRealizado() { return valorRealizado; }
    public void setValorRealizado(BigDecimal valorRealizado) { this.valorRealizado = valorRealizado; }

    public BigDecimal getPercentagemAtingimento() { return percentagemAtingimento; }
    public void setPercentagemAtingimento(BigDecimal percentagemAtingimento) { this.percentagemAtingimento = percentagemAtingimento; }

    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MetaVenda that = (MetaVenda) o;
        return Objects.equals(idMeta, that.idMeta);
    }

    @Override
    public int hashCode() { return Objects.hash(idMeta); }

    @Override
    public String toString() {
        return "MetaVenda{idMeta=" + idMeta + ", ano=" + ano + ", mes=" + mes + ", valorMeta=" + valorMeta + "}";
    }
}
