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
@Table(name = "tenants")

public class Tenant implements Serializable {

    @Id
    @Column(length = 20)
    private String nif;
    @Column(name = "nome_empresa", nullable = false, length = 180)
    private String nomeEmpresa;
    @Column(name = "email_empresa", nullable = false, length = 180)
    private String emailEmpresa;
    @Column(name = "telefone_empresa", length = 30)
    private String telefoneEmpresa;
    @Column(name = "idioma_preferido", nullable = false, length = 5)
    private String idiomaPreferido = "pt";
    @Column(name = "nome_base_dados", nullable = false, length = 120)
    private String nomeBaseDados;
    @Column(nullable = false, length = 30)
    private String estado = "ACTIVO";
    @Column(name = "parceiro_id", length = 36)
    private String parceiroId;
    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao = LocalDateTime.now();

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getNomeEmpresa() {
        return nomeEmpresa;
    }

    public void setNomeEmpresa(String nomeEmpresa) {
        this.nomeEmpresa = nomeEmpresa;
    }

    public String getEmailEmpresa() {
        return emailEmpresa;
    }

    public void setEmailEmpresa(String emailEmpresa) {
        this.emailEmpresa = emailEmpresa;
    }

    public String getTelefoneEmpresa() {
        return telefoneEmpresa;
    }

    public void setTelefoneEmpresa(String telefoneEmpresa) {
        this.telefoneEmpresa = telefoneEmpresa;
    }

    public String getIdiomaPreferido() {
        return idiomaPreferido;
    }

    public void setIdiomaPreferido(String idiomaPreferido) {
        this.idiomaPreferido = idiomaPreferido;
    }

    public String getNomeBaseDados() {
        return nomeBaseDados;
    }

    public void setNomeBaseDados(String nomeBaseDados) {
        this.nomeBaseDados = nomeBaseDados;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getParceiroId() {
        return parceiroId;
    }

    public void setParceiroId(String parceiroId) {
        this.parceiroId = parceiroId;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

}
