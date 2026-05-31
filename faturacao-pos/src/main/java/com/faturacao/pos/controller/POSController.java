package com.faturacao.pos.controller;

import com.faturacao.pos.model.LinhaFactura;
import com.faturacao.pos.model.SessaoCaixa;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class POSController implements Serializable {

    private boolean caixaAberta;
    private boolean bloqueado;
    private boolean fullScreen;
    private String tenantNif = "500000001";
    private SessaoCaixa sessaoActual = new SessaoCaixa();
    private LinhaFactura linhaActual = new LinhaFactura();
    private List<LinhaFactura> carrinho = new ArrayList<>();


    public String login() {
        javax.faces.context.FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("posUser", tenantNif);
        return "/pos.xhtml?faces-redirect=true";
    }

    public void abrirCaixa() {
        sessaoActual = new SessaoCaixa();
        sessaoActual.setTenantNif(tenantNif);
        sessaoActual.setCaixaCodigo("CX-01");
        sessaoActual.setOperador("Operador POS");
        sessaoActual.setValorAbertura(new BigDecimal("10000.00"));
        sessaoActual.setAbertaEm(LocalDateTime.now());
        caixaAberta = true;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Caixa aberta."));
    }

    public void fecharCaixa() {
        sessaoActual.setEstado("FECHADA");
        sessaoActual.setFechadaEm(LocalDateTime.now());
        caixaAberta = false;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Caixa fechada."));
    }

    public void adicionarLinha() {
        carrinho.add(linhaActual);
        linhaActual = new LinhaFactura();
    }

    public void bloquearEcrã() {
        bloqueado = !bloqueado;
    }

    public void alternarFullScreen() {
        fullScreen = !fullScreen;
    }

    public boolean isCaixaAberta() { return caixaAberta; }
    public boolean isBloqueado() { return bloqueado; }
    public boolean isFullScreen() { return fullScreen; }
    public String getTenantNif() { return tenantNif; }
    public void setTenantNif(String tenantNif) { this.tenantNif = tenantNif; }
    public SessaoCaixa getSessaoActual() { return sessaoActual; }
    public LinhaFactura getLinhaActual() { return linhaActual; }
    public void setLinhaActual(LinhaFactura linhaActual) { this.linhaActual = linhaActual; }
    public List<LinhaFactura> getCarrinho() { return carrinho; }
}
