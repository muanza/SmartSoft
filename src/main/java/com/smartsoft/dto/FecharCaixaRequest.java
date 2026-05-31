package com.smartsoft.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;

public class FecharCaixaRequest implements Serializable {

    @NotNull
    @DecimalMin("0.00")
    private BigDecimal saldoFinal;

    private String observacoes;

    public FecharCaixaRequest() {}

    public BigDecimal getSaldoFinal() { return saldoFinal; }
    public void setSaldoFinal(BigDecimal saldoFinal) { this.saldoFinal = saldoFinal; }

    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }
}
