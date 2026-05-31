package com.faturacao.crm.controller;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class AuthController implements Serializable {

    private String email;
    private String password;
    private boolean autenticado;
    private String perfil = "ADMINISTRADOR";

    public String login() {
        if (email == null || email.isBlank() || password == null || password.isBlank()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Credenciais inválidas.", null));
            return null;
        }
        autenticado = true;
        if (email.toLowerCase().contains("parceiro")) {
            perfil = "PARCEIRO";
        }
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("crmUser", email);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("crmPerfil", perfil);
        return "/dashboard.xhtml?faces-redirect=true";
    }

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        autenticado = false;
        return "/login.xhtml?faces-redirect=true";
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public boolean isAutenticado() { return autenticado; }
    public String getPerfil() { return perfil; }
}
