package com.smartsoft.repository;

import com.smartsoft.entity.CategoriaProduto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoriaProdutoRepository extends JpaRepository<CategoriaProduto, UUID> {
}
