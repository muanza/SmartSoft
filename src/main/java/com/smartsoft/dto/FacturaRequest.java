package com.smartsoft.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class FacturaRequest implements Serializable {

    @NotBlank
    @Pattern(regexp = "venda|devolucao|orcamento|proforma")
    private String tipoFactura;

    private String clienteNome;
    private String clienteNif;

    @Email
    private String clienteEmail;

    private String clienteTelefone;
    private String clienteMorada;

    @Pattern(regexp = "dinheiro|cartao|cheque|transferencia|misto|outro")
    private String metodoPagamento;

    @NotNull
    @DecimalMin("0.00")
    private BigDecimal montantePago;

    @DecimalMin("0.00")
    @DecimalMax("100.00")
    private BigDecimal descontoPercentual;

    private String observacoes;
    private LocalDate dataFactura;
    private String moeda;
    private String caixaId;

    @NotNull
    @Size(min = 1)
    private List<LinhaFacturaRequest> linhas;

    public FacturaRequest() {}

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

    public String getMetodoPagamento() { return metodoPagamento; }
    public void setMetodoPagamento(String metodoPagamento) { this.metodoPagamento = metodoPagamento; }

    public BigDecimal getMontantePago() { return montantePago; }
    public void setMontantePago(BigDecimal montantePago) { this.montantePago = montantePago; }

    public BigDecimal getDescontoPercentual() { return descontoPercentual; }
    public void setDescontoPercentual(BigDecimal descontoPercentual) { this.descontoPercentual = descontoPercentual; }

    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }

    public LocalDate getDataFactura() { return dataFactura; }
    public void setDataFactura(LocalDate dataFactura) { this.dataFactura = dataFactura; }

    public String getMoeda() { return moeda; }
    public void setMoeda(String moeda) { this.moeda = moeda; }

    public String getCaixaId() { return caixaId; }
    public void setCaixaId(String caixaId) { this.caixaId = caixaId; }

    public List<LinhaFacturaRequest> getLinhas() { return linhas; }
    public void setLinhas(List<LinhaFacturaRequest> linhas) { this.linhas = linhas; }

    public static class LinhaFacturaRequest implements Serializable {

        private String produtoId;

        @NotBlank
        @Size(max = 255)
        private String descricaoLinha;

        @NotNull
        @DecimalMin("0.01")
        private BigDecimal quantidade;

        @NotNull
        @DecimalMin("0.00")
        private BigDecimal precoUnitario;

        @Pattern(regexp = "0%|7%|14%|17%")
        private String taxaIva = "17%";

        public String getProdutoId() { return produtoId; }
        public void setProdutoId(String produtoId) { this.produtoId = produtoId; }

        public String getDescricaoLinha() { return descricaoLinha; }
        public void setDescricaoLinha(String descricaoLinha) { this.descricaoLinha = descricaoLinha; }

        public BigDecimal getQuantidade() { return quantidade; }
        public void setQuantidade(BigDecimal quantidade) { this.quantidade = quantidade; }

        public BigDecimal getPrecoUnitario() { return precoUnitario; }
        public void setPrecoUnitario(BigDecimal precoUnitario) { this.precoUnitario = precoUnitario; }

        public String getTaxaIva() { return taxaIva; }
        public void setTaxaIva(String taxaIva) { this.taxaIva = taxaIva; }
    }
}
