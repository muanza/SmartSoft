package com.smartsoft.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "log_auditoria")
public class LogAuditoria extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "id_log")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tenant")
    private Tenant tenant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_utilizador")
    private Utilizador utilizador;

    @Column(name = "acao")
    private String acao;

    @Column(name = "data_acao")
    private LocalDateTime dataAcao;

    @Override
    public UUID getId() {
        return id;
    }
}
