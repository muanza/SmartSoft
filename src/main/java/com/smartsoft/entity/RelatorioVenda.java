package com.smartsoft.entity;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "relatorios_vendas", indexes = {
    @Index(name = "idx_relatorios_vendas_tenant", columnList = "id_tenant")
})
public class RelatorioVenda {

    @Id
    @GeneratedValue
    @Column(name = "id_relatorio", updatable = false, nullable = false)
    private UUID idRelatorio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tenant", nullable = false)
    private Tenant tenant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_utilizador")
    private Utilizador utilizador;

    @NotBlank
    @Size(max = 255)
    @Column(name = "titulo_relatorio", nullable = false)
    private String tituloRelatorio;

    @Column(name = "tipo_relatorio", length = 50)
    private String tipoRelatorio;

    @NotNull
    @Column(name = "data_inicio", nullable = false)
    private LocalDate dataInicio;

    @NotNull
    @Column(name = "data_fim", nullable = false)
    private LocalDate dataFim;

    @Column(name = "dados_relatorio", columnDefinition = "jsonb")
    private String dadosRelatorio;

    @Column(name = "total_vendas", precision = 12, scale = 2)
    private BigDecimal totalVendas;

    @Column(name = "numero_transacoes")
    private Integer numeroTransacoes;

    @Column(name = "data_geracao")
    private LocalDateTime dataGeracao;

    @PrePersist
    protected void onCreate() {
        dataGeracao = LocalDateTime.now();
    }

    public RelatorioVenda() {}

    public UUID getIdRelatorio() { return idRelatorio; }
    public void setIdRelatorio(UUID idRelatorio) { this.idRelatorio = idRelatorio; }

    public Tenant getTenant() { return tenant; }
    public void setTenant(Tenant tenant) { this.tenant = tenant; }

    public Utilizador getUtilizador() { return utilizador; }
    public void setUtilizador(Utilizador utilizador) { this.utilizador = utilizador; }

    public String getTituloRelatorio() { return tituloRelatorio; }
    public void setTituloRelatorio(String tituloRelatorio) { this.tituloRelatorio = tituloRelatorio; }

    public String getTipoRelatorio() { return tipoRelatorio; }
    public void setTipoRelatorio(String tipoRelatorio) { this.tipoRelatorio = tipoRelatorio; }

    public LocalDate getDataInicio() { return dataInicio; }
    public void setDataInicio(LocalDate dataInicio) { this.dataInicio = dataInicio; }

    public LocalDate getDataFim() { return dataFim; }
    public void setDataFim(LocalDate dataFim) { this.dataFim = dataFim; }

    public String getDadosRelatorio() { return dadosRelatorio; }
    public void setDadosRelatorio(String dadosRelatorio) { this.dadosRelatorio = dadosRelatorio; }

    public BigDecimal getTotalVendas() { return totalVendas; }
    public void setTotalVendas(BigDecimal totalVendas) { this.totalVendas = totalVendas; }

    public Integer getNumeroTransacoes() { return numeroTransacoes; }
    public void setNumeroTransacoes(Integer numeroTransacoes) { this.numeroTransacoes = numeroTransacoes; }

    public LocalDateTime getDataGeracao() { return dataGeracao; }
    public void setDataGeracao(LocalDateTime dataGeracao) { this.dataGeracao = dataGeracao; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RelatorioVenda that = (RelatorioVenda) o;
        return Objects.equals(idRelatorio, that.idRelatorio);
    }

    @Override
    public int hashCode() { return Objects.hash(idRelatorio); }

    @Override
    public String toString() {
        return "RelatorioVenda{idRelatorio=" + idRelatorio + ", tituloRelatorio='" + tituloRelatorio + "'}";
    }
}
