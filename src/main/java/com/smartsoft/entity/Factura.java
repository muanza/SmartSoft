package com.smartsoft.entity;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "facturas",
    uniqueConstraints = @UniqueConstraint(columnNames = {"id_tenant", "numero_factura"}),
    indexes = {
        @Index(name = "idx_facturas_tenant", columnList = "id_tenant"),
        @Index(name = "idx_facturas_numero", columnList = "numero_factura"),
        @Index(name = "idx_facturas_data", columnList = "data_factura"),
        @Index(name = "idx_facturas_status", columnList = "status"),
        @Index(name = "idx_facturas_utilizador", columnList = "id_utilizador")
    })
public class Factura {

    @Id
    @GeneratedValue
    @Column(name = "id_factura", updatable = false, nullable = false)
    private UUID idFactura;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tenant", nullable = false)
    private Tenant tenant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_caixa")
    private Caixa caixa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_utilizador", nullable = false)
    private Utilizador utilizador;

    @NotBlank
    @Size(max = 50)
    @Column(name = "numero_factura", nullable = false, length = 50)
    private String numeroFactura;

    @Size(max = 20)
    @Column(name = "numero_serie", length = 20)
    private String numeroSerie;

    @NotNull
    @Column(name = "data_factura", nullable = false)
    private LocalDate dataFactura;

    @Column(name = "hora_factura")
    private LocalTime horaFactura;

    @NotBlank
    @Pattern(regexp = "venda|devolucao|orcamento|proforma")
    @Column(name = "tipo_factura", nullable = false, length = 50)
    private String tipoFactura;

    @Size(max = 255)
    @Column(name = "cliente_nome", length = 255)
    private String clienteNome;

    @Size(max = 20)
    @Column(name = "cliente_nif", length = 20)
    private String clienteNif;

    @Email
    @Size(max = 255)
    @Column(name = "cliente_email", length = 255)
    private String clienteEmail;

    @Size(max = 20)
    @Column(name = "cliente_telefone", length = 20)
    private String clienteTelefone;

    @Size(max = 500)
    @Column(name = "cliente_morada", length = 500)
    private String clienteMorada;

    @NotNull
    @DecimalMin("0.00")
    @Column(name = "subtotal", nullable = false, precision = 12, scale = 2, columnDefinition = "DECIMAL(12,2) DEFAULT 0.00")
    private BigDecimal subtotal = BigDecimal.ZERO;

    @NotNull
    @DecimalMin("0.00")
    @Column(name = "total_iva", nullable = false, precision = 12, scale = 2, columnDefinition = "DECIMAL(12,2) DEFAULT 0.00")
    private BigDecimal totalIva = BigDecimal.ZERO;

    @DecimalMin("0.00")
    @DecimalMax("100.00")
    @Column(name = "desconto_percentual", precision = 5, scale = 2, columnDefinition = "DECIMAL(5,2) DEFAULT 0.00")
    private BigDecimal descontoPercentual = BigDecimal.ZERO;

    @DecimalMin("0.00")
    @Column(name = "desconto_montante", precision = 12, scale = 2, columnDefinition = "DECIMAL(12,2) DEFAULT 0.00")
    private BigDecimal descontoMontante = BigDecimal.ZERO;

    @NotNull
    @DecimalMin("0.00")
    @Column(name = "total_liquido", nullable = false, precision = 12, scale = 2, columnDefinition = "DECIMAL(12,2) DEFAULT 0.00")
    private BigDecimal totalLiquido = BigDecimal.ZERO;

    @Size(max = 3)
    @Column(name = "moeda", length = 3, columnDefinition = "VARCHAR(3) DEFAULT 'AOA'")
    private String moeda = "AOA";

    @Pattern(regexp = "dinheiro|cartao|cheque|transferencia|misto|outro")
    @Column(name = "metodo_pagamento", length = 50)
    private String metodoPagamento;

    @DecimalMin("0.00")
    @Column(name = "montante_pago", precision = 12, scale = 2, columnDefinition = "DECIMAL(12,2) DEFAULT 0.00")
    private BigDecimal montantePago = BigDecimal.ZERO;

    @DecimalMin("0.00")
    @Column(name = "troco", precision = 12, scale = 2, columnDefinition = "DECIMAL(12,2) DEFAULT 0.00")
    private BigDecimal troco = BigDecimal.ZERO;

    @Pattern(regexp = "rascunho|finalizada|paga|cancelada|devolvida")
    @Column(name = "status", length = 50, columnDefinition = "VARCHAR(50) DEFAULT 'finalizada'")
    private String status = "finalizada";

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;

    @Size(max = 500)
    @Column(name = "assinatura_digital", length = 500)
    private String assinaturaDigital;

    @Size(max = 500)
    @Column(name = "hash_integridade", length = 500)
    private String hashIntegridade;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    @Column(name = "data_cancelamento")
    private LocalDateTime dataCancelamento;

    @Column(name = "motivo_cancelamento", columnDefinition = "TEXT")
    private String motivoCancelamento;

    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<LinhaFactura> linhas;

    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PagamentoFactura> pagamentos;

