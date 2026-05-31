package com.smartsoft.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "log_validacao_licenca", indexes = {
    @Index(name = "idx_log_validacao_licenca", columnList = "id_licenca")
})
public class LogValidacaoLicenca {

    @Id
    @GeneratedValue
    @Column(name = "id_log", updatable = false, nullable = false)
    private UUID idLog;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_licenca", nullable = false)
    private Licenca licenca;

    @Column(name = "endereco_ip", length = 45)
    private String enderecoIp;

    @Column(name = "data_validacao")
    private LocalDateTime dataValidacao;

    @Column(name = "valida")
    private Boolean valida;

    @Column(name = "motivo_rejeicao", columnDefinition = "TEXT")
    private String motivoRejeicao;

    @PrePersist
    protected void onCreate() {
        dataValidacao = LocalDateTime.now();
    }

    public LogValidacaoLicenca() {}

    public UUID getIdLog() { return idLog; }
    public void setIdLog(UUID idLog) { this.idLog = idLog; }

    public Licenca getLicenca() { return licenca; }
    public void setLicenca(Licenca licenca) { this.licenca = licenca; }

    public String getEnderecoIp() { return enderecoIp; }
    public void setEnderecoIp(String enderecoIp) { this.enderecoIp = enderecoIp; }

    public LocalDateTime getDataValidacao() { return dataValidacao; }
    public void setDataValidacao(LocalDateTime dataValidacao) { this.dataValidacao = dataValidacao; }

    public Boolean getValida() { return valida; }
    public void setValida(Boolean valida) { this.valida = valida; }

    public String getMotivoRejeicao() { return motivoRejeicao; }
    public void setMotivoRejeicao(String motivoRejeicao) { this.motivoRejeicao = motivoRejeicao; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LogValidacaoLicenca that = (LogValidacaoLicenca) o;
        return Objects.equals(idLog, that.idLog);
    }

    @Override
    public int hashCode() { return Objects.hash(idLog); }

    @Override
    public String toString() {
        return "LogValidacaoLicenca{idLog=" + idLog + ", valida=" + valida + "}";
    }
}
