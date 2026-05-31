package com.faturacao.pos.dao;

import com.faturacao.pos.model.Produto;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class ProdutoDAO {

    @PersistenceContext(unitName = "posPU")
    private EntityManager entityManager;

    public List<Produto> listarTodos() {
        return entityManager.createQuery("select p from Produto p order by p.nome", Produto.class).getResultList();
    }

    @Transactional
    public Produto guardar(Produto produto) {
        return entityManager.merge(produto);
    }
}
