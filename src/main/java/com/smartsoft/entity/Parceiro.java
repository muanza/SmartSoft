package com.smartsoft.entity;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "parceiros", indexes = {
    @Index(name = "idx_parceiros_email", columnList = "email_parceiro"),
    @Index(name = "idx_parceiros_ativo", columnList = "ativo")
})
public class Parceiro {

    @Id
    @GeneratedValue
    @Column(name = "id_parceiro", updatable = false, nullable = false)
    private UUID idParceiro;

    @NotBlank
    @Size(max = 255)
    @Column(name = "nome_parceiro", nullable = false)
    private String nomeParceiro;

    @NotBlank
    @Email
    @Size(max = 255)
    @Column(name = "email_parceiro", nullable = false, unique = true)
    private String emailParceiro;

    @Size(max = 20)
    @Column(name = "telefone_parceiro", length = 20)
    private String telefoneParceiro;

    @Size(max = 500)
    @Column(name = "morada_parceiro", length = 500)
    private String moradaParceiro;

    @Size(max = 20)
    @Column(name = "nif_parceiro", unique = true, length = 20)
    private String nifParceiro;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @Column(name = "ativo", columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean ativo = true;

    @OneToMany(mappedBy = "parceiro", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ParceiroTenant> parceiroTenants;

    @PrePersist
    protected void onCreate() {
        dataCriacao = LocalDateTime.now();
    }

    public Parceiro() {}

    public UUID getIdParceiro() { return idParceiro; }
    public void setIdParceiro(UUID idParceiro) { this.idParceiro = idParceiro; }

    public String getNomeParceiro() { return nomeParceiro; }
    public void setNomeParceiro(String nomeParceiro) { this.nomeParceiro = nomeParceiro; }

    public String getEmailParceiro() { return emailParceiro; }
    public void setEmailParceiro(String emailParceiro) { this.emailParceiro = emailParceiro; }

    public String getTelefoneParceiro() { return telefoneParceiro; }
    public void setTelefoneParceiro(String telefoneParceiro) { this.telefoneParceiro = telefoneParceiro; }

    public String getMoradaParceiro() { return moradaParceiro; }
    public void setMoradaParceiro(String moradaParceiro) { this.moradaParceiro = moradaParceiro; }

    public String getNifParceiro() { return nifParceiro; }
    public void setNifParceiro(String nifParceiro) { this.nifParceiro = nifParceiro; }

    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }

    public Boolean getAtivo() { return ativo; }
    public void setAtivo(Boolean ativo) { this.ativo = ativo; }

    public List<ParceiroTenant> getParceiroTenants() { return parceiroTenants; }
    public void setParceiroTenants(List<ParceiroTenant> parceiroTenants) { this.parceiroTenants = parceiroTenants; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Parceiro parceiro = (Parceiro) o;
        return Objects.equals(idParceiro, parceiro.idParceiro);
    }

    @Override
    public int hashCode() { return Objects.hash(idParceiro); }

    @Override
    public String toString() {
        return "Parceiro{idParceiro=" + idParceiro + ", nomeParceiro='" + nomeParceiro + "'}";
    }
}
