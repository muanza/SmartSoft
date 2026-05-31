package com.smartsoft.entity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.UUID;

@Entity
@Table(name = "utilizadores_crm")
public class UtilizadorCrm extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "id_utilizador_crm")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_parceiro")
    private Parceiro parceiro;

    @NotBlank
    @Size(max = 100)
    @Column(name = "nome_utilizador", nullable = false)
    private String nomeUtilizador;

    @NotBlank
    @Email
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @NotBlank
    @Column(name = "senha_hash", nullable = false)
    private String senhaHash;

    @Override
    public UUID getId() {
        return id;
    }
}
