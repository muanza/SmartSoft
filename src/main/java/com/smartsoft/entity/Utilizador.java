package com.smartsoft.entity;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "utilizadores",
    uniqueConstraints = @UniqueConstraint(columnNames = {"id_tenant", "email"}),
    indexes = {
        @Index(name = "idx_utilizadores_tenant", columnList = "id_tenant"),
        @Index(name = "idx_utilizadores_email", columnList = "email"),
        @Index(name = "idx_utilizadores_perfil", columnList = "perfil")
    })
public class Utilizador {

    @Id
    @GeneratedValue
    @Column(name = "id_utilizador", updatable = false, nullable = false)
    private UUID idUtilizador;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tenant", nullable = false)
    private Tenant tenant;

    @NotBlank
    @Size(max = 100)
    @Column(name = "nome_utilizador", nullable = false, length = 100)
    private String nomeUtilizador;

    @NotBlank
    @Email
    @Size(max = 255)
    @Column(name = "email", nullable = false)
    private String email;

    @NotBlank
    @Size(max = 255)
    @Column(name = "senha_hash", nullable = false)
    private String senhaHash;

    @NotBlank
    @Pattern(regexp = "administrador|operador")
    @Column(name = "perfil", nullable = false, length = 50)
    private String perfil;

    @Column(name = "bloqueado", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean bloqueado = false;

    @Min(0)
    @Column(name = "numero_tentativas_falhas", columnDefinition = "INT DEFAULT 0")
    private Integer numeroTentativasFalhas = 0;

    @Column(name = "bloqueio_ecra", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean bloqueioEcra = false;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    @Column(name = "ultimo_acesso")
    private LocalDateTime ultimoAcesso;

    @Column(name = "ativo", columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean ativo = true;

    @PrePersist
    protected void onCreate() {
        dataCriacao = LocalDateTime.now();
        dataAtualizacao = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        dataAtualizacao = LocalDateTime.now();
    }

    public Utilizador() {}

    public UUID getIdUtilizador() { return idUtilizador; }
    public void setIdUtilizador(UUID idUtilizador) { this.idUtilizador = idUtilizador; }

    public Tenant getTenant() { return tenant; }
    public void setTenant(Tenant tenant) { this.tenant = tenant; }

    public String getNomeUtilizador() { return nomeUtilizador; }
    public void setNomeUtilizador(String nomeUtilizador) { this.nomeUtilizador = nomeUtilizador; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSenhaHash() { return senhaHash; }
    public void setSenhaHash(String senhaHash) { this.senhaHash = senhaHash; }

    public String getPerfil() { return perfil; }
    public void setPerfil(String perfil) { this.perfil = perfil; }

    public Boolean getBloqueado() { return bloqueado; }
    public void setBloqueado(Boolean bloqueado) { this.bloqueado = bloqueado; }

    public Integer getNumeroTentativasFalhas() { return numeroTentativasFalhas; }
    public void setNumeroTentativasFalhas(Integer numeroTentativasFalhas) { this.numeroTentativasFalhas = numeroTentativasFalhas; }

    public Boolean getBloqueioEcra() { return bloqueioEcra; }
    public void setBloqueioEcra(Boolean bloqueioEcra) { this.bloqueioEcra = bloqueioEcra; }

    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }

    public LocalDateTime getDataAtualizacao() { return dataAtualizacao; }
    public void setDataAtualizacao(LocalDateTime dataAtualizacao) { this.dataAtualizacao = dataAtualizacao; }

    public LocalDateTime getUltimoAcesso() { return ultimoAcesso; }
    public void setUltimoAcesso(LocalDateTime ultimoAcesso) { this.ultimoAcesso = ultimoAcesso; }

    public Boolean getAtivo() { return ativo; }
    public void setAtivo(Boolean ativo) { this.ativo = ativo; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Utilizador that = (Utilizador) o;
        return Objects.equals(idUtilizador, that.idUtilizador);
    }

    @Override
    public int hashCode() { return Objects.hash(idUtilizador); }

    @Override
    public String toString() {
        return "Utilizador{idUtilizador=" + idUtilizador + ", email='" + email + "', perfil='" + perfil + "'}";
    }
}
