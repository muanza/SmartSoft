package com.smartsoft.entity;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "linhas_factura")
public class LinhaFactura extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "id_linha_factura")
    private UUID id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_factura", nullable = false)
    private Factura factura;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_produto", nullable = false)
    private Produto produto;

    @Min(1)
    @Column(name = "quantidade", nullable = false)
    private BigDecimal quantidade;

    @Column(name = "preco_unitario", nullable = false)
    private BigDecimal precoUnitario;

    @Column(name = "total_linha", nullable = false)
    private BigDecimal totalLinha;

    @Override
    public UUID getId() {
        return id;
    }
}
