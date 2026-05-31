package com.faturacao.crm.controller;

import com.faturacao.crm.dao.TenantDAO;
import com.faturacao.crm.model.Tenant;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class TenantController implements Serializable {

    @Inject
    private TenantDAO tenantDAO;

    private List<Tenant> tenants;
    private Tenant tenantForm;

    @PostConstruct
    public void init() {
        tenants = new ArrayList<>(tenantDAO.listarTodos());
        tenantForm = new Tenant();
    }

    public void salvar() {
        tenantDAO.guardar(tenantForm);
        tenants = new ArrayList<>(tenantDAO.listarTodos());
        tenantForm = new Tenant();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Tenant guardado com sucesso."));
    }

    public List<Tenant> getTenants() { return tenants; }
    public Tenant getTenantForm() { return tenantForm; }
    public void setTenantForm(Tenant tenantForm) { this.tenantForm = tenantForm; }
}
