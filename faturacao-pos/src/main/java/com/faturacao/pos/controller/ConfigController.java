package com.faturacao.pos.controller;

import com.faturacao.pos.service.BackupService;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class ConfigController implements Serializable {

    @Inject
    private BackupService backupService;

    private String crmApiUrl = "http://localhost:8080/faturacao-crm/api/licencas";
    private String apiKey = "APIKEY-500000001";
    private String tenantNif;
    private String pastaBackup = "/var/backups/smartsoft";

    public String getUltimoPlanoBackup() {
        return backupService.gerarBackupIncremental(tenantNif, pastaBackup);
    }

    public String getCrmApiUrl() { return crmApiUrl; }
    public void setCrmApiUrl(String crmApiUrl) { this.crmApiUrl = crmApiUrl; }
    public String getApiKey() { return apiKey; }
    public void setApiKey(String apiKey) { this.apiKey = apiKey; }
    public String getTenantNif() { return tenantNif; }
    public void setTenantNif(String tenantNif) { this.tenantNif = tenantNif; }
    public String getPastaBackup() { return pastaBackup; }
    public void setPastaBackup(String pastaBackup) { this.pastaBackup = pastaBackup; }
}
