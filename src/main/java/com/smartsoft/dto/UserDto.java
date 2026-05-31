package com.smartsoft.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class UserDto {
    @NotBlank
    private String nomeUtilizador;
    @NotBlank @Email
    private String email;
    @NotBlank
    private String perfil;

    public String getNomeUtilizador() { return nomeUtilizador; }
    public void setNomeUtilizador(String nomeUtilizador) { this.nomeUtilizador = nomeUtilizador; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPerfil() { return perfil; }
    public void setPerfil(String perfil) { this.perfil = perfil; }
}
