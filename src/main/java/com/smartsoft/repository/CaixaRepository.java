package com.smartsoft.repository;

import com.smartsoft.entity.Caixa;
import com.smartsoft.entity.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CaixaRepository extends JpaRepository<Caixa, UUID> {

    Optional<Caixa> findByNumeroCaixaAndTenantAndStatus(String numeroCaixa, Tenant tenant, String status);

    List<Caixa> findByTenantAndStatusOrderByDataAberturaDesc(Tenant tenant, String status);

    Optional<Caixa> findFirstByTenantAndStatusOrderByDataAberturaDesc(Tenant tenant, String status);

    default Optional<Caixa> findCaixaAbertaByTenant(Tenant tenant) {
        return findFirstByTenantAndStatusOrderByDataAberturaDesc(tenant, "aberta");
    }
}
