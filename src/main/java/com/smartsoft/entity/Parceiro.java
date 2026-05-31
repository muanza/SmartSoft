package com.smartsoft.entity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "parceiros")
public class Parceiro extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "id_parceiro")
    private UUID id;

    @NotBlank
    @Size(max = 255)
    @Column(name = "nome_parceiro", nullable = false)
    private String nomeParceiro;

    @NotBlank
    @Email
    @Column(name = "email_parceiro", nullable = false, unique = true)
    private String emailParceiro;

    @Pattern(regexp = "^$|^[+0-9 -]{7,20}$")
    @Column(name = "telefone_parceiro", length = 20)
    private String telefoneParceiro;

    @ManyToMany(mappedBy = "parceiros")
    private Set<Tenant> tenants = new LinkedHashSet<>();

    @Override
    public UUID getId() {
        return id;
    }
}
