package com.smartsoft.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "destinatarios_comunicacao")
public class DestinatarioComunicacao extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "id_destinatario")
    private UUID id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_comunicacao", nullable = false)
    private ComunicacaoMassa comunicacao;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tenant", nullable = false)
    private Tenant tenant;

    @Column(name = "data_entrega")
    private LocalDateTime dataEntrega;

    @Override
    public UUID getId() {
        return id;
    }
}
