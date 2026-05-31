package com.smartsoft.entity;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "tenants", indexes = {
    @Index(name = "idx_tenants_nif", columnList = "nif"),
    @Index(name = "idx_tenants_ativo", columnList = "ativo")
})
public class Tenant {

    @Id
    @GeneratedValue
    @Column(name = "id_tenant", updatable = false, nullable = false)
    private UUID idTenant;

    @NotBlank
    @Size(max = 20)
    @Column(name = "nif", unique = true, nullable = false, length = 20)
    private String nif;

    @NotBlank
    @Size(max = 255)
    @Column(name = "nome_empresa", nullable = false)
    private String nomeEmpresa;

    @NotBlank
    @Email
    @Size(max = 255)
    @Column(name = "email_empresa", nullable = false)
    private String emailEmpresa;

    @Size(max = 20)
    @Column(name = "telefone_empresa", length = 20)
    private String telefoneEmpresa;

    @Size(max = 500)
    @Column(name = "morada", length = 500)
    private String morada;

    @Size(max = 10)
    @Column(name = "codigo_postal", length = 10)
    private String codigoPostal;

    @Size(max = 100)
    @Column(name = "cidade", length = 100)
    private String cidade;

    @Size(max = 100)
    @Column(name = "pais", length = 100)
    private String pais;

    @Column(name = "logotipo")
    private byte[] logotipo;

    @Pattern(regexp = "pt|en|fr|zh")
    @Column(name = "idioma_padrao", length = 10, columnDefinition = "VARCHAR(10) DEFAULT 'pt'")
    private String idiomaPadrao = "pt";

    @Column(name = "fuso_horario", length = 50, columnDefinition = "VARCHAR(50) DEFAULT 'Europe/Lisbon'")
    private String fusoHorario = "Europe/Lisbon";

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    @Column(name = "ativo", columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean ativo = true;

    @Column(name = "ult_backup")
    private LocalDateTime ultBackup;

    @PrePersist
    protected void onCreate() {
        dataCriacao = LocalDateTime.now();
        dataAtualizacao = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        dataAtualizacao = LocalDateTime.now();
    }

    public Tenant() {}

    public UUID getIdTenant() { return idTenant; }
    public void setIdTenant(UUID idTenant) { this.idTenant = idTenant; }

    public String getNif() { return nif; }
    public void setNif(String nif) { this.nif = nif; }

    public String getNomeEmpresa() { return nomeEmpresa; }
    public void setNomeEmpresa(String nomeEmpresa) { this.nomeEmpresa = nomeEmpresa; }

    public String getEmailEmpresa() { return emailEmpresa; }
    public void setEmailEmpresa(String emailEmpresa) { this.emailEmpresa = emailEmpresa; }

    public String getTelefoneEmpresa() { return telefoneEmpresa; }
    public void setTelefoneEmpresa(String telefoneEmpresa) { this.telefoneEmpresa = telefoneEmpresa; }

    public String getMorada() { return morada; }
    public void setMorada(String morada) { this.morada = morada; }

    public String getCodigoPostal() { return codigoPostal; }
    public void setCodigoPostal(String codigoPostal) { this.codigoPostal = codigoPostal; }

    public String getCidade() { return cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }

    public String getPais() { return pais; }
    public void setPais(String pais) { this.pais = pais; }

    public byte[] getLogotipo() { return logotipo; }
    public void setLogotipo(byte[] logotipo) { this.logotipo = logotipo; }

    public String getIdiomaPadrao() { return idiomaPadrao; }
    public void setIdiomaPadrao(String idiomaPadrao) { this.idiomaPadrao = idiomaPadrao; }

    public String getFusoHorario() { return fusoHorario; }
    public void setFusoHorario(String fusoHorario) { this.fusoHorario = fusoHorario; }

    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }

    public LocalDateTime getDataAtualizacao() { return dataAtualizacao; }
    public void setDataAtualizacao(LocalDateTime dataAtualizacao) { this.dataAtualizacao = dataAtualizacao; }

    public Boolean getAtivo() { return ativo; }
    public void setAtivo(Boolean ativo) { this.ativo = ativo; }

    public LocalDateTime getUltBackup() { return ultBackup; }
    public void setUltBackup(LocalDateTime ultBackup) { this.ultBackup = ultBackup; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tenant tenant = (Tenant) o;
        return Objects.equals(idTenant, tenant.idTenant) && Objects.equals(nif, tenant.nif);
    }

    @Override
    public int hashCode() { return Objects.hash(idTenant, nif); }

    @Override
    public String toString() {
        return "Tenant{idTenant=" + idTenant + ", nif='" + nif + "', nomeEmpresa='" + nomeEmpresa + "'}";
    }
}
