package com.faturacao.crm.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Table(name = "maquinas_licenciadas")

public class MaquinaLicenciada implements Serializable {

    @Id
    @Column(length = 36)
    private String id = UUID.randomUUID().toString();
    @Column(name = "tenant_nif", nullable = false, length = 20)
    private String tenantNif;
    @Column(name = "serial_hardware", nullable = false, length = 120)
    private String serialHardware;
    @Column(name = "nome_maquina", nullable = false, length = 120)
    private String nomeMaquina;
    @Column(name = "ip_local", length = 45)
    private String ipLocal;
    @Column(nullable = false)
    private Boolean activa = Boolean.TRUE;
    @Column(name = "ultima_sincronizacao")
    private LocalDateTime ultimaSincronizacao = LocalDateTime.now();

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

    public String getSerialHardware() {
        return serialHardware;
    }

    public void setSerialHardware(String serialHardware) {
        this.serialHardware = serialHardware;
    }

    public String getNomeMaquina() {
        return nomeMaquina;
    }

    public void setNomeMaquina(String nomeMaquina) {
        this.nomeMaquina = nomeMaquina;
    }

    public String getIpLocal() {
        return ipLocal;
    }

    public void setIpLocal(String ipLocal) {
        this.ipLocal = ipLocal;
    }

    public Boolean getActiva() {
        return activa;
    }

    public void setActiva(Boolean activa) {
        this.activa = activa;
    }

    public LocalDateTime getUltimaSincronizacao() {
        return ultimaSincronizacao;
    }

    public void setUltimaSincronizacao(LocalDateTime ultimaSincronizacao) {
        this.ultimaSincronizacao = ultimaSincronizacao;
    }

}
