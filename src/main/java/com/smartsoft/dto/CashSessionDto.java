package com.smartsoft.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class CashSessionDto {
    @NotBlank
    private String numeroCaixa;
    @NotNull
    private BigDecimal saldoAbertura;

    public String getNumeroCaixa() { return numeroCaixa; }
    public void setNumeroCaixa(String numeroCaixa) { this.numeroCaixa = numeroCaixa; }
    public BigDecimal getSaldoAbertura() { return saldoAbertura; }
    public void setSaldoAbertura(BigDecimal saldoAbertura) { this.saldoAbertura = saldoAbertura; }
}
