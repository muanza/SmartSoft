package com.smartsoft.repository;

import com.smartsoft.entity.Stock;
import com.smartsoft.entity.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface StockRepository extends JpaRepository<Stock, UUID> {

    Optional<Stock> findByProdutoIdProdutoAndTenant(UUID idProduto, Tenant tenant);

    List<Stock> findByTenant(Tenant tenant);

    @Query("SELECT s FROM Stock s WHERE s.tenant = :tenant AND s.quantidadeAtual <= s.quantidadeMinima")
    List<Stock> findStockCriticoByTenant(@Param("tenant") Tenant tenant);
}
