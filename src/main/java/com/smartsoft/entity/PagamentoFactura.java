package com.smartsoft.entity;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "pagamentos_factura", indexes = {
    @Index(name = "idx_pagamentos_factura", columnList = "id_factura"),
    @Index(name = "idx_pagamentos_tenant", columnList = "id_tenant")
})
public class PagamentoFactura {

    @Id
    @GeneratedValue
    @Column(name = "id_pagamento", updatable = false, nullable = false)
    private UUID idPagamento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_factura", nullable = false)
    private Factura factura;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tenant", nullable = false)
    private Tenant tenant;

    @NotNull
    @DecimalMin("0.01")
    @Column(name = "montante_pagamento", nullable = false, precision = 12, scale = 2)
    private BigDecimal montantePagamento;

    @NotBlank
    @Column(name = "metodo_pagamento", nullable = false, length = 50)
    private String metodoPagamento;

    @Column(name = "data_pagamento")
    private LocalDateTime dataPagamento;

    @Size(max = 100)
    @Column(name = "referencia_pagamento", length = 100)
    private String referenciaPagamento;

    @Pattern(regexp = "pendente|processado|falhou|cancelado")
    @Column(name = "status_pagamento", length = 50, columnDefinition = "VARCHAR(50) DEFAULT 'processado'")
    private String statusPagamento = "processado";

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;

    @PrePersist
    protected void onCreate() {
        dataPagamento = LocalDateTime.now();
    }

    public PagamentoFactura() {}

    public UUID getIdPagamento() { return idPagamento; }
    public void setIdPagamento(UUID idPagamento) { this.idPagamento = idPagamento; }

    public Factura getFactura() { return factura; }
    public void setFactura(Factura factura) { this.factura = factura; }

    public Tenant getTenant() { return tenant; }
    public void setTenant(Tenant tenant) { this.tenant = tenant; }

    public BigDecimal getMontantePagamento() { return montantePagamento; }
    public void setMontantePagamento(BigDecimal montantePagamento) { this.montantePagamento = montantePagamento; }

    public String getMetodoPagamento() { return metodoPagamento; }
    public void setMetodoPagamento(String metodoPagamento) { this.metodoPagamento = metodoPagamento; }

    public LocalDateTime getDataPagamento() { return dataPagamento; }
    public void setDataPagamento(LocalDateTime dataPagamento) { this.dataPagamento = dataPagamento; }

    public String getReferenciaPagamento() { return referenciaPagamento; }
    public void setReferenciaPagamento(String referenciaPagamento) { this.referenciaPagamento = referenciaPagamento; }

    public String getStatusPagamento() { return statusPagamento; }
    public void setStatusPagamento(String statusPagamento) { this.statusPagamento = statusPagamento; }

    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PagamentoFactura that = (PagamentoFactura) o;
        return Objects.equals(idPagamento, that.idPagamento);
    }

    @Override
    public int hashCode() { return Objects.hash(idPagamento); }

    @Override
    public String toString() {
        return "PagamentoFactura{idPagamento=" + idPagamento + ", montantePagamento=" + montantePagamento + "}";
    }
}
