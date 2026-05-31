package com.faturacao.pos.service;

import com.faturacao.pos.dao.FacturaDAO;
import com.faturacao.pos.model.Factura;
import com.faturacao.pos.model.LinhaFactura;
import com.faturacao.pos.model.SerieFactura;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@ApplicationScoped
public class FacturaService {

    @Inject
    private FacturaDAO facturaDAO;

    @Inject
    private HashService hashService;

    @Inject
    private QRCodeService qrCodeService;

    public Factura emitir(String tenantNif, String idioma, String clienteNome, List<LinhaFactura> linhas, SerieFactura serie) {
        BigDecimal subtotal = BigDecimal.ZERO;
        BigDecimal imposto = BigDecimal.ZERO;
        for (LinhaFactura linha : linhas) {
            BigDecimal subtotalLinha = linha.getPrecoUnitario().multiply(linha.getQuantidade()).setScale(2, RoundingMode.HALF_UP);
            linha.setSubtotal(subtotalLinha);
            subtotal = subtotal.add(subtotalLinha);
            imposto = imposto.add(subtotalLinha.multiply(linha.getTaxaImposto()).divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP));
        }
        Factura factura = new Factura();
        factura.setTenantNif(tenantNif);
        factura.setIdioma(idioma);
        factura.setClienteNome(clienteNome);
        factura.setNumero(serie.getCodigo() + "-" + String.format("%06d", serie.getProximoNumero()));
        factura.setSubtotal(subtotal);
        factura.setImpostoTotal(imposto);
        factura.setTotal(subtotal.add(imposto));
        factura.setHashFiscal(hashService.gerarHash(factura.getNumero() + "|" + factura.getTotal()));
        factura.setQrCodeTexto(qrCodeService.gerarConteudo(factura));
        serie.setProximoNumero(serie.getProximoNumero() + 1);
        return facturaDAO.guardar(factura);
    }
}
