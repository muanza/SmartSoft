package com.smartsoft.repository;

import com.smartsoft.entity.Factura;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FacturaRepository extends JpaRepository<Factura, UUID> {
}
