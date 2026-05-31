package com.faturacao.pos.controller;

import com.faturacao.pos.model.Utilizador;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class UtilizadorController implements Serializable {

    @Inject
    private POSController posController;

    private List<Utilizador> utilizadores;
    private Utilizador utilizadorForm;

    @PostConstruct
    public void init() {
        utilizadores = new ArrayList<>();
        utilizadorForm = new Utilizador();
        utilizadorForm.setTenantNif(posController.getTenantNif());
    }

    public void salvar() {
        utilizadores.add(utilizadorForm);
        utilizadorForm = new Utilizador();
        utilizadorForm.setTenantNif(posController.getTenantNif());
    }

    public List<Utilizador> getUtilizadores() { return utilizadores; }
    public Utilizador getUtilizadorForm() { return utilizadorForm; }
    public void setUtilizadorForm(Utilizador utilizadorForm) { this.utilizadorForm = utilizadorForm; }
}
