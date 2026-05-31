package com.faturacao.crm.dao;

import com.faturacao.crm.model.Tenant;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class TenantDAO extends BaseDAO<Tenant> {

    public TenantDAO() {
        super(Tenant.class);
    }

    public Tenant encontrarPorNif(String nif) {
        return encontrar(nif);
    }

    public List<Tenant> listarActivos() {
        return em().createQuery("select t from Tenant t where t.estado = :estado order by t.nomeEmpresa", Tenant.class)
                .setParameter("estado", "ACTIVO")
                .getResultList();
    }
}
