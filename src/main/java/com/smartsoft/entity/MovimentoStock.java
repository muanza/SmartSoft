package com.smartsoft.entity;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "movimentos_stock", indexes = {
    @Index(name = "idx_movimentos_stock_stock", columnList = "id_stock"),
    @Index(name = "idx_movimentos_stock_tenant", columnList = "id_tenant"),
    @Index(name = "idx_movimentos_stock_data", columnList = "data_movimento")
})
public class MovimentoStock {

    @Id
    @GeneratedValue
    @Column(name = "id_movimento_stock", updatable = false, nullable = false)
    private UUID idMovimentoStock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_stock", nullable = false)
    private Stock stock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tenant", nullable = false)
    private Tenant tenant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_utilizador")
    private Utilizador utilizador;

    @NotBlank
    @Pattern(regexp = "entrada|saida|ajuste|devolucao|transferencia")
    @Column(name = "tipo_movimento", nullable = false, length = 50)
    private String tipoMovimento;

    @NotNull
    @Column(name = "quantidade_movimento", nullable = false)
    private Integer quantidadeMovimento;

    @Column(name = "motivo", columnDefinition = "TEXT")
    private String motivo;

    @Size(max = 100)
    @Column(name = "referencia_documento", length = 100)
    private String referenciaDocumento;

    @Column(name = "data_movimento")
    private LocalDateTime dataMovimento;

    @PrePersist
    protected void onCreate() {
        dataMovimento = LocalDateTime.now();
    }

    public MovimentoStock() {}

    public UUID getIdMovimentoStock() { return idMovimentoStock; }
    public void setIdMovimentoStock(UUID idMovimentoStock) { this.idMovimentoStock = idMovimentoStock; }

    public Stock getStock() { return stock; }
    public void setStock(Stock stock) { this.stock = stock; }

    public Tenant getTenant() { return tenant; }
    public void setTenant(Tenant tenant) { this.tenant = tenant; }

    public Utilizador getUtilizador() { return utilizador; }
    public void setUtilizador(Utilizador utilizador) { this.utilizador = utilizador; }

    public String getTipoMovimento() { return tipoMovimento; }
    public void setTipoMovimento(String tipoMovimento) { this.tipoMovimento = tipoMovimento; }

    public Integer getQuantidadeMovimento() { return quantidadeMovimento; }
    public void setQuantidadeMovimento(Integer quantidadeMovimento) { this.quantidadeMovimento = quantidadeMovimento; }

    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }

    public String getReferenciaDocumento() { return referenciaDocumento; }
    public void setReferenciaDocumento(String referenciaDocumento) { this.referenciaDocumento = referenciaDocumento; }

    public LocalDateTime getDataMovimento() { return dataMovimento; }
    public void setDataMovimento(LocalDateTime dataMovimento) { this.dataMovimento = dataMovimento; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovimentoStock that = (MovimentoStock) o;
        return Objects.equals(idMovimentoStock, that.idMovimentoStock);
    }

    @Override
    public int hashCode() { return Objects.hash(idMovimentoStock); }

    @Override
    public String toString() {
        return "MovimentoStock{idMovimentoStock=" + idMovimentoStock + ", tipoMovimento='" + tipoMovimento + "', quantidadeMovimento=" + quantidadeMovimento + "}";
    }
}
