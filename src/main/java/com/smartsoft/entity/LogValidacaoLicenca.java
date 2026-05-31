package com.smartsoft.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "log_validacao_licenca")
public class LogValidacaoLicenca extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "id_log_validacao")
    private UUID id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_licenca", nullable = false)
    private Licenca licenca;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tenant", nullable = false)
    private Tenant tenant;

    @Column(name = "data_validacao")
    private LocalDateTime dataValidacao;

    @Column(name = "resultado_validacao")
    private String resultadoValidacao;

    @Override
    public UUID getId() {
        return id;
    }
}
