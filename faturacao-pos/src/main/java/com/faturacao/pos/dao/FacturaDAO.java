package com.faturacao.pos.dao;

import com.faturacao.pos.model.Factura;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class FacturaDAO {

    @PersistenceContext(unitName = "posPU")
    private EntityManager entityManager;

    public List<Factura> listarTodas() {
        return entityManager.createQuery("select f from Factura f order by f.dataEmissao desc", Factura.class).getResultList();
    }

    @Transactional
    public Factura guardar(Factura factura) {
        return entityManager.merge(factura);
    }
}
