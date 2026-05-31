package com.faturacao.pos.controller;

import com.faturacao.pos.dao.FacturaDAO;
import com.faturacao.pos.model.Factura;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class FacturaController implements Serializable {

    @Inject
    private FacturaDAO facturaDAO;

    private List<Factura> facturas;

    @PostConstruct
    public void init() {
        facturas = new ArrayList<>(facturaDAO.listarTodas());
    }

    public List<Factura> getFacturas() { return facturas; }
}
