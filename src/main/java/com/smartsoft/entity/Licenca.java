package com.smartsoft.entity;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "licencas")
public class Licenca extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "id_licenca")
    private UUID id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tenant", nullable = false)
    private Tenant tenant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_parceiro")
    private Parceiro parceiro;

    @NotBlank
    @Size(max = 255)
    @Column(name = "chave_licenca", nullable = false, unique = true)
    private String chaveLicenca;

    @NotBlank
    @Size(max = 50)
    @Column(name = "tipo_licenca", nullable = false)
    private String tipoLicenca;

    @NotNull
    @Column(name = "data_inicio", nullable = false)
    private LocalDate dataInicio;

    @NotNull
    @Column(name = "data_expiracao", nullable = false)
    private LocalDate dataExpiracao;

    @Min(1)
    @Column(name = "num_utilizadores_permitidos")
    private Integer numUtilizadoresPermitidos;

    @Min(1)
    @Column(name = "limite_facturas_diarias")
    private Integer limiteFacturasDiarias;

    @Override
    public UUID getId() {
        return id;
    }
}
