package com.smartsoft.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "pagamentos_factura")
public class PagamentoFactura extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "id_pagamento")
    private UUID id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_factura", nullable = false)
    private Factura factura;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tenant", nullable = false)
    private Tenant tenant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_utilizador")
    private Utilizador utilizador;

    @NotBlank
    @Column(name = "metodo_pagamento", nullable = false)
    private String metodoPagamento;

    @Column(name = "valor_pago", nullable = false)
    private BigDecimal valorPago;

    @Column(name = "data_pagamento")
    private LocalDateTime dataPagamento;

    @Override
    public UUID getId() {
        return id;
    }
}
