package com.smartsoft.controller;

import com.smartsoft.dto.ApiResponseDto;
import com.smartsoft.dto.CashSessionDto;
import com.smartsoft.service.GenericCrudService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/cash-register")
public class CashRegisterController {

    private final GenericCrudService crudService;

    public CashRegisterController(GenericCrudService crudService) {
        this.crudService = crudService;
    }

    @PostMapping("/open")
    public ResponseEntity<CashSessionDto> open(@Valid @RequestBody CashSessionDto dto) {
        return ResponseEntity.ok(crudService.save("cash-sessions", UUID.randomUUID(), dto));
    }

    @PostMapping("/{id}/close")
    public ResponseEntity<ApiResponseDto> close(@PathVariable UUID id) {
        return ResponseEntity.ok(new ApiResponseDto("Cash session " + id + " closed"));
    }

    @GetMapping("/movements")
    public ResponseEntity<List<CashSessionDto>> movements() {
        return ResponseEntity.ok(crudService.list("cash-sessions"));
    }
}
