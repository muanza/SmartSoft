package com.smartsoft.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "log_auditoria", indexes = {
    @Index(name = "idx_log_auditoria_tenant", columnList = "id_tenant"),
    @Index(name = "idx_log_auditoria_utilizador", columnList = "id_utilizador"),
    @Index(name = "idx_log_auditoria_data", columnList = "data_acao")
})
public class LogAuditoria {

    @Id
    @GeneratedValue
    @Column(name = "id_log_auditoria", updatable = false, nullable = false)
    private UUID idLogAuditoria;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tenant", nullable = false)
    private Tenant tenant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_utilizador")
    private Utilizador utilizador;

    @Column(name = "tipo_acao", length = 50)
    private String tipoAcao;

    @Column(name = "descricao_acao", columnDefinition = "TEXT")
    private String descricaoAcao;

    @Column(name = "tabela_afetada", length = 100)
    private String tabelaAfetada;

    @Column(name = "id_registro_afetado")
    private UUID idRegistroAfetado;

    @Column(name = "dados_anteriores", columnDefinition = "jsonb")
    private String dadosAnteriores;

    @Column(name = "dados_novos", columnDefinition = "jsonb")
    private String dadosNovos;

    @Column(name = "data_acao")
    private LocalDateTime dataAcao;

    @Column(name = "endereco_ip", length = 45)
    private String enderecoIp;

    @PrePersist
    protected void onCreate() {
        dataAcao = LocalDateTime.now();
    }

    public LogAuditoria() {}

    public UUID getIdLogAuditoria() { return idLogAuditoria; }
    public void setIdLogAuditoria(UUID idLogAuditoria) { this.idLogAuditoria = idLogAuditoria; }

    public Tenant getTenant() { return tenant; }
    public void setTenant(Tenant tenant) { this.tenant = tenant; }

    public Utilizador getUtilizador() { return utilizador; }
    public void setUtilizador(Utilizador utilizador) { this.utilizador = utilizador; }

    public String getTipoAcao() { return tipoAcao; }
    public void setTipoAcao(String tipoAcao) { this.tipoAcao = tipoAcao; }

    public String getDescricaoAcao() { return descricaoAcao; }
    public void setDescricaoAcao(String descricaoAcao) { this.descricaoAcao = descricaoAcao; }

    public String getTabelaAfetada() { return tabelaAfetada; }
    public void setTabelaAfetada(String tabelaAfetada) { this.tabelaAfetada = tabelaAfetada; }

    public UUID getIdRegistroAfetado() { return idRegistroAfetado; }
    public void setIdRegistroAfetado(UUID idRegistroAfetado) { this.idRegistroAfetado = idRegistroAfetado; }

    public String getDadosAnteriores() { return dadosAnteriores; }
    public void setDadosAnteriores(String dadosAnteriores) { this.dadosAnteriores = dadosAnteriores; }

    public String getDadosNovos() { return dadosNovos; }
    public void setDadosNovos(String dadosNovos) { this.dadosNovos = dadosNovos; }

    public LocalDateTime getDataAcao() { return dataAcao; }
    public void setDataAcao(LocalDateTime dataAcao) { this.dataAcao = dataAcao; }

    public String getEnderecoIp() { return enderecoIp; }
    public void setEnderecoIp(String enderecoIp) { this.enderecoIp = enderecoIp; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LogAuditoria that = (LogAuditoria) o;
        return Objects.equals(idLogAuditoria, that.idLogAuditoria);
    }

    @Override
    public int hashCode() { return Objects.hash(idLogAuditoria); }

    @Override
    public String toString() {
        return "LogAuditoria{idLogAuditoria=" + idLogAuditoria + ", tipoAcao='" + tipoAcao + "'}";
    }
}
