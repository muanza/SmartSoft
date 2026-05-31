package com.faturacao.crm.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

public abstract class BaseDAO<T> {

    @PersistenceContext(unitName = "crmPU")
    private EntityManager entityManager;

    private final Class<T> entityClass;

    protected BaseDAO(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected EntityManager em() {
        return entityManager;
    }

    public T encontrar(Object id) {
        return em().find(entityClass, id);
    }

    public List<T> listarTodos() {
        return em().createQuery("select e from " + entityClass.getSimpleName() + " e", entityClass).getResultList();
    }

    @Transactional
    public T guardar(T entity) {
        return em().merge(entity);
    }

    @Transactional
    public void remover(Object id) {
        T entity = encontrar(id);
        if (entity != null) {
            em().remove(entity);
        }
    }
}
