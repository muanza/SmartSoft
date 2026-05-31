package com.smartsoft.entity;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "stock")
public class Stock extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "id_stock")
    private UUID id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tenant", nullable = false)
    private Tenant tenant;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_produto", nullable = false)
    private Produto produto;

    @Min(0)
    @Column(name = "quantidade_disponivel", nullable = false)
    private BigDecimal quantidadeDisponivel;

    @Override
    public UUID getId() {
        return id;
    }
}
