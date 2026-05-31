package com.faturacao.crm.controller;

import com.faturacao.crm.dao.LicencaDAO;
import com.faturacao.crm.dao.ParceiroDAO;
import com.faturacao.crm.dao.TenantDAO;
import com.faturacao.crm.service.BackupService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class DashboardController {

    @Inject
    private TenantDAO tenantDAO;

    @Inject
    private LicencaDAO licencaDAO;

    @Inject
    private ParceiroDAO parceiroDAO;

    @Inject
    private BackupService backupService;

    public int getTotalTenants() {
        return tenantDAO.listarTodos().size();
    }

    public int getTotalLicencas() {
        return licencaDAO.listarTodos().size();
    }

    public int getTotalParceiros() {
        return parceiroDAO.listarTodos().size();
    }

    public String getEstadoBackups() {
        return backupService.gerarPlanoBackup("500000001");
    }
}
