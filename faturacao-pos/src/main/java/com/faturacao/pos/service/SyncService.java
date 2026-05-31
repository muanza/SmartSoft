package com.faturacao.pos.service;

import com.faturacao.pos.model.Factura;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SyncService {

    public boolean sincronizarFactura(Factura factura, String crmApiUrl, String apiKey) {
        return crmApiUrl != null && !crmApiUrl.isBlank() && apiKey != null && !apiKey.isBlank() && factura != null;
    }
}
