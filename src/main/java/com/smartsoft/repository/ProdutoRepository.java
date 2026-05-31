package com.smartsoft.repository;

import com.smartsoft.entity.Produto;
import com.smartsoft.entity.Tenant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, UUID> {

    Optional<Produto> findByCodigoProdutoAndTenant(String codigoProduto, Tenant tenant);

    Page<Produto> findByTenantAndAtivoTrueOrderByNomeProdutoAsc(Tenant tenant, Pageable pageable);

    List<Produto> findByTenantAndAtivoTrue(Tenant tenant);

    List<Produto> findByTenantAndCategoriaIdCategoria(Tenant tenant, UUID idCategoria);

    boolean existsByCodigoProdutoAndTenant(String codigoProduto, Tenant tenant);

    boolean existsByCodigoProdutoAndTenantNif(String codigoProduto, String tenantNif);

    Page<Produto> findByTenantNifAndAtivoTrue(String tenantNif, Pageable pageable);
}
