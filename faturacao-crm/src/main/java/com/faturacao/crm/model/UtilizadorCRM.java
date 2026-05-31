package com.faturacao.crm.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Table(name = "utilizadores_crm")

public class UtilizadorCRM implements Serializable {

    @Id
    @Column(length = 36)
    private String id = UUID.randomUUID().toString();
    @Column(nullable = false, length = 150)
    private String nome;
    @Column(nullable = false, unique = true, length = 150)
    private String email;
    @Column(name = "palavra_passe_hash", nullable = false, length = 255)
    private String palavraPasseHash;
    @Column(nullable = false, length = 30)
    private String perfil = "PARCEIRO";
    @Column(nullable = false)
    private Boolean activo = Boolean.TRUE;
    @Column(name = "parceiro_id", length = 36)
    private String parceiroId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPalavraPasseHash() {
        return palavraPasseHash;
    }

    public void setPalavraPasseHash(String palavraPasseHash) {
        this.palavraPasseHash = palavraPasseHash;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public String getParceiroId() {
        return parceiroId;
    }

    public void setParceiroId(String parceiroId) {
        this.parceiroId = parceiroId;
    }

}
