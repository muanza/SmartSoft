package com.smartsoft.entity;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "configuracoes_empresa")
public class ConfiguracaoEmpresa extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "id_configuracao")
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tenant", nullable = false, unique = true)
    private Tenant tenant;

    @Column(name = "proximo_numero_factura")
    private Integer proximoNumeroFactura;

    @Min(0)
    @Max(10)
    @Column(name = "casas_decimais")
    private Integer casasDecimais;

    @Column(name = "margem_lucro_padrao")
    private BigDecimal margemLucroPadrao;

    @Override
    public UUID getId() {
        return id;
    }
}
