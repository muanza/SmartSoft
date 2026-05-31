package com.smartsoft.controller;

import com.smartsoft.config.TenantContext;
import com.smartsoft.dto.ApiResponse;
import com.smartsoft.entity.LogBackup;
import com.smartsoft.service.BackupService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/backup")
public class BackupController {

    private final BackupService backupService;

    public BackupController(BackupService backupService) {
        this.backupService = backupService;
    }

    @PostMapping("/iniciar")
    public ResponseEntity<ApiResponse<Map<String, Object>>> iniciarBackup(
            @RequestParam(defaultValue = "manual") String tipo) {
        String tenantNif = TenantContext.getCurrentTenant();
        Map<String, Object> resultado = backupService.iniciarBackup(tenantNif, tipo);
        return ResponseEntity.ok(ApiResponse.sucesso("Backup iniciado", resultado));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<LogBackup>>> listarBackups() {
        String tenantNif = TenantContext.getCurrentTenant();
        List<LogBackup> backups = backupService.listarBackups(tenantNif);
        return ResponseEntity.ok(ApiResponse.sucesso("Backups listados", backups));
    }
}
