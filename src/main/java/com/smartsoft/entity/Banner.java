package com.smartsoft.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "banners")
public class Banner extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "id_banner")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_parceiro")
    private Parceiro parceiro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tenant")
    private Tenant tenant;

    @NotBlank
    @Size(max = 255)
    @Column(name = "titulo", nullable = false)
    private String titulo;

    @NotBlank
    @Column(name = "conteudo", nullable = false)
    private String conteudo;

    @NotNull
    @Column(name = "data_inicio_exibicao")
    private LocalDate dataInicioExibicao;

    @NotNull
    @Column(name = "data_fim_exibicao")
    private LocalDate dataFimExibicao;

    @Override
    public UUID getId() {
        return id;
    }
}
