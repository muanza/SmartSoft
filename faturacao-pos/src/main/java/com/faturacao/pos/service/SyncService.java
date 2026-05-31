package com.faturacao.pos.service;

import com.faturacao.pos.model.Factura;

import javax.enterprise.context.ApplicationScoped;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@ApplicationScoped
public class SyncService {

    public boolean sincronizarFactura(Factura factura, String crmApiUrl, String apiKey) {
        if (factura == null || crmApiUrl == null || crmApiUrl.isBlank() || apiKey == null || apiKey.isBlank()) {
            return false;
        }
        String payload = String.format("{\"numero\":\"%s\",\"tenant\":\"%s\",\"total\":\"%s\",\"hash\":\"%s\"}",
                factura.getNumero(), factura.getTenantNif(), factura.getTotal(), factura.getHashFiscal());
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(crmApiUrl + "?tenant=" + factura.getTenantNif()))
                .header("Content-Type", "application/json")
                .header("X-API-Key", apiKey)
                .POST(HttpRequest.BodyPublishers.ofString(payload))
                .build();
        try {
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            return response.statusCode() >= 200 && response.statusCode() < 300;
        } catch (IOException | InterruptedException exception) {
            if (exception instanceof InterruptedException) {
                Thread.currentThread().interrupt();
            }
            return false;
        }
    }
}
