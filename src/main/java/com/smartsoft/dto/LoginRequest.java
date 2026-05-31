package com.smartsoft.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

public class LoginRequest implements Serializable {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 6, max = 100)
    private String senha;

    @NotBlank
    private String tenantNif;

    public LoginRequest() {}

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public String getTenantNif() { return tenantNif; }
    public void setTenantNif(String tenantNif) { this.tenantNif = tenantNif; }
}
