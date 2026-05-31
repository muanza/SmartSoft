package com.faturacao.crm.service;

import com.faturacao.crm.model.ApiKey;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class ApiKeyService {

    private static final SecureRandom RANDOM = new SecureRandom();

    @PersistenceContext(unitName = "crmPU")
    private EntityManager entityManager;

    @Transactional
    public ApiKey gerar(String tenantNif, String descricao) {
        ApiKey apiKey = new ApiKey();
        apiKey.setTenantNif(tenantNif);
        apiKey.setDescricao(descricao);
        apiKey.setChaveValor("API-" + tenantNif + "-" + Long.toHexString(RANDOM.nextLong()).toUpperCase());
        apiKey.setActiva(Boolean.TRUE);
        return entityManager.merge(apiKey);
    }

    @Transactional
    public boolean validar(String tenantNif, String chaveValor) {
        List<ApiKey> resultado = entityManager.createQuery(
                        "select a from ApiKey a where a.tenantNif = :tenant and a.chaveValor = :chave and a.activa = true", ApiKey.class)
                .setParameter("tenant", tenantNif)
                .setParameter("chave", chaveValor)
                .setLockMode(LockModeType.PESSIMISTIC_WRITE)
                .setMaxResults(1)
                .getResultList();
        if (!resultado.isEmpty()) {
            ApiKey apiKey = resultado.get(0);
            apiKey.setUltimaUtilizacao(LocalDateTime.now());
            entityManager.merge(apiKey);
            return true;
        }
        return false;
    }
}
