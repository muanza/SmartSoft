package com.faturacao.crm.controller;

import com.faturacao.crm.model.MaquinaLicenciada;
import com.faturacao.crm.service.LicencaService;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class MaquinaController implements Serializable {

    @Inject
    private LicencaService licencaService;

    private List<MaquinaLicenciada> maquinas;
    private MaquinaLicenciada maquinaForm;

    @PostConstruct
    public void init() {
        maquinas = new ArrayList<>();
        maquinaForm = new MaquinaLicenciada();
        MaquinaLicenciada exemplo = new MaquinaLicenciada();
        exemplo.setTenantNif("500000001");
        exemplo.setSerialHardware("HW-DEMO-001");
        exemplo.setNomeMaquina("POS-DEMO");
        exemplo.setIpLocal("192.168.0.10");
        exemplo.setUltimaSincronizacao(LocalDateTime.now());
        maquinas.add(exemplo);
    }

    public void registar() {
        maquinas.add(licencaService.registarMaquina(maquinaForm.getTenantNif(), maquinaForm.getSerialHardware(), maquinaForm.getNomeMaquina(), maquinaForm.getIpLocal()));
        maquinaForm = new MaquinaLicenciada();
    }

    public List<MaquinaLicenciada> getMaquinas() { return maquinas; }
    public MaquinaLicenciada getMaquinaForm() { return maquinaForm; }
    public void setMaquinaForm(MaquinaLicenciada maquinaForm) { this.maquinaForm = maquinaForm; }
}
