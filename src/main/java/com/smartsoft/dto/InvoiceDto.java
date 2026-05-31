package com.smartsoft.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

public class InvoiceDto {
    @NotNull
    private UUID utilizadorId;
    @NotBlank
    private String numeroFactura;
    @NotBlank
    private String tipoFactura;
    @NotNull
    private BigDecimal totalLiquido;

    public UUID getUtilizadorId() { return utilizadorId; }
    public void setUtilizadorId(UUID utilizadorId) { this.utilizadorId = utilizadorId; }
    public String getNumeroFactura() { return numeroFactura; }
    public void setNumeroFactura(String numeroFactura) { this.numeroFactura = numeroFactura; }
    public String getTipoFactura() { return tipoFactura; }
    public void setTipoFactura(String tipoFactura) { this.tipoFactura = tipoFactura; }
    public BigDecimal getTotalLiquido() { return totalLiquido; }
    public void setTotalLiquido(BigDecimal totalLiquido) { this.totalLiquido = totalLiquido; }
}
