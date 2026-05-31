package com.smartsoft.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class TenantDto {
    @NotBlank @Size(max = 20)
    private String nif;
    @NotBlank
    private String nomeEmpresa;
    @NotBlank @Email
    private String emailEmpresa;

    public String getNif() { return nif; }
    public void setNif(String nif) { this.nif = nif; }
    public String getNomeEmpresa() { return nomeEmpresa; }
    public void setNomeEmpresa(String nomeEmpresa) { this.nomeEmpresa = nomeEmpresa; }
    public String getEmailEmpresa() { return emailEmpresa; }
    public void setEmailEmpresa(String emailEmpresa) { this.emailEmpresa = emailEmpresa; }
}
