package com.smartsoft.repository;

import com.smartsoft.entity.LogAuditoria;
import com.smartsoft.entity.Tenant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LogAuditoriaRepository extends JpaRepository<LogAuditoria, UUID> {

    Page<LogAuditoria> findByTenantOrderByDataAcaoDesc(Tenant tenant, Pageable pageable);
}
