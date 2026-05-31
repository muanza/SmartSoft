package com.smartsoft.entity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.UUID;

@Entity
@Table(name = "utilizadores")
public class Utilizador extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "id_utilizador")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tenant", nullable = false)
    private Tenant tenant;

    @NotBlank
    @Size(max = 100)
    @Column(name = "nome_utilizador", nullable = false)
    private String nomeUtilizador;

    @NotBlank
    @Email
    @Column(name = "email", nullable = false)
    private String email;

    @NotBlank
    @Column(name = "senha_hash", nullable = false)
    private String senhaHash;

    @NotBlank
    @Size(max = 50)
    @Column(name = "perfil", nullable = false)
    private String perfil;

    @Override
    public UUID getId() {
        return id;
    }
}
