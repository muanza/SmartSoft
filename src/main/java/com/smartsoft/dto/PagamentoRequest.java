package com.smartsoft.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;

public class PagamentoRequest implements Serializable {

    @NotNull
    @DecimalMin("0.01")
    private BigDecimal montantePagamento;

    @NotBlank
    private String metodoPagamento;

    @Size(max = 100)
    private String referenciaPagamento;

    private String observacoes;

    public PagamentoRequest() {}

    public BigDecimal getMontantePagamento() { return montantePagamento; }
    public void setMontantePagamento(BigDecimal montantePagamento) { this.montantePagamento = montantePagamento; }

    public String getMetodoPagamento() { return metodoPagamento; }
    public void setMetodoPagamento(String metodoPagamento) { this.metodoPagamento = metodoPagamento; }

    public String getReferenciaPagamento() { return referenciaPagamento; }
    public void setReferenciaPagamento(String referenciaPagamento) { this.referenciaPagamento = referenciaPagamento; }

    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }
}
