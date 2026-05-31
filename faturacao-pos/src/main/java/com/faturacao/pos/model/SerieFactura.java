package com.faturacao.pos.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Table(name = "series_factura")

public class SerieFactura implements Serializable {

    @Id
    @Column(length = 36)
    private String id = UUID.randomUUID().toString();
    @Column(name = "tenant_nif", nullable = false, length = 20)
    private String tenantNif;
    @Column(nullable = false, length = 30)
    private String codigo = "FT";
    @Column(name = "tipo_documento", nullable = false, length = 30)
    private String tipoDocumento = "FACTURA";
    @Column(name = "proximo_numero", nullable = false)
    private Integer proximoNumero = 1;
    @Column(nullable = false)
    private Boolean activo = Boolean.TRUE;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTenantNif() {
        return tenantNif;
    }

    public void setTenantNif(String tenantNif) {
        this.tenantNif = tenantNif;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public Integer getProximoNumero() {
        return proximoNumero;
    }

    public void setProximoNumero(Integer proximoNumero) {
        this.proximoNumero = proximoNumero;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

}
