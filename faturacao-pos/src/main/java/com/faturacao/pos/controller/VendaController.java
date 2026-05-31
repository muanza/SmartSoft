package com.faturacao.pos.controller;

import com.faturacao.pos.model.Factura;
import com.faturacao.pos.model.SerieFactura;
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

    private String clienteNome = "Consumidor Final";
    private String idioma = "pt";
    private Factura ultimaFactura;

    public void finalizarVenda() {
        SerieFactura serie = new SerieFactura();
        serie.setTenantNif(posController.getTenantNif());
        ultimaFactura = facturaService.emitir(posController.getTenantNif(), idioma, clienteNome, posController.getCarrinho(), serie);
        syncService.sincronizarFactura(ultimaFactura, "http://localhost:8080/faturacao-crm/api/licencas", "APIKEY-500000001");
        posController.getCarrinho().clear();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Factura emitida: " + ultimaFactura.getNumero()));
    }

    public String getClienteNome() { return clienteNome; }
    public void setClienteNome(String clienteNome) { this.clienteNome = clienteNome; }
    public String getIdioma() { return idioma; }
    public void setIdioma(String idioma) { this.idioma = idioma; }
    public Factura getUltimaFactura() { return ultimaFactura; }
}
