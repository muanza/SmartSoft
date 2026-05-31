package com.smartsoft.repository;

import com.smartsoft.entity.ConfiguracaoEmpresa;
import com.smartsoft.entity.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ConfiguracaoEmpresaRepository extends JpaRepository<ConfiguracaoEmpresa, UUID> {

    Optional<ConfiguracaoEmpresa> findByTenant(Tenant tenant);

    Optional<ConfiguracaoEmpresa> findByTenantNif(String tenantNif);
}
