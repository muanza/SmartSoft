package com.smartsoft.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "log_backup")
public class LogBackup extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "id_backup")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tenant")
    private Tenant tenant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_utilizador")
    private Utilizador utilizador;

    @Column(name = "tipo_backup")
    private String tipoBackup;

    @Column(name = "data_inicio")
    private LocalDateTime dataInicio;

    @Column(name = "estado_backup")
    private String estadoBackup;

    @Override
    public UUID getId() {
        return id;
    }
}
