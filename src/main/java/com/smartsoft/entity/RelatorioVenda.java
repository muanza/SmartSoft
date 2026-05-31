package com.smartsoft.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "relatorios_vendas")
public class RelatorioVenda extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "id_relatorio")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tenant")
    private Tenant tenant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_utilizador")
    private Utilizador utilizador;

    @Column(name = "data_relatorio")
    private LocalDate dataRelatorio;

    @Column(name = "total_vendas")
    private BigDecimal totalVendas;

    @Override
    public UUID getId() {
        return id;
    }
}
