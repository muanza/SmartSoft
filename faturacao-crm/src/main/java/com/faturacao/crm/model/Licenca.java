package com.faturacao.crm.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Table(name = "licencas")

public class Licenca implements Serializable {

    @Id
    @Column(length = 36)
    private String id = UUID.randomUUID().toString();
    @Column(name = "tenant_nif", nullable = false, length = 20)
    private String tenantNif;
    @Column(name = "parceiro_id", length = 36)
    private String parceiroId;
    @Column(name = "plano_id", nullable = false, length = 36)
    private String planoId;
    @Column(name = "chave_activacao", nullable = false, length = 120)
    private String chaveActivacao;
    @Column(nullable = false, length = 30)
    private String estado = "ACTIVA";
    @Column(name = "limite_facturas_dia", nullable = false)
    private Integer limiteFacturasDia = 1000;
    @Column(name = "data_inicio")
    private LocalDate dataInicio = LocalDate.now();
    @Column(name = "data_fim")
    private LocalDate dataFim = LocalDate.now().plusYears(1);
    @Column(name = "ultimo_ping")
    private LocalDateTime ultimoPing;

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

    public String getParceiroId() {
        return parceiroId;
    }

    public void setParceiroId(String parceiroId) {
        this.parceiroId = parceiroId;
    }

    public String getPlanoId() {
        return planoId;
    }

    public void setPlanoId(String planoId) {
        this.planoId = planoId;
    }

    public String getChaveActivacao() {
        return chaveActivacao;
    }

    public void setChaveActivacao(String chaveActivacao) {
        this.chaveActivacao = chaveActivacao;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getLimiteFacturasDia() {
        return limiteFacturasDia;
    }

    public void setLimiteFacturasDia(Integer limiteFacturasDia) {
        this.limiteFacturasDia = limiteFacturasDia;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public LocalDateTime getUltimoPing() {
        return ultimoPing;
    }

    public void setUltimoPing(LocalDateTime ultimoPing) {
        this.ultimoPing = ultimoPing;
    }

}
