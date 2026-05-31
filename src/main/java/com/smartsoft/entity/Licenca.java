package com.smartsoft.entity;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "licencas", indexes = {
    @Index(name = "idx_licencas_tenant", columnList = "id_tenant"),
    @Index(name = "idx_licencas_chave", columnList = "chave_licenca"),
    @Index(name = "idx_licencas_ativo", columnList = "ativo"),
    @Index(name = "idx_licencas_expiracao", columnList = "data_expiracao")
})
public class Licenca {

    @Id
    @GeneratedValue
    @Column(name = "id_licenca", updatable = false, nullable = false)
    private UUID idLicenca;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tenant", nullable = false)
    private Tenant tenant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_parceiro")
    private Parceiro parceiro;

    @NotBlank
    @Size(max = 255)
    @Column(name = "chave_licenca", unique = true, nullable = false)
    private String chaveLicenca;

    @NotBlank
    @Pattern(regexp = "basico|profissional|empresa")
    @Column(name = "tipo_licenca", nullable = false, length = 50)
    private String tipoLicenca;

    @Size(max = 20)
    @Column(name = "versao_software", length = 20)
    private String versaoSoftware;

    @NotNull
    @Column(name = "data_inicio", nullable = false)
    private LocalDate dataInicio;

    @NotNull
    @Column(name = "data_expiracao", nullable = false)
    private LocalDate dataExpiracao;

    @Min(1)
    @Column(name = "num_utilizadores_permitidos", columnDefinition = "INT DEFAULT 5")
    private Integer numUtilizadoresPermitidos = 5;

    @Min(1)
    @Column(name = "limite_facturas_diarias", columnDefinition = "INT DEFAULT 1000")
    private Integer limiteFacturasDiarias = 1000;

    @Column(name = "ativo", columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean ativo = true;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    @Column(name = "bloqueada", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean bloqueada = false;

    @Column(name = "motivo_bloqueio", columnDefinition = "TEXT")
    private String motivoBloqueio;

    @PrePersist
    protected void onCreate() {
        dataCriacao = LocalDateTime.now();
        dataAtualizacao = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        dataAtualizacao = LocalDateTime.now();
    }

    public Licenca() {}

    public UUID getIdLicenca() { return idLicenca; }
    public void setIdLicenca(UUID idLicenca) { this.idLicenca = idLicenca; }

    public Tenant getTenant() { return tenant; }
    public void setTenant(Tenant tenant) { this.tenant = tenant; }

    public Parceiro getParceiro() { return parceiro; }
    public void setParceiro(Parceiro parceiro) { this.parceiro = parceiro; }

    public String getChaveLicenca() { return chaveLicenca; }
    public void setChaveLicenca(String chaveLicenca) { this.chaveLicenca = chaveLicenca; }

    public String getTipoLicenca() { return tipoLicenca; }
    public void setTipoLicenca(String tipoLicenca) { this.tipoLicenca = tipoLicenca; }

    public String getVersaoSoftware() { return versaoSoftware; }
    public void setVersaoSoftware(String versaoSoftware) { this.versaoSoftware = versaoSoftware; }

    public LocalDate getDataInicio() { return dataInicio; }
    public void setDataInicio(LocalDate dataInicio) { this.dataInicio = dataInicio; }

    public LocalDate getDataExpiracao() { return dataExpiracao; }
    public void setDataExpiracao(LocalDate dataExpiracao) { this.dataExpiracao = dataExpiracao; }

    public Integer getNumUtilizadoresPermitidos() { return numUtilizadoresPermitidos; }
    public void setNumUtilizadoresPermitidos(Integer numUtilizadoresPermitidos) { this.numUtilizadoresPermitidos = numUtilizadoresPermitidos; }

    public Integer getLimiteFacturasDiarias() { return limiteFacturasDiarias; }
    public void setLimiteFacturasDiarias(Integer limiteFacturasDiarias) { this.limiteFacturasDiarias = limiteFacturasDiarias; }

    public Boolean getAtivo() { return ativo; }
    public void setAtivo(Boolean ativo) { this.ativo = ativo; }

    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }

    public LocalDateTime getDataAtualizacao() { return dataAtualizacao; }
    public void setDataAtualizacao(LocalDateTime dataAtualizacao) { this.dataAtualizacao = dataAtualizacao; }

    public Boolean getBloqueada() { return bloqueada; }
    public void setBloqueada(Boolean bloqueada) { this.bloqueada = bloqueada; }

    public String getMotivoBloqueio() { return motivoBloqueio; }
    public void setMotivoBloqueio(String motivoBloqueio) { this.motivoBloqueio = motivoBloqueio; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Licenca licenca = (Licenca) o;
        return Objects.equals(idLicenca, licenca.idLicenca);
    }

    @Override
    public int hashCode() { return Objects.hash(idLicenca); }

    @Override
    public String toString() {
        return "Licenca{idLicenca=" + idLicenca + ", chaveLicenca='" + chaveLicenca + "', tipoLicenca='" + tipoLicenca + "'}";
    }
}
