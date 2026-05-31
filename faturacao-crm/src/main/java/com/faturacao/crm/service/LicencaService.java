package com.faturacao.crm.service;

import com.faturacao.crm.dao.LicencaDAO;
import com.faturacao.crm.model.Licenca;
import com.faturacao.crm.model.MaquinaLicenciada;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;

@ApplicationScoped
public class LicencaService {

    @Inject
    private LicencaDAO licencaDAO;

    @PersistenceContext(unitName = "crmPU")
    private EntityManager entityManager;

    public boolean validarLicenca(String tenantNif) {
        Licenca licenca = licencaDAO.encontrarActiva(tenantNif);
        return licenca != null && !licenca.getDataFim().isBefore(LocalDate.now());
    }

    @Transactional
    public Licenca emitirLicenca(Licenca licenca) {
        licenca.setUltimoPing(LocalDateTime.now());
        if (licenca.getChaveActivacao() == null || licenca.getChaveActivacao().isBlank()) {
            licenca.setChaveActivacao("CRM-" + licenca.getTenantNif() + "-ATIVA");
        }
        return entityManager.merge(licenca);
    }

    @Transactional
    public MaquinaLicenciada registarMaquina(String tenantNif, String serialHardware, String nomeMaquina, String ipLocal) {
        MaquinaLicenciada maquina = new MaquinaLicenciada();
        maquina.setTenantNif(tenantNif);
        maquina.setSerialHardware(serialHardware);
        maquina.setNomeMaquina(nomeMaquina);
        maquina.setIpLocal(ipLocal);
        maquina.setUltimaSincronizacao(LocalDateTime.now());
        return entityManager.merge(maquina);
    }
}
