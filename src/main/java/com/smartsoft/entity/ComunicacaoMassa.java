package com.smartsoft.entity;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "comunicacoes_massa", indexes = {
    @Index(name = "idx_comunicacoes_massa_data", columnList = "data_envio"),
    @Index(name = "idx_comunicacoes_massa_enviado", columnList = "enviado")
})
public class ComunicacaoMassa {

    @Id
    @GeneratedValue
    @Column(name = "id_comunicacao", updatable = false, nullable = false)
    private UUID idComunicacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_utilizador_crm", nullable = false)
    private UtilizadorCrm utilizadorCrm;

    @NotBlank
    @Size(max = 255)
    @Column(name = "titulo", nullable = false)
    private String titulo;

    @NotBlank
    @Column(name = "conteudo", columnDefinition = "TEXT", nullable = false)
    private String conteudo;

    @Pattern(regexp = "email|notificacao|sms")
    @Column(name = "tipo_comunicacao", length = 50, columnDefinition = "VARCHAR(50) DEFAULT 'email'")
    private String tipoComunicacao = "email";

    @Column(name = "data_envio")
    private LocalDateTime dataEnvio;

    @Column(name = "enviado", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean enviado = false;

    @OneToMany(mappedBy = "comunicacao", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DestinatarioComunicacao> destinatarios;

    @PrePersist
    protected void onCreate() {
        dataEnvio = LocalDateTime.now();
    }

    public ComunicacaoMassa() {}

    public UUID getIdComunicacao() { return idComunicacao; }
    public void setIdComunicacao(UUID idComunicacao) { this.idComunicacao = idComunicacao; }

    public UtilizadorCrm getUtilizadorCrm() { return utilizadorCrm; }
    public void setUtilizadorCrm(UtilizadorCrm utilizadorCrm) { this.utilizadorCrm = utilizadorCrm; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getConteudo() { return conteudo; }
    public void setConteudo(String conteudo) { this.conteudo = conteudo; }

    public String getTipoComunicacao() { return tipoComunicacao; }
    public void setTipoComunicacao(String tipoComunicacao) { this.tipoComunicacao = tipoComunicacao; }

    public LocalDateTime getDataEnvio() { return dataEnvio; }
    public void setDataEnvio(LocalDateTime dataEnvio) { this.dataEnvio = dataEnvio; }

    public Boolean getEnviado() { return enviado; }
    public void setEnviado(Boolean enviado) { this.enviado = enviado; }

    public List<DestinatarioComunicacao> getDestinatarios() { return destinatarios; }
    public void setDestinatarios(List<DestinatarioComunicacao> destinatarios) { this.destinatarios = destinatarios; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComunicacaoMassa that = (ComunicacaoMassa) o;
        return Objects.equals(idComunicacao, that.idComunicacao);
    }

    @Override
    public int hashCode() { return Objects.hash(idComunicacao); }

    @Override
    public String toString() {
        return "ComunicacaoMassa{idComunicacao=" + idComunicacao + ", titulo='" + titulo + "'}";
    }
}
