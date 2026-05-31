package com.smartsoft.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public class FacturaResponse implements Serializable {

    private UUID idFactura;
    private String numeroFactura;
    private String numeroSerie;
    private LocalDate dataFactura;
    private LocalTime horaFactura;
    private String tipoFactura;
    private String clienteNome;
    private String clienteNif;
    private String clienteEmail;
    private String clienteTelefone;
    private String clienteMorada;
    private BigDecimal subtotal;
    private BigDecimal totalIva;
    private BigDecimal descontoPercentual;
    private BigDecimal descontoMontante;
    private BigDecimal totalLiquido;
    private String moeda;
    private String metodoPagamento;
    private BigDecimal montantePago;
    private BigDecimal troco;
    private String status;
    private String observacoes;
    private LocalDateTime dataCriacao;
    private List<LinhaFacturaResponse> linhas;

    public FacturaResponse() {}

    public UUID getIdFactura() { return idFactura; }
    public void setIdFactura(UUID idFactura) { this.idFactura = idFactura; }

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

    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }

    public List<LinhaFacturaResponse> getLinhas() { return linhas; }
    public void setLinhas(List<LinhaFacturaResponse> linhas) { this.linhas = linhas; }

    public static class LinhaFacturaResponse implements Serializable {
        private UUID idLinha;
        private String descricaoLinha;
        private BigDecimal quantidade;
        private BigDecimal precoUnitario;
        private String taxaIva;
        private BigDecimal montanteIva;
        private BigDecimal totalLinha;

        public UUID getIdLinha() { return idLinha; }
        public void setIdLinha(UUID idLinha) { this.idLinha = idLinha; }

        public String getDescricaoLinha() { return descricaoLinha; }
        public void setDescricaoLinha(String descricaoLinha) { this.descricaoLinha = descricaoLinha; }

        public BigDecimal getQuantidade() { return quantidade; }
        public void setQuantidade(BigDecimal quantidade) { this.quantidade = quantidade; }

        public BigDecimal getPrecoUnitario() { return precoUnitario; }
        public void setPrecoUnitario(BigDecimal precoUnitario) { this.precoUnitario = precoUnitario; }

        public String getTaxaIva() { return taxaIva; }
        public void setTaxaIva(String taxaIva) { this.taxaIva = taxaIva; }

        public BigDecimal getMontanteIva() { return montanteIva; }
        public void setMontanteIva(BigDecimal montanteIva) { this.montanteIva = montanteIva; }

        public BigDecimal getTotalLinha() { return totalLinha; }
        public void setTotalLinha(BigDecimal totalLinha) { this.totalLinha = totalLinha; }
    }
}
