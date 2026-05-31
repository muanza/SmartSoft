package com.smartsoft.entity;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "traducoes",
    uniqueConstraints = @UniqueConstraint(columnNames = {"id_tenant", "chave_traducao", "idioma"}),
    indexes = {
        @Index(name = "idx_traducoes_idioma", columnList = "idioma"),
        @Index(name = "idx_traducoes_chave", columnList = "chave_traducao")
    })
public class Traducao {

    @Id
    @GeneratedValue
    @Column(name = "id_traducao", updatable = false, nullable = false)
    private UUID idTraducao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tenant")
    private Tenant tenant;

    @NotBlank
    @Size(max = 255)
    @Column(name = "chave_traducao", nullable = false, length = 255)
    private String chaveTraducao;

    @NotBlank
    @Pattern(regexp = "pt|en|fr|zh")
    @Column(name = "idioma", nullable = false, length = 10)
    private String idioma;

    @NotBlank
    @Column(name = "valor_traducao", nullable = false, columnDefinition = "TEXT")
    private String valorTraducao;

    @Pattern(regexp = "label|mensagem|titulo|campo|outro")
    @Column(name = "tipo_elemento", length = 50)
    private String tipoElemento;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @PrePersist
    protected void onCreate() {
        dataCriacao = LocalDateTime.now();
    }

    public Traducao() {}

    public UUID getIdTraducao() { return idTraducao; }
    public void setIdTraducao(UUID idTraducao) { this.idTraducao = idTraducao; }

    public Tenant getTenant() { return tenant; }
    public void setTenant(Tenant tenant) { this.tenant = tenant; }

    public String getChaveTraducao() { return chaveTraducao; }
    public void setChaveTraducao(String chaveTraducao) { this.chaveTraducao = chaveTraducao; }

    public String getIdioma() { return idioma; }
    public void setIdioma(String idioma) { this.idioma = idioma; }

    public String getValorTraducao() { return valorTraducao; }
    public void setValorTraducao(String valorTraducao) { this.valorTraducao = valorTraducao; }

    public String getTipoElemento() { return tipoElemento; }
    public void setTipoElemento(String tipoElemento) { this.tipoElemento = tipoElemento; }

    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Traducao traducao = (Traducao) o;
        return Objects.equals(idTraducao, traducao.idTraducao);
    }

    @Override
    public int hashCode() { return Objects.hash(idTraducao); }

    @Override
    public String toString() {
        return "Traducao{idTraducao=" + idTraducao + ", chave='" + chaveTraducao + "', idioma='" + idioma + "'}";
    }
}
