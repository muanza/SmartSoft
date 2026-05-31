package com.smartsoft.entity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "tenants")
public class Tenant extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "id_tenant")
    private UUID id;

    @NotBlank
    @Size(max = 20)
    @Column(nullable = false, unique = true, length = 20)
    private String nif;

    @NotBlank
    @Size(max = 255)
    @Column(name = "nome_empresa", nullable = false)
    private String nomeEmpresa;

    @NotBlank
    @Email
    @Size(max = 255)
    @Column(name = "email_empresa", nullable = false)
    private String emailEmpresa;

    @Pattern(regexp = "^$|^[+0-9 -]{7,20}$")
    @Column(name = "telefone_empresa", length = 20)
    private String telefoneEmpresa;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @ManyToMany
    @JoinTable(name = "parceiro_tenant",
            joinColumns = @JoinColumn(name = "id_tenant"),
            inverseJoinColumns = @JoinColumn(name = "id_parceiro"))
    private Set<Parceiro> parceiros = new LinkedHashSet<>();

    @Override
    public UUID getId() {
        return id;
    }
}
