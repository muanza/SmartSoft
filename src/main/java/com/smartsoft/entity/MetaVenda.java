package com.smartsoft.entity;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "metas_vendas")
public class MetaVenda extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "id_meta")
    private UUID id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tenant", nullable = false)
    private Tenant tenant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_utilizador")
    private Utilizador utilizador;

    @Min(0)
    @Column(name = "valor_meta", nullable = false)
    private BigDecimal valorMeta;

    @Column(name = "periodo_inicio")
    private LocalDate periodoInicio;

    @Column(name = "periodo_fim")
    private LocalDate periodoFim;

    @Override
    public UUID getId() {
        return id;
    }
}
