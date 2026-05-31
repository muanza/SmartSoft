package com.smartsoft.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;

public class AbrirCaixaRequest implements Serializable {

    @DecimalMin("0.00")
    private BigDecimal saldoInicial;

    private String observacoes;

    public AbrirCaixaRequest() {}

    public BigDecimal getSaldoInicial() { return saldoInicial; }
    public void setSaldoInicial(BigDecimal saldoInicial) { this.saldoInicial = saldoInicial; }

    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }
}
