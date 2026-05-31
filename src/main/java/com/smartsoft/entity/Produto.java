package com.smartsoft.entity;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "produtos")
public class Produto extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "id_produto")
    private UUID id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tenant", nullable = false)
    private Tenant tenant;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_categoria", nullable = false)
    private CategoriaProduto categoria;

    @NotBlank
    @Size(max = 255)
    @Column(name = "nome_produto", nullable = false)
    private String nomeProduto;

    @NotBlank
    @Size(max = 50)
    @Column(name = "codigo_produto", nullable = false)
    private String codigoProduto;

    @NotNull
    @Min(0)
    @Column(name = "preco_unitario", nullable = false)
    private BigDecimal precoUnitario;

    @Column(name = "taxa_iva")
    private BigDecimal taxaIva;

    @Override
    public UUID getId() {
        return id;
    }
}
