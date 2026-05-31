package com.faturacao.crm.controller;

import com.faturacao.crm.dao.ParceiroDAO;
import com.faturacao.crm.model.Parceiro;

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
public class ParceiroController implements Serializable {

    @Inject
    private ParceiroDAO parceiroDAO;

    private List<Parceiro> parceiros;
    private Parceiro parceiroForm;

    @PostConstruct
    public void init() {
        parceiros = new ArrayList<>(parceiroDAO.listarTodos());
        parceiroForm = new Parceiro();
    }

    public void salvar() {
        parceiroDAO.guardar(parceiroForm);
        parceiros = new ArrayList<>(parceiroDAO.listarTodos());
        parceiroForm = new Parceiro();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Parceiro guardado."));
    }

    public List<Parceiro> getParceiros() { return parceiros; }
    public Parceiro getParceiroForm() { return parceiroForm; }
    public void setParceiroForm(Parceiro parceiroForm) { this.parceiroForm = parceiroForm; }
}
