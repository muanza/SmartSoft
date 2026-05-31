package com.smartsoft.repository;

import com.smartsoft.entity.LogBackup;
import com.smartsoft.entity.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LogBackupRepository extends JpaRepository<LogBackup, UUID> {

    List<LogBackup> findByTenantOrderByDataInicioBackupDesc(Tenant tenant);

    List<LogBackup> findByTenantAndStatusBackupOrderByDataInicioBackupDesc(Tenant tenant, String statusBackup);
}
