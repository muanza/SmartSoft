package com.smartsoft.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "movimentos_stock")
public class MovimentoStock extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "id_movimento_stock")
    private UUID id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tenant", nullable = false)
    private Tenant tenant;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_produto", nullable = false)
    private Produto produto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_utilizador")
    private Utilizador utilizador;

    @NotBlank
    @Column(name = "tipo_movimento", nullable = false)
    private String tipoMovimento;

    @Column(name = "quantidade", nullable = false)
    private BigDecimal quantidade;

    @Column(name = "data_movimento")
    private LocalDateTime dataMovimento;

    @Override
    public UUID getId() {
        return id;
    }
}
