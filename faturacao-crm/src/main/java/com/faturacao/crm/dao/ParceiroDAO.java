package com.faturacao.crm.dao;

import com.faturacao.crm.model.Parceiro;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ParceiroDAO extends BaseDAO<Parceiro> {

    public ParceiroDAO() {
        super(Parceiro.class);
    }
}
