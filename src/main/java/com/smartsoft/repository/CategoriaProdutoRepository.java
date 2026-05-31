package com.smartsoft.repository;

import com.smartsoft.entity.CategoriaProduto;
import com.smartsoft.entity.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CategoriaProdutoRepository extends JpaRepository<CategoriaProduto, UUID> {

    List<CategoriaProduto> findByTenantAndAtivoTrueOrderByNomeCategoriaAsc(Tenant tenant);

    boolean existsByNomeCategoriaAndTenant(String nomeCategoria, Tenant tenant);
}
