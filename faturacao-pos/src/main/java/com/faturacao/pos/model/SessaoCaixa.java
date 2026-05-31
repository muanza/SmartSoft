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
@Table(name = "sessoes_caixa")

public class SessaoCaixa implements Serializable {

    @Id
    @Column(length = 36)
    private String id = UUID.randomUUID().toString();
    @Column(name = "tenant_nif", nullable = false, length = 20)
    private String tenantNif;
    @Column(name = "caixa_codigo", nullable = false, length = 40)
    private String caixaCodigo;
    @Column(nullable = false, length = 150)
    private String operador;
    @Column(name = "valor_abertura", nullable = false, precision = 18, scale = 2)
    private java.math.BigDecimal valorAbertura = java.math.BigDecimal.ZERO;
    @Column(name = "valor_fecho", precision = 18, scale = 2)
    private java.math.BigDecimal valorFecho = java.math.BigDecimal.ZERO;
    @Column(nullable = false, length = 30)
    private String estado = "ABERTA";
    @Column(name = "aberta_em")
    private LocalDateTime abertaEm = LocalDateTime.now();
    @Column(name = "fechada_em")
    private LocalDateTime fechadaEm;

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

    public String getCaixaCodigo() {
        return caixaCodigo;
    }

    public void setCaixaCodigo(String caixaCodigo) {
        this.caixaCodigo = caixaCodigo;
    }

    public String getOperador() {
        return operador;
    }

    public void setOperador(String operador) {
        this.operador = operador;
    }

    public java.math.BigDecimal getValorAbertura() {
        return valorAbertura;
    }

    public void setValorAbertura(java.math.BigDecimal valorAbertura) {
        this.valorAbertura = valorAbertura;
    }

    public java.math.BigDecimal getValorFecho() {
        return valorFecho;
    }

    public void setValorFecho(java.math.BigDecimal valorFecho) {
        this.valorFecho = valorFecho;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDateTime getAbertaEm() {
        return abertaEm;
    }

    public void setAbertaEm(LocalDateTime abertaEm) {
        this.abertaEm = abertaEm;
    }

    public LocalDateTime getFechadaEm() {
        return fechadaEm;
    }

    public void setFechadaEm(LocalDateTime fechadaEm) {
        this.fechadaEm = fechadaEm;
    }

}
