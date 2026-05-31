package com.smartsoft.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;

public class MovimentoCaixaRequest implements Serializable {

    @NotBlank
    @Pattern(regexp = "entrada|saida|devolucao")
    private String tipoMovimento;

    @Size(max = 255)
    private String descricao;

    @NotNull
    @DecimalMin("0.01")
    private BigDecimal montante;

    @Pattern(regexp = "dinheiro|cartao|cheque|transferencia|outro")
    private String metodoPagamento;

    private String referenciaFacturaId;

    public MovimentoCaixaRequest() {}

    public String getTipoMovimento() { return tipoMovimento; }
    public void setTipoMovimento(String tipoMovimento) { this.tipoMovimento = tipoMovimento; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public BigDecimal getMontante() { return montante; }
    public void setMontante(BigDecimal montante) { this.montante = montante; }

    public String getMetodoPagamento() { return metodoPagamento; }
    public void setMetodoPagamento(String metodoPagamento) { this.metodoPagamento = metodoPagamento; }

    public String getReferenciaFacturaId() { return referenciaFacturaId; }
    public void setReferenciaFacturaId(String referenciaFacturaId) { this.referenciaFacturaId = referenciaFacturaId; }
}
