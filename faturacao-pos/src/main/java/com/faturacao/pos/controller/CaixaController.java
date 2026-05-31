package com.faturacao.pos.controller;

import com.faturacao.pos.model.MovimentoCaixa;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class CaixaController implements Serializable {

    private List<MovimentoCaixa> movimentos;
    private MovimentoCaixa movimentoForm;

    @PostConstruct
    public void init() {
        movimentos = new ArrayList<>();
        movimentoForm = new MovimentoCaixa();
        movimentoForm.setValor(BigDecimal.ZERO);
    }

    public void adicionar() {
        movimentos.add(movimentoForm);
        movimentoForm = new MovimentoCaixa();
        movimentoForm.setValor(BigDecimal.ZERO);
    }

    public List<MovimentoCaixa> getMovimentos() { return movimentos; }
    public MovimentoCaixa getMovimentoForm() { return movimentoForm; }
    public void setMovimentoForm(MovimentoCaixa movimentoForm) { this.movimentoForm = movimentoForm; }
}
