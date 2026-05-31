package com.smartsoft.repository;

import com.smartsoft.entity.Utilizador;
import com.smartsoft.entity.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UtilizadorRepository extends JpaRepository<Utilizador, UUID> {

    Optional<Utilizador> findByEmailAndTenant(String email, Tenant tenant);

    Optional<Utilizador> findByEmailAndTenantNif(String email, String tenantNif);

    List<Utilizador> findByTenantAndAtivoTrue(Tenant tenant);

    List<Utilizador> findByTenantNif(String tenantNif);

    boolean existsByEmailAndTenant(String email, Tenant tenant);

    boolean existsByEmailAndTenantNif(String email, String tenantNif);
}
