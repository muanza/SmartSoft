package com.faturacao.crm.service;

import com.faturacao.crm.model.Comunicado;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EmailService {

    public String prepararEnvio(Comunicado comunicado, String destinatario) {
        return "Comunicado '" + comunicado.getAssunto() + "' preparado para " + destinatario;
    }
}
