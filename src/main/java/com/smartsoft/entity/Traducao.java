package com.smartsoft.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.UUID;

@Entity
@Table(name = "traducoes")
public class Traducao extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "id_traducao")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tenant")
    private Tenant tenant;

    @NotBlank
    @Size(max = 255)
    @Column(name = "chave_traducao", nullable = false)
    private String chaveTraducao;

    @NotNull
    @Pattern(regexp = "pt|en|fr|zh")
    @Column(name = "idioma", nullable = false)
    private String idioma;

    @NotBlank
    @Column(name = "valor_traducao", nullable = false)
    private String valorTraducao;

    @Override
    public UUID getId() {
        return id;
    }
}
