package com.smartsoft.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "parceiro_tenant",
    uniqueConstraints = @UniqueConstraint(columnNames = {"id_parceiro", "id_tenant"}),
    indexes = {
        @Index(name = "idx_parceiro_tenant_parceiro", columnList = "id_parceiro"),
        @Index(name = "idx_parceiro_tenant_tenant", columnList = "id_tenant")
    })
public class ParceiroTenant {

    @Id
    @GeneratedValue
    @Column(name = "id_parceiro_tenant", updatable = false, nullable = false)
    private UUID idParceiroTenant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_parceiro", nullable = false)
    private Parceiro parceiro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tenant", nullable = false)
    private Tenant tenant;

    @Column(name = "data_associacao")
    private LocalDateTime dataAssociacao;

    @Column(name = "ativo", columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean ativo = true;

    @PrePersist
    protected void onCreate() {
        dataAssociacao = LocalDateTime.now();
    }

    public ParceiroTenant() {}

    public UUID getIdParceiroTenant() { return idParceiroTenant; }
    public void setIdParceiroTenant(UUID idParceiroTenant) { this.idParceiroTenant = idParceiroTenant; }

    public Parceiro getParceiro() { return parceiro; }
    public void setParceiro(Parceiro parceiro) { this.parceiro = parceiro; }

    public Tenant getTenant() { return tenant; }
    public void setTenant(Tenant tenant) { this.tenant = tenant; }

    public LocalDateTime getDataAssociacao() { return dataAssociacao; }
    public void setDataAssociacao(LocalDateTime dataAssociacao) { this.dataAssociacao = dataAssociacao; }

    public Boolean getAtivo() { return ativo; }
    public void setAtivo(Boolean ativo) { this.ativo = ativo; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParceiroTenant that = (ParceiroTenant) o;
        return Objects.equals(idParceiroTenant, that.idParceiroTenant);
    }

    @Override
    public int hashCode() { return Objects.hash(idParceiroTenant); }

    @Override
    public String toString() {
        return "ParceiroTenant{idParceiroTenant=" + idParceiroTenant + "}";
    }
}
