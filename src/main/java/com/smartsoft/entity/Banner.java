package com.smartsoft.entity;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "banners", indexes = {
    @Index(name = "idx_banners_ativo", columnList = "ativo"),
    @Index(name = "idx_banners_datas", columnList = "data_inicio,data_fim")
})
public class Banner {

    @Id
    @GeneratedValue
    @Column(name = "id_banner", updatable = false, nullable = false)
    private UUID idBanner;

    @NotBlank
    @Size(max = 255)
    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column(name = "descricao", columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "imagem")
    private byte[] imagem;

    @Size(max = 500)
    @Column(name = "url_destino", length = 500)
    private String urlDestino;

    @NotNull
    @Column(name = "data_inicio", nullable = false)
    private LocalDate dataInicio;

    @NotNull
    @Column(name = "data_fim", nullable = false)
    private LocalDate dataFim;

    @Pattern(regexp = "geral|parceiro|tenant")
    @Column(name = "tipo_banner", length = 50, columnDefinition = "VARCHAR(50) DEFAULT 'geral'")
    private String tipoBanner = "geral";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alvo_parceiro")
    private Parceiro alvoParceiro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alvo_tenant")
    private Tenant alvoTenant;

    @Column(name = "ativo", columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean ativo = true;

    @Column(name = "ordem_exibicao", columnDefinition = "INT DEFAULT 0")
    private Integer ordemExibicao = 0;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @PrePersist
    protected void onCreate() {
        dataCriacao = LocalDateTime.now();
    }

    public Banner() {}

    public UUID getIdBanner() { return idBanner; }
    public void setIdBanner(UUID idBanner) { this.idBanner = idBanner; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public byte[] getImagem() { return imagem; }
    public void setImagem(byte[] imagem) { this.imagem = imagem; }

    public String getUrlDestino() { return urlDestino; }
    public void setUrlDestino(String urlDestino) { this.urlDestino = urlDestino; }

    public LocalDate getDataInicio() { return dataInicio; }
    public void setDataInicio(LocalDate dataInicio) { this.dataInicio = dataInicio; }

    public LocalDate getDataFim() { return dataFim; }
    public void setDataFim(LocalDate dataFim) { this.dataFim = dataFim; }

    public String getTipoBanner() { return tipoBanner; }
    public void setTipoBanner(String tipoBanner) { this.tipoBanner = tipoBanner; }

    public Parceiro getAlvoParceiro() { return alvoParceiro; }
    public void setAlvoParceiro(Parceiro alvoParceiro) { this.alvoParceiro = alvoParceiro; }

    public Tenant getAlvoTenant() { return alvoTenant; }
    public void setAlvoTenant(Tenant alvoTenant) { this.alvoTenant = alvoTenant; }

    public Boolean getAtivo() { return ativo; }
    public void setAtivo(Boolean ativo) { this.ativo = ativo; }

    public Integer getOrdemExibicao() { return ordemExibicao; }
    public void setOrdemExibicao(Integer ordemExibicao) { this.ordemExibicao = ordemExibicao; }

    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Banner banner = (Banner) o;
        return Objects.equals(idBanner, banner.idBanner);
    }

    @Override
    public int hashCode() { return Objects.hash(idBanner); }

    @Override
    public String toString() {
        return "Banner{idBanner=" + idBanner + ", titulo='" + titulo + "'}";
    }
}
