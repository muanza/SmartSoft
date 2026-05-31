package com.smartsoft.entity;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "log_sincronizacao_cloud", indexes = {
    @Index(name = "idx_log_sincronizacao_cloud_tenant", columnList = "id_tenant"),
    @Index(name = "idx_log_sincronizacao_cloud_data", columnList = "data_inicio_sincronizacao")
})
public class LogSincronizacaoCloud {

    @Id
    @GeneratedValue
    @Column(name = "id_log_sincronizacao", updatable = false, nullable = false)
    private UUID idLogSincronizacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tenant", nullable = false)
    private Tenant tenant;

    @Pattern(regexp = "upload|download|bidireccional")
    @Column(name = "tipo_sincronizacao", length = 50)
    private String tipoSincronizacao;

    @Column(name = "dados_sincronizados", columnDefinition = "jsonb")
    private String dadosSincronizados;

    @Column(name = "data_inicio_sincronizacao")
    private LocalDateTime dataInicioSincronizacao;

    @Column(name = "data_fim_sincronizacao")
    private LocalDateTime dataFimSincronizacao;

    @Pattern(regexp = "em_progresso|concluida|falhou")
    @Column(name = "status_sincronizacao", length = 50, columnDefinition = "VARCHAR(50) DEFAULT 'em_progresso'")
    private String statusSincronizacao = "em_progresso";

    @Column(name = "motivo_falha", columnDefinition = "TEXT")
    private String motivoFalha;

    @Column(name = "numero_registos_sincronizados")
    private Integer numeroRegistosSincronizados;

    @Size(max = 64)
    @Column(name = "checksum_verificacao", length = 64)
    private String checksumVerificacao;

    @PrePersist
    protected void onCreate() {
        dataInicioSincronizacao = LocalDateTime.now();
    }

    public LogSincronizacaoCloud() {}

    public UUID getIdLogSincronizacao() { return idLogSincronizacao; }
    public void setIdLogSincronizacao(UUID idLogSincronizacao) { this.idLogSincronizacao = idLogSincronizacao; }

    public Tenant getTenant() { return tenant; }
    public void setTenant(Tenant tenant) { this.tenant = tenant; }

    public String getTipoSincronizacao() { return tipoSincronizacao; }
    public void setTipoSincronizacao(String tipoSincronizacao) { this.tipoSincronizacao = tipoSincronizacao; }

    public String getDadosSincronizados() { return dadosSincronizados; }
    public void setDadosSincronizados(String dadosSincronizados) { this.dadosSincronizados = dadosSincronizados; }

    public LocalDateTime getDataInicioSincronizacao() { return dataInicioSincronizacao; }
    public void setDataInicioSincronizacao(LocalDateTime dataInicioSincronizacao) { this.dataInicioSincronizacao = dataInicioSincronizacao; }

    public LocalDateTime getDataFimSincronizacao() { return dataFimSincronizacao; }
    public void setDataFimSincronizacao(LocalDateTime dataFimSincronizacao) { this.dataFimSincronizacao = dataFimSincronizacao; }

    public String getStatusSincronizacao() { return statusSincronizacao; }
    public void setStatusSincronizacao(String statusSincronizacao) { this.statusSincronizacao = statusSincronizacao; }

    public String getMotivoFalha() { return motivoFalha; }
    public void setMotivoFalha(String motivoFalha) { this.motivoFalha = motivoFalha; }

    public Integer getNumeroRegistosSincronizados() { return numeroRegistosSincronizados; }
    public void setNumeroRegistosSincronizados(Integer numeroRegistosSincronizados) { this.numeroRegistosSincronizados = numeroRegistosSincronizados; }

    public String getChecksumVerificacao() { return checksumVerificacao; }
    public void setChecksumVerificacao(String checksumVerificacao) { this.checksumVerificacao = checksumVerificacao; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LogSincronizacaoCloud that = (LogSincronizacaoCloud) o;
        return Objects.equals(idLogSincronizacao, that.idLogSincronizacao);
    }

    @Override
    public int hashCode() { return Objects.hash(idLogSincronizacao); }

    @Override
    public String toString() {
        return "LogSincronizacaoCloud{idLogSincronizacao=" + idLogSincronizacao + ", statusSincronizacao='" + statusSincronizacao + "'}";
    }
}
