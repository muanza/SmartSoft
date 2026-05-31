package com.smartsoft.repository;

import com.smartsoft.entity.Factura;
import com.smartsoft.entity.Tenant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FacturaRepository extends JpaRepository<Factura, UUID> {

    Optional<Factura> findByNumeroFacturaAndTenant(String numeroFactura, Tenant tenant);

    Page<Factura> findByTenantOrderByDataFacturaDesc(Tenant tenant, Pageable pageable);

    List<Factura> findByTenantAndDataFacturaBetween(Tenant tenant, LocalDate inicio, LocalDate fim);

    List<Factura> findByTenantAndStatus(Tenant tenant, String status);

    @Query("SELECT COUNT(f) FROM Factura f WHERE f.tenant = :tenant AND f.dataFactura = :data AND f.status != 'cancelada'")
    long countByTenantAndData(@Param("tenant") Tenant tenant, @Param("data") LocalDate data);

    @Query("SELECT COALESCE(SUM(f.totalLiquido), 0) FROM Factura f WHERE f.tenant = :tenant AND f.dataFactura BETWEEN :inicio AND :fim AND f.status IN ('finalizada', 'paga')")
    java.math.BigDecimal sumTotalByTenantAndPeriodo(@Param("tenant") Tenant tenant,
                                                     @Param("inicio") LocalDate inicio,
                                                     @Param("fim") LocalDate fim);

    @Query("SELECT COALESCE(SUM(f.totalLiquido), 0) FROM Factura f WHERE f.tenant = :tenant AND f.dataFactura = :data AND f.status IN ('finalizada', 'paga')")
    java.math.BigDecimal sumTotalLiquidoByTenantAndData(@Param("tenant") Tenant tenant, @Param("data") LocalDate data);

    @Query("SELECT COALESCE(SUM(f.totalLiquido), 0) FROM Factura f WHERE f.tenant = :tenant AND f.dataFactura BETWEEN :inicio AND :fim AND f.status IN ('finalizada', 'paga')")
    java.math.BigDecimal sumTotalLiquidoByTenantAndPeriodo(@Param("tenant") Tenant tenant,
                                                             @Param("inicio") LocalDate inicio,
                                                             @Param("fim") LocalDate fim);

    @Query("SELECT COUNT(f) FROM Factura f WHERE f.tenant = :tenant AND f.dataFactura BETWEEN :inicio AND :fim AND f.status != 'cancelada'")
    long countByTenantAndPeriodo(@Param("tenant") Tenant tenant, @Param("inicio") LocalDate inicio, @Param("fim") LocalDate fim);

    boolean existsByNumeroFacturaAndTenant(String numeroFactura, Tenant tenant);
}
