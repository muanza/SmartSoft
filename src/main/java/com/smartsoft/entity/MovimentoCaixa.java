package com.smartsoft.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "movimentos_caixa")
public class MovimentoCaixa extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "id_movimento_caixa")
    private UUID id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tenant", nullable = false)
    private Tenant tenant;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_caixa", nullable = false)
    private Caixa caixa;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_utilizador", nullable = false)
    private Utilizador utilizador;

    @NotBlank
    @Column(name = "tipo_movimento", nullable = false)
    private String tipoMovimento;

    @Column(name = "valor", nullable = false)
    private BigDecimal valor;

    @Column(name = "data_movimento")
    private LocalDateTime dataMovimento;

    @Override
    public UUID getId() {
        return id;
    }
}
