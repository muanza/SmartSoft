package com.smartsoft.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "parceiro_tenant")
public class ParceiroTenant extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "id_relacao")
    private UUID id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tenant", nullable = false)
    private Tenant tenant;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_parceiro", nullable = false)
    private Parceiro parceiro;

    @Column(name = "data_associacao")
    private LocalDate dataAssociacao;

    @Override
    public UUID getId() {
        return id;
    }
}
