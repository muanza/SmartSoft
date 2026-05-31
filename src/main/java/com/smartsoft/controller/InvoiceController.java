package com.smartsoft.controller;

import com.smartsoft.dto.ApiResponseDto;
import com.smartsoft.dto.InvoiceDto;
import com.smartsoft.service.GenericCrudService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    private final GenericCrudService crudService;

    public InvoiceController(GenericCrudService crudService) {
        this.crudService = crudService;
    }

    @PostMapping
    public ResponseEntity<InvoiceDto> create(@Valid @RequestBody InvoiceDto dto) {
        return ResponseEntity.ok(crudService.save("invoices", UUID.randomUUID(), dto));
    }

    @GetMapping
    public ResponseEntity<List<InvoiceDto>> list() {
        return ResponseEntity.ok(crudService.list("invoices"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<InvoiceDto> update(@PathVariable UUID id, @Valid @RequestBody InvoiceDto dto) {
        return ResponseEntity.ok(crudService.save("invoices", id, dto));
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<ApiResponseDto> cancel(@PathVariable UUID id) {
        return ResponseEntity.ok(new ApiResponseDto("Invoice " + id + " cancelled"));
    }

    @PostMapping("/{id}/validate")
    public ResponseEntity<ApiResponseDto> validate(@PathVariable UUID id) {
        return ResponseEntity.ok(new ApiResponseDto("Invoice " + id + " validated"));
    }
}
