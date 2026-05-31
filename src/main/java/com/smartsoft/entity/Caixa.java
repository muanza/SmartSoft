package com.smartsoft.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "caixas")
public class Caixa extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "id_caixa")
    private UUID id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tenant", nullable = false)
    private Tenant tenant;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_utilizador", nullable = false)
    private Utilizador utilizador;

    @NotBlank
    @Column(name = "numero_caixa", nullable = false)
    private String numeroCaixa;

    @Column(name = "saldo_abertura", nullable = false)
    private BigDecimal saldoAbertura;

    @Column(name = "data_abertura")
    private LocalDateTime dataAbertura;

    @Override
    public UUID getId() {
        return id;
    }
}
