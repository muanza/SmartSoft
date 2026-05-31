package com.faturacao.pos.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Table(name = "linhas_factura")

public class LinhaFactura implements Serializable {

    @Id
    @Column(length = 36)
    private String id = UUID.randomUUID().toString();
    @Column(name = "factura_id", length = 36)
    private String facturaId;
    @Column(nullable = false, length = 180)
    private String descricao;
    @Column(nullable = false, precision = 18, scale = 2)
    private java.math.BigDecimal quantidade = java.math.BigDecimal.ONE;
    @Column(name = "preco_unitario", nullable = false, precision = 18, scale = 2)
    private java.math.BigDecimal precoUnitario = java.math.BigDecimal.ZERO;
    @Column(name = "taxa_imposto", nullable = false, precision = 8, scale = 2)
    private java.math.BigDecimal taxaImposto = new java.math.BigDecimal("14.00");
    @Column(nullable = false, precision = 18, scale = 2)
    private java.math.BigDecimal subtotal = java.math.BigDecimal.ZERO;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFacturaId() {
        return facturaId;
    }

    public void setFacturaId(String facturaId) {
        this.facturaId = facturaId;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public java.math.BigDecimal getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(java.math.BigDecimal quantidade) {
        this.quantidade = quantidade;
    }

    public java.math.BigDecimal getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(java.math.BigDecimal precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public java.math.BigDecimal getTaxaImposto() {
        return taxaImposto;
    }

    public void setTaxaImposto(java.math.BigDecimal taxaImposto) {
        this.taxaImposto = taxaImposto;
    }

    public java.math.BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(java.math.BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

}
