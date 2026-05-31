package com.smartsoft.repository;

import com.smartsoft.entity.PagamentoFactura;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PagamentoFacturaRepository extends JpaRepository<PagamentoFactura, UUID> {
    List<PagamentoFactura> findByFacturaId(UUID facturaId);
}
