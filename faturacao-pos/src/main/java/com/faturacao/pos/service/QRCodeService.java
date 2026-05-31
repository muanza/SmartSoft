package com.faturacao.pos.service;

import com.faturacao.pos.model.Factura;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class QRCodeService {

    public String gerarConteudo(Factura factura) {
        return String.join(";",
                "NIF=" + factura.getTenantNif(),
                "NUM=" + factura.getNumero(),
                "TOTAL=" + factura.getTotal(),
                "HASH=" + factura.getHashFiscal());
    }
}
