package com.smartsoft.entity;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "movimentos_caixa", indexes = {
    @Index(name = "idx_movimentos_caixa", columnList = "id_caixa"),
    @Index(name = "idx_movimentos_tenant", columnList = "id_tenant")
})
public class MovimentoCaixa {

    @Id
    @GeneratedValue
    @Column(name = "id_movimento", updatable = false, nullable = false)
    private UUID idMovimento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_caixa", nullable = false)
    private Caixa caixa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tenant", nullable = false)
    private Tenant tenant;

    @NotBlank
    @Pattern(regexp = "entrada|saida|devolucao")
    @Column(name = "tipo_movimento", nullable = false, length = 50)
    private String tipoMovimento;

    @Size(max = 255)
    @Column(name = "descricao", length = 255)
    private String descricao;

    @NotNull
    @Column(name = "montante", nullable = false, precision = 12, scale = 2)
    private BigDecimal montante;

    @Pattern(regexp = "dinheiro|cartao|cheque|transferencia|outro")
    @Column(name = "metodo_pagamento", length = 50)
    private String metodoPagamento;

    @Column(name = "data_movimento")
    private LocalDateTime dataMovimento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "referencia_factura")
    private Factura referenciaFactura;

    @PrePersist
    protected void onCreate() {
        dataMovimento = LocalDateTime.now();
    }

    public MovimentoCaixa() {}

    public UUID getIdMovimento() { return idMovimento; }
    public void setIdMovimento(UUID idMovimento) { this.idMovimento = idMovimento; }

    public Caixa getCaixa() { return caixa; }
    public void setCaixa(Caixa caixa) { this.caixa = caixa; }

    public Tenant getTenant() { return tenant; }
    public void setTenant(Tenant tenant) { this.tenant = tenant; }

    public String getTipoMovimento() { return tipoMovimento; }
    public void setTipoMovimento(String tipoMovimento) { this.tipoMovimento = tipoMovimento; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public BigDecimal getMontante() { return montante; }
    public void setMontante(BigDecimal montante) { this.montante = montante; }

    public String getMetodoPagamento() { return metodoPagamento; }
    public void setMetodoPagamento(String metodoPagamento) { this.metodoPagamento = metodoPagamento; }

    public LocalDateTime getDataMovimento() { return dataMovimento; }
    public void setDataMovimento(LocalDateTime dataMovimento) { this.dataMovimento = dataMovimento; }

    public Factura getReferenciaFactura() { return referenciaFactura; }
    public void setReferenciaFactura(Factura referenciaFactura) { this.referenciaFactura = referenciaFactura; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovimentoCaixa that = (MovimentoCaixa) o;
        return Objects.equals(idMovimento, that.idMovimento);
    }

    @Override
    public int hashCode() { return Objects.hash(idMovimento); }

    @Override
    public String toString() {
        return "MovimentoCaixa{idMovimento=" + idMovimento + ", tipoMovimento='" + tipoMovimento + "', montante=" + montante + "}";
    }
}
