package com.faturacao.pos.service;

import com.faturacao.pos.dao.FacturaDAO;
import com.faturacao.pos.model.Factura;
import com.faturacao.pos.model.LinhaFactura;
import com.faturacao.pos.model.SerieFactura;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
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

    @PersistenceContext(unitName = "posPU")
    private EntityManager entityManager;

    @Transactional
    public Factura emitir(String tenantNif, String idioma, String clienteNome, List<LinhaFactura> linhas) {
        SerieFactura serie = obterSerieActiva(tenantNif);
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
        entityManager.merge(serie);
        return facturaDAO.guardar(factura);
    }

    private SerieFactura obterSerieActiva(String tenantNif) {
        List<SerieFactura> series = entityManager.createQuery(
                        "select s from SerieFactura s where s.tenantNif = :tenant and s.activo = true order by s.codigo",
                        SerieFactura.class)
                .setParameter("tenant", tenantNif)
                .setMaxResults(1)
                .getResultList();
        if (!series.isEmpty()) {
            return series.get(0);
        }
        SerieFactura serie = new SerieFactura();
        serie.setTenantNif(tenantNif);
        serie.setCodigo("FT");
        serie.setTipoDocumento("FACTURA");
        return entityManager.merge(serie);
    }
}
