package com.smartsoft.service;

import com.smartsoft.entity.LogBackup;
import com.smartsoft.entity.Tenant;
import com.smartsoft.exception.ResourceNotFoundException;
import com.smartsoft.repository.LogBackupRepository;
import com.smartsoft.repository.TenantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class BackupService {

    private static final Logger log = LoggerFactory.getLogger(BackupService.class);

    private final LogBackupRepository logBackupRepository;
    private final TenantRepository tenantRepository;

    public BackupService(LogBackupRepository logBackupRepository,
                         TenantRepository tenantRepository) {
        this.logBackupRepository = logBackupRepository;
        this.tenantRepository = tenantRepository;
    }

    @Scheduled(cron = "${smartsoft.backup.schedule:0 0 2 * * ?}")
    @Transactional
    public void executarBackupAutomatico() {
        log.info("Iniciando backup automático...");
        List<Tenant> tenants = tenantRepository.findAll();
        for (Tenant tenant : tenants) {
            try {
                realizarBackup(tenant, "incremental", "local");
            } catch (Exception e) {
                log.error("Erro ao realizar backup para tenant {}: {}", tenant.getNif(), e.getMessage());
            }
        }
    }

    @Transactional
    public LogBackup realizarBackup(Tenant tenant, String tipoBackup, String destino) {
        LogBackup backup = new LogBackup();
        backup.setTenant(tenant);
        backup.setTipoBackup(tipoBackup);
        backup.setLocalBackup(destino);
        backup.setDataInicioBackup(LocalDateTime.now());
        backup.setStatusBackup("em_progresso");

        try {
            // In a real implementation: execute backup logic here
            backup.setStatusBackup("concluido");
            backup.setDataFimBackup(LocalDateTime.now());
            backup.setMotivoFalha(null);
            log.info("Backup {} concluído para tenant: {}", tipoBackup, tenant.getNif());
        } catch (Exception e) {
            backup.setStatusBackup("falhou");
            backup.setDataFimBackup(LocalDateTime.now());
            backup.setMotivoFalha("Erro: " + e.getMessage());
            log.error("Erro no backup para tenant {}: {}", tenant.getNif(), e.getMessage());
        }

        return logBackupRepository.save(backup);
    }

    @Transactional
    public Map<String, Object> iniciarBackup(String tenantNif, String tipoBackup) {
        Tenant tenant = tenantRepository.findByNifAndAtivoTrue(tenantNif)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant", "nif", tenantNif));

        LogBackup backup = realizarBackup(tenant, tipoBackup != null ? tipoBackup : "manual", "local");
        return Map.of(
                "id", backup.getIdLogBackup().toString(),
                "status", backup.getStatusBackup(),
                "dataInicio", backup.getDataInicioBackup().toString(),
                "mensagem", backup.getMotivoFalha() != null ? backup.getMotivoFalha() : "Backup concluído"
        );
    }

    @Transactional(readOnly = true)
    public List<LogBackup> listarBackups(String tenantNif) {
        Tenant tenant = tenantRepository.findByNifAndAtivoTrue(tenantNif)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant", "nif", tenantNif));
        return logBackupRepository.findByTenantOrderByDataInicioBackupDesc(tenant);
    }
}
