package com.faturacao.crm.service;

import javax.enterprise.context.ApplicationScoped;
import java.time.LocalDateTime;

@ApplicationScoped
public class BackupService {

    public String gerarPlanoBackup(String tenantNif) {
        return "Backup incremental local do tenant " + tenantNif + " agendado em " + LocalDateTime.now();
    }
}
