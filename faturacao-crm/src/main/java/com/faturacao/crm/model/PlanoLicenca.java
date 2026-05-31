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
@Table(name = "planos_licenca")

public class PlanoLicenca implements Serializable {

    @Id
    @Column(length = 36)
    private String id = UUID.randomUUID().toString();
    @Column(nullable = false, length = 100)
    private String nome;
    @Column(length = 255)
    private String descricao;
    @Column(name = "limite_utilizadores", nullable = false)
    private Integer limiteUtilizadores = 5;
    @Column(name = "limite_facturas_dia", nullable = false)
    private Integer limiteFacturasDia = 1000;
    @Column(name = "permite_multi_caixa", nullable = false)
    private Boolean permiteMultiCaixa = Boolean.FALSE;
    @Column(name = "sincronizacao_cloud", nullable = false)
    private Boolean sincronizacaoCloud = Boolean.TRUE;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Integer getLimiteUtilizadores() {
        return limiteUtilizadores;
    }

    public void setLimiteUtilizadores(Integer limiteUtilizadores) {
        this.limiteUtilizadores = limiteUtilizadores;
    }

    public Integer getLimiteFacturasDia() {
        return limiteFacturasDia;
    }

    public void setLimiteFacturasDia(Integer limiteFacturasDia) {
        this.limiteFacturasDia = limiteFacturasDia;
    }

    public Boolean getPermiteMultiCaixa() {
        return permiteMultiCaixa;
    }

    public void setPermiteMultiCaixa(Boolean permiteMultiCaixa) {
        this.permiteMultiCaixa = permiteMultiCaixa;
    }

    public Boolean getSincronizacaoCloud() {
        return sincronizacaoCloud;
    }

    public void setSincronizacaoCloud(Boolean sincronizacaoCloud) {
        this.sincronizacaoCloud = sincronizacaoCloud;
    }

}
