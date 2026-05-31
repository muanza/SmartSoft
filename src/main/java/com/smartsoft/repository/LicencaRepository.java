package com.smartsoft.repository;

import com.smartsoft.entity.Licenca;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface LicencaRepository extends JpaRepository<Licenca, UUID> {
    Optional<Licenca> findByChaveLicenca(String chaveLicenca);
}
