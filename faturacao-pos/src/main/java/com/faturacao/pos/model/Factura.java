package com.faturacao.pos.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Table(name = "facturas")

public class Factura implements Serializable {

    @Id
    @Column(length = 36)
    private String id = UUID.randomUUID().toString();
    @Column(name = "tenant_nif", nullable = false, length = 20)
    private String tenantNif;
    @Column(nullable = false, unique = true, length = 60)
    private String numero;
    @Column(name = "tipo_documento", nullable = false, length = 30)
    private String tipoDocumento = "FACTURA";
    @Column(name = "hash_fiscal", nullable = false, length = 255)
    private String hashFiscal;
    @Column(name = "qr_code_texto", nullable = false, length = 4000)
    private String qrCodeTexto;
    @Column(name = "cliente_nome", length = 180)
    private String clienteNome;
    @Column(nullable = false, length = 5)
    private String idioma = "pt";
    @Column(nullable = false, precision = 18, scale = 2)
    private java.math.BigDecimal subtotal = java.math.BigDecimal.ZERO;
    @Column(name = "imposto_total", nullable = false, precision = 18, scale = 2)
    private java.math.BigDecimal impostoTotal = java.math.BigDecimal.ZERO;
    @Column(nullable = false, precision = 18, scale = 2)
    private java.math.BigDecimal total = java.math.BigDecimal.ZERO;
    @Column(nullable = false, length = 30)
    private String estado = "EMITIDA";
    @Column(name = "data_emissao")
    private LocalDateTime dataEmissao = LocalDateTime.now();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTenantNif() {
        return tenantNif;
    }

    public void setTenantNif(String tenantNif) {
        this.tenantNif = tenantNif;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getHashFiscal() {
        return hashFiscal;
    }

    public void setHashFiscal(String hashFiscal) {
        this.hashFiscal = hashFiscal;
    }

    public String getQrCodeTexto() {
        return qrCodeTexto;
    }

    public void setQrCodeTexto(String qrCodeTexto) {
        this.qrCodeTexto = qrCodeTexto;
    }

    public String getClienteNome() {
        return clienteNome;
    }

    public void setClienteNome(String clienteNome) {
        this.clienteNome = clienteNome;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public java.math.BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(java.math.BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public java.math.BigDecimal getImpostoTotal() {
        return impostoTotal;
    }

    public void setImpostoTotal(java.math.BigDecimal impostoTotal) {
        this.impostoTotal = impostoTotal;
    }

    public java.math.BigDecimal getTotal() {
        return total;
    }

    public void setTotal(java.math.BigDecimal total) {
        this.total = total;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDateTime getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(LocalDateTime dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

}
