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
@Table(name = "api_keys")

public class ApiKey implements Serializable {

    @Id
    @Column(length = 36)
    private String id = UUID.randomUUID().toString();
    @Column(name = "tenant_nif", nullable = false, length = 20)
    private String tenantNif;
    @Column(name = "chave_valor", nullable = false, unique = true, length = 120)
    private String chaveValor;
    @Column(length = 180)
    private String descricao;
    @Column(nullable = false)
    private Boolean activa = Boolean.TRUE;
    @Column(name = "ultima_utilizacao")
    private LocalDateTime ultimaUtilizacao;

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

    public String getChaveValor() {
        return chaveValor;
    }

    public void setChaveValor(String chaveValor) {
        this.chaveValor = chaveValor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean getActiva() {
        return activa;
    }

    public void setActiva(Boolean activa) {
        this.activa = activa;
    }

    public LocalDateTime getUltimaUtilizacao() {
        return ultimaUtilizacao;
    }

    public void setUltimaUtilizacao(LocalDateTime ultimaUtilizacao) {
        this.ultimaUtilizacao = ultimaUtilizacao;
    }

}
