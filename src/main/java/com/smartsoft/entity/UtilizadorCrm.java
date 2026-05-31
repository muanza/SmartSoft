package com.smartsoft.entity;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "utilizadores_crm", indexes = {
    @Index(name = "idx_utilizadores_crm_email", columnList = "email"),
    @Index(name = "idx_utilizadores_crm_perfil", columnList = "perfil"),
    @Index(name = "idx_utilizadores_crm_ativo", columnList = "ativo")
})
public class UtilizadorCrm {

    @Id
    @GeneratedValue
    @Column(name = "id_utilizador_crm", updatable = false, nullable = false)
    private UUID idUtilizadorCrm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_parceiro")
    private Parceiro parceiro;

    @NotBlank
    @Email
    @Size(max = 255)
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @NotBlank
    @Size(max = 255)
    @Column(name = "nome_completo", nullable = false)
    private String nomeCompleto;

    @NotBlank
    @Size(max = 255)
    @Column(name = "senha_hash", nullable = false)
    private String senhaHash;

    @NotBlank
    @Pattern(regexp = "administrador|parceiro")
    @Column(name = "perfil", nullable = false, length = 50)
    private String perfil;

    @Column(name = "ativo", columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean ativo = true;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @Column(name = "data_ultimo_acesso")
    private LocalDateTime dataUltimoAcesso;

    @Size(max = 45)
    @Column(name = "ultimo_ip_acesso", length = 45)
    private String ultimoIpAcesso;

    @PrePersist
    protected void onCreate() {
        dataCriacao = LocalDateTime.now();
    }

    public UtilizadorCrm() {}

    public UUID getIdUtilizadorCrm() { return idUtilizadorCrm; }
    public void setIdUtilizadorCrm(UUID idUtilizadorCrm) { this.idUtilizadorCrm = idUtilizadorCrm; }

    public Parceiro getParceiro() { return parceiro; }
    public void setParceiro(Parceiro parceiro) { this.parceiro = parceiro; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getNomeCompleto() { return nomeCompleto; }
    public void setNomeCompleto(String nomeCompleto) { this.nomeCompleto = nomeCompleto; }

    public String getSenhaHash() { return senhaHash; }
    public void setSenhaHash(String senhaHash) { this.senhaHash = senhaHash; }

    public String getPerfil() { return perfil; }
    public void setPerfil(String perfil) { this.perfil = perfil; }

    public Boolean getAtivo() { return ativo; }
    public void setAtivo(Boolean ativo) { this.ativo = ativo; }

    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }

    public LocalDateTime getDataUltimoAcesso() { return dataUltimoAcesso; }
    public void setDataUltimoAcesso(LocalDateTime dataUltimoAcesso) { this.dataUltimoAcesso = dataUltimoAcesso; }

    public String getUltimoIpAcesso() { return ultimoIpAcesso; }
    public void setUltimoIpAcesso(String ultimoIpAcesso) { this.ultimoIpAcesso = ultimoIpAcesso; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UtilizadorCrm that = (UtilizadorCrm) o;
        return Objects.equals(idUtilizadorCrm, that.idUtilizadorCrm);
    }

    @Override
    public int hashCode() { return Objects.hash(idUtilizadorCrm); }

    @Override
    public String toString() {
        return "UtilizadorCrm{idUtilizadorCrm=" + idUtilizadorCrm + ", email='" + email + "', perfil='" + perfil + "'}";
    }
}
