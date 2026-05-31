package com.smartsoft.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

public class UtilizadorRequest implements Serializable {

    @NotBlank
    @Size(max = 100)
    private String nomeUtilizador;

    @NotBlank
    @Email
    @Size(max = 255)
    private String email;

    @NotBlank
    @Size(min = 8, max = 100)
    private String senha;

    @NotBlank
    @Pattern(regexp = "administrador|operador")
    private String perfil;

    public UtilizadorRequest() {}

    public String getNomeUtilizador() { return nomeUtilizador; }
    public void setNomeUtilizador(String nomeUtilizador) { this.nomeUtilizador = nomeUtilizador; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public String getPerfil() { return perfil; }
    public void setPerfil(String perfil) { this.perfil = perfil; }
}
