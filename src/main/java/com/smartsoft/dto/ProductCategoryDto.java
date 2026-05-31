package com.smartsoft.dto;

import javax.validation.constraints.NotBlank;

public class ProductCategoryDto {
    @NotBlank
    private String nomeCategoria;

    public String getNomeCategoria() { return nomeCategoria; }
    public void setNomeCategoria(String nomeCategoria) { this.nomeCategoria = nomeCategoria; }
}
