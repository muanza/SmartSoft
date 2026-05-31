package com.smartsoft.repository;

import com.smartsoft.entity.Traducao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TraducaoRepository extends JpaRepository<Traducao, UUID> {

    List<Traducao> findByIdioma(String idioma);

    Optional<Traducao> findByChaveTraducaoAndIdioma(String chave, String idioma);

    List<Traducao> findByTenantIdTenantAndIdioma(UUID idTenant, String idioma);
}