    @PrePersist
    protected void onCreate() {
        dataCriacao = LocalDateTime.now();
        dataAtualizacao = LocalDateTime.now();
        if (dataFactura == null) dataFactura = LocalDate.now();
        if (horaFactura == null) horaFactura = LocalTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        dataAtualizacao = LocalDateTime.now();
    }

    public Factura() {}

    public UUID getIdFactura() { return idFactura; }
    public void setIdFactura(UUID idFactura) { this.idFactura = idFactura; }

    public Tenant getTenant() { return tenant; }
    public void setTenant(Tenant tenant) { this.tenant = tenant; }

    public Caixa getCaixa() { return caixa; }
    public void setCaixa(Caixa caixa) { this.caixa = caixa; }

    public Utilizador getUtilizador() { return utilizador; }
    public void setUtilizador(Utilizador utilizador) { this.utilizador = utilizador; }

    public String getNumeroFactura() { return numeroFactura; }
    public void setNumeroFactura(String numeroFactura) { this.numeroFactura = numeroFactura; }

    public String getNumeroSerie() { return numeroSerie; }
    public void setNumeroSerie(String numeroSerie) { this.numeroSerie = numeroSerie; }

    public LocalDate getDataFactura() { return dataFactura; }
    public void setDataFactura(LocalDate dataFactura) { this.dataFactura = dataFactura; }

    public LocalTime getHoraFactura() { return horaFactura; }
    public void setHoraFactura(LocalTime horaFactura) { this.horaFactura = horaFactura; }

    public String getTipoFactura() { return tipoFactura; }
    public void setTipoFactura(String tipoFactura) { this.tipoFactura = tipoFactura; }

    public String getClienteNome() { return clienteNome; }
    public void setClienteNome(String clienteNome) { this.clienteNome = clienteNome; }

    public String getClienteNif() { return clienteNif; }
    public void setClienteNif(String clienteNif) { this.clienteNif = clienteNif; }

    public String getClienteEmail() { return clienteEmail; }
    public void setClienteEmail(String clienteEmail) { this.clienteEmail = clienteEmail; }

    public String getClienteTelefone() { return clienteTelefone; }
    public void setClienteTelefone(String clienteTelefone) { this.clienteTelefone = clienteTelefone; }

    public String getClienteMorada() { return clienteMorada; }
    public void setClienteMorada(String clienteMorada) { this.clienteMorada = clienteMorada; }

    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }

    public BigDecimal getTotalIva() { return totalIva; }
    public void setTotalIva(BigDecimal totalIva) { this.totalIva = totalIva; }

    public BigDecimal getDescontoPercentual() { return descontoPercentual; }
    public void setDescontoPercentual(BigDecimal descontoPercentual) { this.descontoPercentual = descontoPercentual; }

    public BigDecimal getDescontoMontante() { return descontoMontante; }
    public void setDescontoMontante(BigDecimal descontoMontante) { this.descontoMontante = descontoMontante; }

    public BigDecimal getTotalLiquido() { return totalLiquido; }
    public void setTotalLiquido(BigDecimal totalLiquido) { this.totalLiquido = totalLiquido; }

    public String getMoeda() { return moeda; }
    public void setMoeda(String moeda) { this.moeda = moeda; }

    public String getMetodoPagamento() { return metodoPagamento; }
    public void setMetodoPagamento(String metodoPagamento) { this.metodoPagamento = metodoPagamento; }

    public BigDecimal getMontantePago() { return montantePago; }
    public void setMontantePago(BigDecimal montantePago) { this.montantePago = montantePago; }

    public BigDecimal getTroco() { return troco; }
    public void setTroco(BigDecimal troco) { this.troco = troco; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }

    public String getAssinaturaDigital() { return assinaturaDigital; }
    public void setAssinaturaDigital(String assinaturaDigital) { this.assinaturaDigital = assinaturaDigital; }

    public String getHashIntegridade() { return hashIntegridade; }
    public void setHashIntegridade(String hashIntegridade) { this.hashIntegridade = hashIntegridade; }

    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }

    public LocalDateTime getDataAtualizacao() { return dataAtualizacao; }
    public void setDataAtualizacao(LocalDateTime dataAtualizacao) { this.dataAtualizacao = dataAtualizacao; }

    public LocalDateTime getDataCancelamento() { return dataCancelamento; }
    public void setDataCancelamento(LocalDateTime dataCancelamento) { this.dataCancelamento = dataCancelamento; }

    public String getMotivoCancelamento() { return motivoCancelamento; }
    public void setMotivoCancelamento(String motivoCancelamento) { this.motivoCancelamento = motivoCancelamento; }

    public List<LinhaFactura> getLinhas() { return linhas; }
    public void setLinhas(List<LinhaFactura> linhas) { this.linhas = linhas; }

    public List<PagamentoFactura> getPagamentos() { return pagamentos; }
    public void setPagamentos(List<PagamentoFactura> pagamentos) { this.pagamentos = pagamentos; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Factura factura = (Factura) o;
        return Objects.equals(idFactura, factura.idFactura);
    }

    @Override
    public int hashCode() { return Objects.hash(idFactura); }

    @Override
    public String toString() {
        return "Factura{idFactura=" + idFactura + ", numeroFactura='" + numeroFactura + "', status='" + status + "'}";
    }
}
