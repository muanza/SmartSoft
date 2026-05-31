package com.faturacao.pos.controller;

import com.faturacao.pos.model.Factura;
import com.faturacao.pos.service.FacturaService;
import com.faturacao.pos.service.SyncService;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class VendaController implements Serializable {

    @Inject
    private POSController posController;

    @Inject
    private FacturaService facturaService;

    @Inject
    private SyncService syncService;

    @Inject
    private ConfigController configController;

    private String clienteNome = "Consumidor Final";
    private String idioma = "pt";
    private Factura ultimaFactura;

    public void finalizarVenda() {
        ultimaFactura = facturaService.emitir(posController.getTenantNif(), idioma, clienteNome, posController.getCarrinho());
        boolean sincronizada = syncService.sincronizarFactura(ultimaFactura, configController.getCrmApiUrl(), configController.getApiKey());
        posController.getCarrinho().clear();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Factura emitida: " + ultimaFactura.getNumero()));
        if (!sincronizada) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Sincronização pendente.", null));
        }
    }

    public String getClienteNome() { return clienteNome; }
    public void setClienteNome(String clienteNome) { this.clienteNome = clienteNome; }
    public String getIdioma() { return idioma; }
    public void setIdioma(String idioma) { this.idioma = idioma; }
    public Factura getUltimaFactura() { return ultimaFactura; }
}
