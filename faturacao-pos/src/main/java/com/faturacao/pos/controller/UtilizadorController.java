package com.faturacao.pos.controller;

import com.faturacao.pos.model.Utilizador;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class UtilizadorController implements Serializable {

    private List<Utilizador> utilizadores;
    private Utilizador utilizadorForm;

    @PostConstruct
    public void init() {
        utilizadores = new ArrayList<>();
        utilizadorForm = new Utilizador();
        utilizadorForm.setTenantNif("500000001");
    }

    public void salvar() {
        utilizadores.add(utilizadorForm);
        utilizadorForm = new Utilizador();
        utilizadorForm.setTenantNif("500000001");
    }

    public List<Utilizador> getUtilizadores() { return utilizadores; }
    public Utilizador getUtilizadorForm() { return utilizadorForm; }
    public void setUtilizadorForm(Utilizador utilizadorForm) { this.utilizadorForm = utilizadorForm; }
}
