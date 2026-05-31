package com.smartsoft.entity;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "log_backup", indexes = {
    @Index(name = "idx_log_backup_tenant", columnList = "id_tenant"),
    @Index(name = "idx_log_backup_data", columnList = "data_inicio_backup")
})
public class LogBackup {

    @Id
    @GeneratedValue
    @Column(name = "id_log_backup", updatable = false, nullable = false)
    private UUID idLogBackup;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tenant", nullable = false)
    private Tenant tenant;

    @Pattern(regexp = "completo|incremental|diferencial")
    @Column(name = "tipo_backup", length = 50, columnDefinition = "VARCHAR(50) DEFAULT 'incremental'")
    private String tipoBackup = "incremental";

    @NotBlank
    @Size(max = 500)
    @Column(name = "local_backup", nullable = false, length = 500)
    private String localBackup;

    @Size(max = 500)
    @Column(name = "cloud_backup", length = 500)
    private String cloudBackup;

    @Column(name = "tamanho_backup")
    private Long tamanhoBackup;

    @Column(name = "data_inicio_backup")
    private LocalDateTime dataInicioBackup;

    @Column(name = "data_fim_backup")
    private LocalDateTime dataFimBackup;

    @Pattern(regexp = "em_progresso|concluido|falhou")
    @Column(name = "status_backup", length = 50, columnDefinition = "VARCHAR(50) DEFAULT 'em_progresso'")
    private String statusBackup = "em_progresso";

    @Column(name = "motivo_falha", columnDefinition = "TEXT")
    private String motivoFalha;

    @Column(name = "sincronizado_cloud", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean sincronizadoCloud = false;

    @Column(name = "data_sincronizacao_cloud")
    private LocalDateTime dataSincronizacaoCloud;

    @Column(name = "numero_registos_backup")
    private Integer numeroRegistosBackup;

    @Size(max = 64)
    @Column(name = "checksum", length = 64)
    private String checksum;

    @PrePersist
    protected void onCreate() {
        dataInicioBackup = LocalDateTime.now();
    }

    public LogBackup() {}

    public UUID getIdLogBackup() { return idLogBackup; }
    public void setIdLogBackup(UUID idLogBackup) { this.idLogBackup = idLogBackup; }

    public Tenant getTenant() { return tenant; }
    public void setTenant(Tenant tenant) { this.tenant = tenant; }

    public String getTipoBackup() { return tipoBackup; }
    public void setTipoBackup(String tipoBackup) { this.tipoBackup = tipoBackup; }

    public String getLocalBackup() { return localBackup; }
    public void setLocalBackup(String localBackup) { this.localBackup = localBackup; }

    public String getCloudBackup() { return cloudBackup; }
    public void setCloudBackup(String cloudBackup) { this.cloudBackup = cloudBackup; }

    public Long getTamanhoBackup() { return tamanhoBackup; }
    public void setTamanhoBackup(Long tamanhoBackup) { this.tamanhoBackup = tamanhoBackup; }

    public LocalDateTime getDataInicioBackup() { return dataInicioBackup; }
    public void setDataInicioBackup(LocalDateTime dataInicioBackup) { this.dataInicioBackup = dataInicioBackup; }

    public LocalDateTime getDataFimBackup() { return dataFimBackup; }
    public void setDataFimBackup(LocalDateTime dataFimBackup) { this.dataFimBackup = dataFimBackup; }

    public String getStatusBackup() { return statusBackup; }
    public void setStatusBackup(String statusBackup) { this.statusBackup = statusBackup; }

    public String getMotivoFalha() { return motivoFalha; }
    public void setMotivoFalha(String motivoFalha) { this.motivoFalha = motivoFalha; }

    public Boolean getSincronizadoCloud() { return sincronizadoCloud; }
    public void setSincronizadoCloud(Boolean sincronizadoCloud) { this.sincronizadoCloud = sincronizadoCloud; }

    public LocalDateTime getDataSincronizacaoCloud() { return dataSincronizacaoCloud; }
    public void setDataSincronizacaoCloud(LocalDateTime dataSincronizacaoCloud) { this.dataSincronizacaoCloud = dataSincronizacaoCloud; }

    public Integer getNumeroRegistosBackup() { return numeroRegistosBackup; }
    public void setNumeroRegistosBackup(Integer numeroRegistosBackup) { this.numeroRegistosBackup = numeroRegistosBackup; }

    public String getChecksum() { return checksum; }
    public void setChecksum(String checksum) { this.checksum = checksum; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LogBackup that = (LogBackup) o;
        return Objects.equals(idLogBackup, that.idLogBackup);
    }

    @Override
    public int hashCode() { return Objects.hash(idLogBackup); }

    @Override
    public String toString() {
        return "LogBackup{idLogBackup=" + idLogBackup + ", tipoBackup='" + tipoBackup + "', statusBackup='" + statusBackup + "'}";
    }
}
