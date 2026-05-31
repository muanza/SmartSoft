package com.smartsoft.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "comunicacoes_massa")
public class ComunicacaoMassa extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "id_comunicacao")
    private UUID id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_utilizador_crm", nullable = false)
    private UtilizadorCrm utilizadorCrm;

    @NotBlank
    @Size(max = 255)
    @Column(name = "titulo", nullable = false)
    private String titulo;

    @NotBlank
    @Column(name = "mensagem", nullable = false)
    private String mensagem;

    @Column(name = "data_envio")
    private LocalDateTime dataEnvio;

    @Override
    public UUID getId() {
        return id;
    }
}
