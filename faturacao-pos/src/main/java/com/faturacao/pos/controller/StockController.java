package com.faturacao.pos.controller;

import com.faturacao.pos.dao.ProdutoDAO;
import com.faturacao.pos.model.Produto;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Named
@ViewScoped
public class StockController implements Serializable {

    @Inject
    private ProdutoDAO produtoDAO;

    private List<Produto> produtosEmRuptura;

    @PostConstruct
    public void init() {
        produtosEmRuptura = produtoDAO.listarTodos().stream()
                .filter(produto -> produto.getStockAtual().compareTo(new BigDecimal("5")) <= 0)
                .collect(Collectors.toList());
    }

    public List<Produto> getProdutosEmRuptura() { return produtosEmRuptura; }
}
