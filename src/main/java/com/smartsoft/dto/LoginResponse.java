package com.smartsoft.dto;

import java.io.Serializable;

public class LoginResponse implements Serializable {

    private String token;
    private String tipo;
    private String email;
    private String nomeUtilizador;
    private String perfil;
    private String tenantNif;
    private long expiresIn;

    public LoginResponse() {}

    public LoginResponse(String token, String email, String nomeUtilizador,
                         String perfil, String tenantNif, long expiresIn) {
        this.token = token;
        this.tipo = "Bearer";
        this.email = email;
        this.nomeUtilizador = nomeUtilizador;
        this.perfil = perfil;
        this.tenantNif = tenantNif;
        this.expiresIn = expiresIn;
    }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getNomeUtilizador() { return nomeUtilizador; }
    public void setNomeUtilizador(String nomeUtilizador) { this.nomeUtilizador = nomeUtilizador; }

    public String getPerfil() { return perfil; }
    public void setPerfil(String perfil) { this.perfil = perfil; }

    public String getTenantNif() { return tenantNif; }
    public void setTenantNif(String tenantNif) { this.tenantNif = tenantNif; }

    public long getExpiresIn() { return expiresIn; }
    public void setExpiresIn(long expiresIn) { this.expiresIn = expiresIn; }
}
