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
@Table(name = "produtos")

public class Produto implements Serializable {

    @Id
    @Column(length = 36)
    private String id = UUID.randomUUID().toString();
    @Column(name = "tenant_nif", nullable = false, length = 20)
    private String tenantNif;
    @Column(nullable = false, length = 50)
    private String codigo;
    @Column(nullable = false, length = 180)
    private String nome;
    @Column(length = 255)
    private String descricao;
    @Column(name = "preco_venda", nullable = false, precision = 18, scale = 2)
    private java.math.BigDecimal precoVenda = java.math.BigDecimal.ZERO;
    @Column(name = "imposto_percentual", nullable = false, precision = 8, scale = 2)
    private java.math.BigDecimal impostoPercentual = new java.math.BigDecimal("14.00");
    @Column(name = "stock_actual", nullable = false, precision = 18, scale = 2)
    private java.math.BigDecimal stockAtual = java.math.BigDecimal.ZERO;
    @Column(nullable = false)
    private Boolean activo = Boolean.TRUE;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTenantNif() {
        return tenantNif;
    }

    public void setTenantNif(String tenantNif) {
        this.tenantNif = tenantNif;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public java.math.BigDecimal getPrecoVenda() {
        return precoVenda;
    }

    public void setPrecoVenda(java.math.BigDecimal precoVenda) {
        this.precoVenda = precoVenda;
    }

    public java.math.BigDecimal getImpostoPercentual() {
        return impostoPercentual;
    }

    public void setImpostoPercentual(java.math.BigDecimal impostoPercentual) {
        this.impostoPercentual = impostoPercentual;
    }

    public java.math.BigDecimal getStockAtual() {
        return stockAtual;
    }

    public void setStockAtual(java.math.BigDecimal stockAtual) {
        this.stockAtual = stockAtual;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

}
