package com.smartsoft.repository;

import com.smartsoft.entity.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TenantRepository extends JpaRepository<Tenant, UUID> {

    Optional<Tenant> findByNif(String nif);

    Optional<Tenant> findByNifAndAtivoTrue(String nif);

    List<Tenant> findByAtivoTrue();

    boolean existsByNif(String nif);
}
