package com.faturacao.crm.controller;

import com.faturacao.crm.service.ApiKeyService;
import com.faturacao.crm.service.LicencaService;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

@WebServlet(urlPatterns = "/api/licencas")
public class ApiController extends HttpServlet {

    @Inject
    private LicencaService licencaService;

    @Inject
    private ApiKeyService apiKeyService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String tenant = req.getParameter("tenant");
        if (!validarAcesso(req, resp, tenant)) {
            return;
        }

        boolean activa = licencaService.validarLicenca(tenant);
        JsonObject resposta = Json.createObjectBuilder()
                .add("autorizado", true)
                .add("tenant", tenant)
                .add("licencaActiva", activa)
                .build();
        escrever(resp, resposta);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String tenant = req.getParameter("tenant");
        if (!validarAcesso(req, resp, tenant)) {
            return;
        }

        JsonObject payload;
        try (Reader requestReader = req.getReader()) {
            StringBuilder builder = new StringBuilder();
            char[] buffer = new char[1024];
            int read;
            while ((read = requestReader.read(buffer)) != -1) {
                builder.append(buffer, 0, read);
            }
            try (JsonReader reader = Json.createReader(new StringReader(builder.toString()))) {
                payload = reader.readObject();
            }
        }

        JsonObject resposta = Json.createObjectBuilder()
                .add("autorizado", true)
                .add("tenant", tenant)
                .add("sincronizado", true)
                .add("documento", payload.getString("numero", "sem-numero"))
                .build();
        escrever(resp, resposta);
    }

    private boolean validarAcesso(HttpServletRequest req, HttpServletResponse resp, String tenant) throws IOException {
        String apiKey = req.getHeader("X-API-Key");
        if (apiKey == null || apiKey.isBlank()) {
            apiKey = req.getParameter("apiKey");
        }
        boolean autorizado = tenant != null && apiKey != null && apiKeyService.validar(tenant, apiKey);
        if (!autorizado) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            escrever(resp, Json.createObjectBuilder().add("autorizado", false).add("mensagem", "API key inválida.").build());
        }
        return autorizado;
    }

    private void escrever(HttpServletResponse resp, JsonObject json) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(json.toString());
    }
}
