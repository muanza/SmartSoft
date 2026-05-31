package com.smartsoft.entity;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "caixas", indexes = {
    @Index(name = "idx_caixas_tenant", columnList = "id_tenant"),
    @Index(name = "idx_caixas_utilizador", columnList = "id_utilizador"),
    @Index(name = "idx_caixas_status", columnList = "status")
})
public class Caixa {

    @Id
    @GeneratedValue
    @Column(name = "id_caixa", updatable = false, nullable = false)
    private UUID idCaixa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tenant", nullable = false)
    private Tenant tenant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_utilizador", nullable = false)
    private Utilizador utilizador;

    @NotBlank
    @Size(max = 20)
    @Column(name = "numero_caixa", nullable = false, length = 20)
    private String numeroCaixa;

    @Column(name = "data_abertura")
    private LocalDateTime dataAbertura;

    @Column(name = "data_fecho")
    private LocalDateTime dataFecho;

    @DecimalMin("0.00")
    @Column(name = "saldo_inicial", precision = 12, scale = 2, columnDefinition = "DECIMAL(12,2) DEFAULT 0.00")
    private BigDecimal saldoInicial = BigDecimal.ZERO;

    @Column(name = "saldo_final", precision = 12, scale = 2)
    private BigDecimal saldoFinal;

    @Min(0)
    @Column(name = "num_facturas", columnDefinition = "INT DEFAULT 0")
    private Integer numFacturas = 0;

    @DecimalMin("0.00")
    @Column(name = "total_vendas", precision = 12, scale = 2, columnDefinition = "DECIMAL(12,2) DEFAULT 0.00")
    private BigDecimal totalVendas = BigDecimal.ZERO;

    @Size(max = 3)
    @Column(name = "moeda", length = 3, columnDefinition = "VARCHAR(3) DEFAULT 'AOA'")
    private String moeda = "AOA";

    @Pattern(regexp = "aberta|fechada|suspensa")
    @Column(name = "status", length = 20)
    private String status;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;

    @OneToMany(mappedBy = "caixa", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MovimentoCaixa> movimentos;

    @PrePersist
    protected void onCreate() {
        dataAbertura = LocalDateTime.now();
        status = "aberta";
    }

    public Caixa() {}

    public UUID getIdCaixa() { return idCaixa; }
    public void setIdCaixa(UUID idCaixa) { this.idCaixa = idCaixa; }

    public Tenant getTenant() { return tenant; }
    public void setTenant(Tenant tenant) { this.tenant = tenant; }

    public Utilizador getUtilizador() { return utilizador; }
    public void setUtilizador(Utilizador utilizador) { this.utilizador = utilizador; }

    public String getNumeroCaixa() { return numeroCaixa; }
    public void setNumeroCaixa(String numeroCaixa) { this.numeroCaixa = numeroCaixa; }

    public LocalDateTime getDataAbertura() { return dataAbertura; }
    public void setDataAbertura(LocalDateTime dataAbertura) { this.dataAbertura = dataAbertura; }

    public LocalDateTime getDataFecho() { return dataFecho; }
    public void setDataFecho(LocalDateTime dataFecho) { this.dataFecho = dataFecho; }

    public BigDecimal getSaldoInicial() { return saldoInicial; }
    public void setSaldoInicial(BigDecimal saldoInicial) { this.saldoInicial = saldoInicial; }

    public BigDecimal getSaldoFinal() { return saldoFinal; }
    public void setSaldoFinal(BigDecimal saldoFinal) { this.saldoFinal = saldoFinal; }

    public Integer getNumFacturas() { return numFacturas; }
    public void setNumFacturas(Integer numFacturas) { this.numFacturas = numFacturas; }

    public BigDecimal getTotalVendas() { return totalVendas; }
    public void setTotalVendas(BigDecimal totalVendas) { this.totalVendas = totalVendas; }

    public String getMoeda() { return moeda; }
    public void setMoeda(String moeda) { this.moeda = moeda; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }

    public List<MovimentoCaixa> getMovimentos() { return movimentos; }
    public void setMovimentos(List<MovimentoCaixa> movimentos) { this.movimentos = movimentos; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Caixa caixa = (Caixa) o;
        return Objects.equals(idCaixa, caixa.idCaixa);
    }

    @Override
    public int hashCode() { return Objects.hash(idCaixa); }

    @Override
    public String toString() {
        return "Caixa{idCaixa=" + idCaixa + ", numeroCaixa='" + numeroCaixa + "', status='" + status + "'}";
    }
}
