package com.smartsoft.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "categorias_produtos")
public class CategoriaProduto extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "id_categoria")
    private UUID id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tenant", nullable = false)
    private Tenant tenant;

    @NotBlank
    @Size(max = 100)
    @Column(name = "nome_categoria", nullable = false)
    private String nomeCategoria;

    @OneToMany(mappedBy = "categoria")
    private Set<Produto> produtos = new LinkedHashSet<>();

    @Override
    public UUID getId() {
        return id;
    }
}
