package com.smartsoft.controller;

import com.smartsoft.dto.TenantDto;
import com.smartsoft.service.GenericCrudService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tenants")
public class TenantController {

    private final GenericCrudService crudService;

    public TenantController(GenericCrudService crudService) {
        this.crudService = crudService;
    }

    @PostMapping
    public ResponseEntity<TenantDto> create(@Valid @RequestBody TenantDto dto) {
        return ResponseEntity.ok(crudService.save("tenants", UUID.randomUUID(), dto));
    }

    @GetMapping
    public ResponseEntity<List<TenantDto>> list() {
        return ResponseEntity.ok(crudService.list("tenants"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TenantDto> update(@PathVariable UUID id, @Valid @RequestBody TenantDto dto) {
        return ResponseEntity.ok(crudService.save("tenants", id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        crudService.delete("tenants", id);
        return ResponseEntity.noContent().build();
    }
}
