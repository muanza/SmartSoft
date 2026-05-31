package com.faturacao.pos.service;

import javax.enterprise.context.ApplicationScoped;
import java.time.LocalDateTime;

@ApplicationScoped
public class BackupService {

    public String gerarBackupIncremental(String tenantNif, String pastaDestino) {
        return pastaDestino + "/backup-" + tenantNif + "-" + LocalDateTime.now().toLocalDate() + ".sql";
    }
}
