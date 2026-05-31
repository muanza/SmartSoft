package com.smartsoft.entity;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "configuracoes_empresa", indexes = {
    @Index(name = "idx_configuracoes_empresa_tenant", columnList = "id_tenant")
})
public class ConfiguracaoEmpresa {

    @Id
    @GeneratedValue
    @Column(name = "id_configuracao", updatable = false, nullable = false)
    private UUID idConfiguracao;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tenant", nullable = false, unique = true)
    private Tenant tenant;

    @Size(max = 20)
    @Column(name = "numero_serie_inicial_factura", length = 20)
    private String numeroSerieInicialFactura;

    @Min(1)
    @Column(name = "proximo_numero_factura", columnDefinition = "INT DEFAULT 1")
    private Integer proximoNumeroFactura = 1;

    @Size(max = 100)
    @Column(name = "formato_numero_factura", length = 100, columnDefinition = "VARCHAR(100) DEFAULT 'NNNNNNNN/YYYY'")
    private String formatoNumeroFactura = "NNNNNNNN/YYYY";

    @Size(max = 5)
    @Column(name = "separador_milhares", length = 5, columnDefinition = "VARCHAR(5) DEFAULT ','")
    private String separadorMilhares = ",";

    @Size(max = 5)
    @Column(name = "separador_decimal", length = 5, columnDefinition = "VARCHAR(5) DEFAULT '.'")
    private String separadorDecimal = ".";

    @Min(0) @Max(6)
    @Column(name = "casas_decimais", columnDefinition = "INT DEFAULT 2")
    private Integer casasDecimais = 2;

    @Column(name = "taxa_iva_padrao", length = 10, columnDefinition = "VARCHAR(10) DEFAULT '17%'")
    private String taxaIvaPadrao = "17%";

    @Size(max = 3)
    @Column(name = "moeda_padrao", length = 3, columnDefinition = "VARCHAR(3) DEFAULT 'AOA'")
    private String moedaPadrao = "AOA";

    @Size(max = 500)
    @Column(name = "url_api_crm", length = 500)
    private String urlApiCrm;

    @Size(max = 255)
    @Column(name = "chave_api_crm", length = 255)
    private String chaveApiCrm;

    @Column(name = "backup_automatico_ativo", columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean backupAutomaticoAtivo = true;

    @Min(1)
    @Column(name = "intervalo_backup", columnDefinition = "INT DEFAULT 24")
    private Integer intervaloBackup = 24;

    @Size(max = 500)
    @Column(name = "caminho_backup_local", length = 500)
    private String caminhoBackupLocal;

    @Size(max = 500)
    @Column(name = "caminho_backup_cloud", length = 500)
    private String caminhoBackupCloud;

    @Size(max = 500)
    @Column(name = "url_cloud_storage", length = 500)
    private String urlCloudStorage;

    @Size(max = 500)
    @Column(name = "credencial_cloud_storage", length = 500)
    private String credencialCloudStorage;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    @PrePersist
    protected void onCreate() {
        dataCriacao = LocalDateTime.now();
        dataAtualizacao = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        dataAtualizacao = LocalDateTime.now();
    }

    public ConfiguracaoEmpresa() {}

    public UUID getIdConfiguracao() { return idConfiguracao; }
    public void setIdConfiguracao(UUID idConfiguracao) { this.idConfiguracao = idConfiguracao; }

    public Tenant getTenant() { return tenant; }
    public void setTenant(Tenant tenant) { this.tenant = tenant; }

    public String getNumeroSerieInicialFactura() { return numeroSerieInicialFactura; }
    public void setNumeroSerieInicialFactura(String numeroSerieInicialFactura) { this.numeroSerieInicialFactura = numeroSerieInicialFactura; }

    public Integer getProximoNumeroFactura() { return proximoNumeroFactura; }
    public void setProximoNumeroFactura(Integer proximoNumeroFactura) { this.proximoNumeroFactura = proximoNumeroFactura; }

    public String getFormatoNumeroFactura() { return formatoNumeroFactura; }
    public void setFormatoNumeroFactura(String formatoNumeroFactura) { this.formatoNumeroFactura = formatoNumeroFactura; }

    public String getSeparadorMilhares() { return separadorMilhares; }
    public void setSeparadorMilhares(String separadorMilhares) { this.separadorMilhares = separadorMilhares; }

    public String getSeparadorDecimal() { return separadorDecimal; }
    public void setSeparadorDecimal(String separadorDecimal) { this.separadorDecimal = separadorDecimal; }

    public Integer getCasasDecimais() { return casasDecimais; }
    public void setCasasDecimais(Integer casasDecimais) { this.casasDecimais = casasDecimais; }

    public String getTaxaIvaPadrao() { return taxaIvaPadrao; }
    public void setTaxaIvaPadrao(String taxaIvaPadrao) { this.taxaIvaPadrao = taxaIvaPadrao; }

    public String getMoedaPadrao() { return moedaPadrao; }
    public void setMoedaPadrao(String moedaPadrao) { this.moedaPadrao = moedaPadrao; }

    public String getUrlApiCrm() { return urlApiCrm; }
    public void setUrlApiCrm(String urlApiCrm) { this.urlApiCrm = urlApiCrm; }

    public String getChaveApiCrm() { return chaveApiCrm; }
    public void setChaveApiCrm(String chaveApiCrm) { this.chaveApiCrm = chaveApiCrm; }

    public Boolean getBackupAutomaticoAtivo() { return backupAutomaticoAtivo; }
    public void setBackupAutomaticoAtivo(Boolean backupAutomaticoAtivo) { this.backupAutomaticoAtivo = backupAutomaticoAtivo; }

    public Integer getIntervaloBackup() { return intervaloBackup; }
    public void setIntervaloBackup(Integer intervaloBackup) { this.intervaloBackup = intervaloBackup; }

    public String getCaminhoBackupLocal() { return caminhoBackupLocal; }
    public void setCaminhoBackupLocal(String caminhoBackupLocal) { this.caminhoBackupLocal = caminhoBackupLocal; }

    public String getCaminhoBackupCloud() { return caminhoBackupCloud; }
    public void setCaminhoBackupCloud(String caminhoBackupCloud) { this.caminhoBackupCloud = caminhoBackupCloud; }

    public String getUrlCloudStorage() { return urlCloudStorage; }
    public void setUrlCloudStorage(String urlCloudStorage) { this.urlCloudStorage = urlCloudStorage; }

    public String getCredencialCloudStorage() { return credencialCloudStorage; }
    public void setCredencialCloudStorage(String credencialCloudStorage) { this.credencialCloudStorage = credencialCloudStorage; }

    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }

    public LocalDateTime getDataAtualizacao() { return dataAtualizacao; }
    public void setDataAtualizacao(LocalDateTime dataAtualizacao) { this.dataAtualizacao = dataAtualizacao; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConfiguracaoEmpresa that = (ConfiguracaoEmpresa) o;
        return Objects.equals(idConfiguracao, that.idConfiguracao);
    }

    @Override
    public int hashCode() { return Objects.hash(idConfiguracao); }

    @Override
    public String toString() {
        return "ConfiguracaoEmpresa{idConfiguracao=" + idConfiguracao + "}";
    }
}
