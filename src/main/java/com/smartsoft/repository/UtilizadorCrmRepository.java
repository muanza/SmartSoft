package com.smartsoft.repository;

import com.smartsoft.entity.UtilizadorCrm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UtilizadorCrmRepository extends JpaRepository<UtilizadorCrm, UUID> {

    Optional<UtilizadorCrm> findByEmail(String email);

    Optional<UtilizadorCrm> findByEmailAndAtivoTrue(String email);
}
