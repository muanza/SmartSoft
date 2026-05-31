package com.faturacao.pos.controller;

import com.faturacao.pos.dao.ClienteDAO;
import com.faturacao.pos.model.Cliente;

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
public class ClienteController implements Serializable {

    @Inject
    private ClienteDAO clienteDAO;

    private List<Cliente> clientes;
    private Cliente clienteForm;

    @PostConstruct
    public void init() {
        clientes = new ArrayList<>(clienteDAO.listarTodos());
        clienteForm = new Cliente();
        clienteForm.setTenantNif("500000001");
    }

    public void salvar() {
        clienteDAO.guardar(clienteForm);
        clientes = new ArrayList<>(clienteDAO.listarTodos());
        clienteForm = new Cliente();
        clienteForm.setTenantNif("500000001");
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Cliente guardado."));
    }

    public List<Cliente> getClientes() { return clientes; }
    public Cliente getClienteForm() { return clienteForm; }
    public void setClienteForm(Cliente clienteForm) { this.clienteForm = clienteForm; }
}
