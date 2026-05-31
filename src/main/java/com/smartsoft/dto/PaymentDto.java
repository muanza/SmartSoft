package com.smartsoft.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

public class PaymentDto {
    @NotNull
    private UUID facturaId;
    @NotBlank
    private String metodoPagamento;
    @NotNull
    private BigDecimal valorPago;

    public UUID getFacturaId() { return facturaId; }
    public void setFacturaId(UUID facturaId) { this.facturaId = facturaId; }
    public String getMetodoPagamento() { return metodoPagamento; }
    public void setMetodoPagamento(String metodoPagamento) { this.metodoPagamento = metodoPagamento; }
    public BigDecimal getValorPago() { return valorPago; }
    public void setValorPago(BigDecimal valorPago) { this.valorPago = valorPago; }
}
