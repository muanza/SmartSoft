package com.smartsoft.controller;

import com.smartsoft.dto.ProductCategoryDto;
import com.smartsoft.dto.ProductDto;
import com.smartsoft.service.GenericCrudService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final GenericCrudService crudService;

    public ProductController(GenericCrudService crudService) {
        this.crudService = crudService;
    }

    @PostMapping("/categories")
    public ResponseEntity<ProductCategoryDto> createCategory(@Valid @RequestBody ProductCategoryDto dto) {
        return ResponseEntity.ok(crudService.save("categories", UUID.randomUUID(), dto));
    }

    @GetMapping("/categories")
    public ResponseEntity<List<ProductCategoryDto>> listCategories() {
        return ResponseEntity.ok(crudService.list("categories"));
    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto dto) {
        return ResponseEntity.ok(crudService.save("products", UUID.randomUUID(), dto));
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> listProducts() {
        return ResponseEntity.ok(crudService.list("products"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable UUID id, @Valid @RequestBody ProductDto dto) {
        return ResponseEntity.ok(crudService.save("products", id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID id) {
        crudService.delete("products", id);
        return ResponseEntity.noContent().build();
    }
}
