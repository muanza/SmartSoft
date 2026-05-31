package com.faturacao.pos.dao;

import com.faturacao.pos.model.Cliente;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class ClienteDAO {

    @PersistenceContext(unitName = "posPU")
    private EntityManager entityManager;

    public List<Cliente> listarTodos() {
        return entityManager.createQuery("select c from Cliente c order by c.nome", Cliente.class).getResultList();
    }

    @Transactional
    public Cliente guardar(Cliente cliente) {
        return entityManager.merge(cliente);
    }
}
