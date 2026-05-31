package com.smartsoft.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "facturas")
public class Factura extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "id_factura")
    private UUID id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tenant", nullable = false)
    private Tenant tenant;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_utilizador", nullable = false)
    private Utilizador utilizador;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_caixa")
    private Caixa caixa;

    @NotBlank
    @Size(max = 50)
    @Column(name = "numero_factura", nullable = false)
    private String numeroFactura;

    @NotNull
    @Column(name = "data_factura", nullable = false)
    private LocalDate dataFactura;

    @NotBlank
    @Size(max = 50)
    @Column(name = "tipo_factura", nullable = false)
    private String tipoFactura;

    @Column(name = "total_liquido", nullable = false)
    private BigDecimal totalLiquido;

    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL)
    private Set<LinhaFactura> linhas = new LinkedHashSet<>();

    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL)
    private Set<PagamentoFactura> pagamentos = new LinkedHashSet<>();

    @Override
    public UUID getId() {
        return id;
    }
}
