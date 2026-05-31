package com.smartsoft.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

public class AlterarSenhaRequest implements Serializable {

    @NotBlank
    private String senhaAtual;

    @NotBlank
    @Size(min = 8, max = 100)
    private String novaSenha;

    @NotBlank
    private String confirmarNovaSenha;

    public AlterarSenhaRequest() {}

    public String getSenhaAtual() { return senhaAtual; }
    public void setSenhaAtual(String senhaAtual) { this.senhaAtual = senhaAtual; }

    public String getNovaSenha() { return novaSenha; }
    public void setNovaSenha(String novaSenha) { this.novaSenha = novaSenha; }

    public String getConfirmarNovaSenha() { return confirmarNovaSenha; }
    public void setConfirmarNovaSenha(String confirmarNovaSenha) { this.confirmarNovaSenha = confirmarNovaSenha; }
}
