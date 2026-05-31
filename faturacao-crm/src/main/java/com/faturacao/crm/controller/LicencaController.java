package com.faturacao.crm.controller;

import com.faturacao.crm.dao.LicencaDAO;
import com.faturacao.crm.model.Licenca;
import com.faturacao.crm.service.LicencaService;

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
public class LicencaController implements Serializable {

    @Inject
    private LicencaDAO licencaDAO;

    @Inject
    private LicencaService licencaService;

    private List<Licenca> licencas;
    private Licenca licencaForm;

    @PostConstruct
    public void init() {
        licencas = new ArrayList<>(licencaDAO.listarTodos());
        licencaForm = new Licenca();
    }

    public void salvar() {
        licencaService.emitirLicenca(licencaForm);
        licencas = new ArrayList<>(licencaDAO.listarTodos());
        licencaForm = new Licenca();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Licença actualizada."));
    }

    public List<Licenca> getLicencas() { return licencas; }
    public Licenca getLicencaForm() { return licencaForm; }
    public void setLicencaForm(Licenca licencaForm) { this.licencaForm = licencaForm; }
}
