package com.smartsoft.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "log_sincronizacao_cloud")
public class LogSincronizacaoCloud extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "id_sincronizacao")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tenant")
    private Tenant tenant;

    @Column(name = "data_inicio_sincronizacao")
    private LocalDateTime dataInicioSincronizacao;

    @Column(name = "estado_sincronizacao")
    private String estadoSincronizacao;

    @Override
    public UUID getId() {
        return id;
    }
}
