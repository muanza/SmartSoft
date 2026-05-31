package com.smartsoft.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "destinatarios_comunicacao", indexes = {
    @Index(name = "idx_destinatarios_comunicacao", columnList = "id_comunicacao")
})
public class DestinatarioComunicacao {

    @Id
    @GeneratedValue
    @Column(name = "id_destinatario", updatable = false, nullable = false)
    private UUID idDestinatario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_comunicacao", nullable = false)
    private ComunicacaoMassa comunicacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tenant", nullable = false)
    private Tenant tenant;

    @Column(name = "data_entrega")
    private LocalDateTime dataEntrega;

    @Column(name = "lido", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean lido = false;

    @Column(name = "data_leitura")
    private LocalDateTime dataLeitura;

    public DestinatarioComunicacao() {}

    public UUID getIdDestinatario() { return idDestinatario; }
    public void setIdDestinatario(UUID idDestinatario) { this.idDestinatario = idDestinatario; }

    public ComunicacaoMassa getComunicacao() { return comunicacao; }
    public void setComunicacao(ComunicacaoMassa comunicacao) { this.comunicacao = comunicacao; }

    public Tenant getTenant() { return tenant; }
    public void setTenant(Tenant tenant) { this.tenant = tenant; }

    public LocalDateTime getDataEntrega() { return dataEntrega; }
    public void setDataEntrega(LocalDateTime dataEntrega) { this.dataEntrega = dataEntrega; }

    public Boolean getLido() { return lido; }
    public void setLido(Boolean lido) { this.lido = lido; }

    public LocalDateTime getDataLeitura() { return dataLeitura; }
    public void setDataLeitura(LocalDateTime dataLeitura) { this.dataLeitura = dataLeitura; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DestinatarioComunicacao that = (DestinatarioComunicacao) o;
        return Objects.equals(idDestinatario, that.idDestinatario);
    }

    @Override
    public int hashCode() { return Objects.hash(idDestinatario); }

    @Override
    public String toString() {
        return "DestinatarioComunicacao{idDestinatario=" + idDestinatario + ", lido=" + lido + "}";
    }
}
