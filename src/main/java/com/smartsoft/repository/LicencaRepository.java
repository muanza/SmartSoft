package com.smartsoft.repository;

import com.smartsoft.entity.Licenca;
import com.smartsoft.entity.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LicencaRepository extends JpaRepository<Licenca, UUID> {

    Optional<Licenca> findByChaveLicenca(String chaveLicenca);

    Optional<Licenca> findByTenantAndAtivoTrueAndBloqueadaFalseAndDataExpiracaoAfter(
            Tenant tenant, LocalDate data);

    List<Licenca> findByTenant(Tenant tenant);

    boolean existsByChaveLicenca(String chaveLicenca);
}
