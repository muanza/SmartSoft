package com.faturacao.crm.controller;

import com.faturacao.crm.model.Comunicado;
import com.faturacao.crm.service.EmailService;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class ComunicadoController implements Serializable {

    @Inject
    private EmailService emailService;

    private List<Comunicado> comunicados;
    private Comunicado comunicadoForm;
    private String ultimoEnvio;

    @PostConstruct
    public void init() {
        comunicados = new ArrayList<>();
        comunicadoForm = new Comunicado();
    }

    public void enviar() {
        comunicados.add(comunicadoForm);
        ultimoEnvio = emailService.prepararEnvio(comunicadoForm, "todas-empresas@tenant.local");
        comunicadoForm = new Comunicado();
    }

    public List<Comunicado> getComunicados() { return comunicados; }
    public Comunicado getComunicadoForm() { return comunicadoForm; }
    public void setComunicadoForm(Comunicado comunicadoForm) { this.comunicadoForm = comunicadoForm; }
    public String getUltimoEnvio() { return ultimoEnvio; }
}
