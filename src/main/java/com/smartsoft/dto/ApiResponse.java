package com.smartsoft.dto;

import java.io.Serializable;

public class ApiResponse<T> implements Serializable {

    private boolean sucesso;
    private String mensagem;
    private T dados;
    private String erro;

    public ApiResponse() {}

    public static <T> ApiResponse<T> sucesso(T dados) {
        ApiResponse<T> response = new ApiResponse<>();
        response.sucesso = true;
        response.dados = dados;
        return response;
    }

    public static <T> ApiResponse<T> sucesso(String mensagem, T dados) {
        ApiResponse<T> response = new ApiResponse<>();
        response.sucesso = true;
        response.mensagem = mensagem;
        response.dados = dados;
        return response;
    }

    public static <T> ApiResponse<T> erro(String mensagem) {
        ApiResponse<T> response = new ApiResponse<>();
        response.sucesso = false;
        response.erro = mensagem;
        return response;
    }

    public boolean isSucesso() { return sucesso; }
    public void setSucesso(boolean sucesso) { this.sucesso = sucesso; }

    public String getMensagem() { return mensagem; }
    public void setMensagem(String mensagem) { this.mensagem = mensagem; }

    public T getDados() { return dados; }
    public void setDados(T dados) { this.dados = dados; }

    public String getErro() { return erro; }
    public void setErro(String erro) { this.erro = erro; }
}
