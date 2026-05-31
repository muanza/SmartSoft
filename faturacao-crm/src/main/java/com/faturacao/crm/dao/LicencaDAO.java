package com.faturacao.crm.dao;

import com.faturacao.crm.model.Licenca;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class LicencaDAO extends BaseDAO<Licenca> {

    public LicencaDAO() {
        super(Licenca.class);
    }

    public List<Licenca> listarPorTenant(String tenantNif) {
        return em().createQuery("select l from Licenca l where l.tenantNif = :tenant order by l.dataFim desc", Licenca.class)
                .setParameter("tenant", tenantNif)
                .getResultList();
    }

    public Licenca encontrarActiva(String tenantNif) {
        List<Licenca> licencas = em().createQuery("select l from Licenca l where l.tenantNif = :tenant and l.estado = :estado", Licenca.class)
                .setParameter("tenant", tenantNif)
                .setParameter("estado", "ACTIVA")
                .setMaxResults(1)
                .getResultList();
        return licencas.isEmpty() ? null : licencas.get(0);
    }
}
