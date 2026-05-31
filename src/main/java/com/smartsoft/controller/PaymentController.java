package com.smartsoft.controller;

import com.smartsoft.dto.PaymentDto;
import com.smartsoft.service.GenericCrudService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final GenericCrudService crudService;

    public PaymentController(GenericCrudService crudService) {
        this.crudService = crudService;
    }

    @PostMapping
    public ResponseEntity<PaymentDto> record(@Valid @RequestBody PaymentDto dto) {
        return ResponseEntity.ok(crudService.save("payments", UUID.randomUUID(), dto));
    }

    @GetMapping("/history")
    public ResponseEntity<List<PaymentDto>> history() {
        return ResponseEntity.ok(crudService.list("payments"));
    }
}
