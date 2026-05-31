package com.faturacao.pos.controller;

import com.faturacao.pos.dao.ProdutoDAO;
import com.faturacao.pos.model.Produto;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class ProdutoController implements Serializable {

    @Inject
    private ProdutoDAO produtoDAO;

    @Inject
    private POSController posController;

    private List<Produto> produtos;
    private Produto produtoForm;

    @PostConstruct
    public void init() {
        produtos = new ArrayList<>(produtoDAO.listarTodos());
        produtoForm = new Produto();
        produtoForm.setTenantNif(posController.getTenantNif());
    }

    public void salvar() {
        produtoDAO.guardar(produtoForm);
        produtos = new ArrayList<>(produtoDAO.listarTodos());
        produtoForm = new Produto();
        produtoForm.setTenantNif(posController.getTenantNif());
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Produto guardado."));
    }

    public List<Produto> getProdutos() { return produtos; }
    public Produto getProdutoForm() { return produtoForm; }
    public void setProdutoForm(Produto produtoForm) { this.produtoForm = produtoForm; }
}
