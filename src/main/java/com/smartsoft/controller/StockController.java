package com.smartsoft.controller;

import com.smartsoft.dto.StockMovementDto;
import com.smartsoft.service.GenericCrudService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/stock")
public class StockController {

    private final GenericCrudService crudService;

    public StockController(GenericCrudService crudService) {
        this.crudService = crudService;
    }

    @GetMapping("/levels")
    public ResponseEntity<List<StockMovementDto>> levels() {
        return ResponseEntity.ok(crudService.list("stock-levels"));
    }

    @PostMapping("/movements")
    public ResponseEntity<StockMovementDto> registerMovement(@Valid @RequestBody StockMovementDto dto) {
        return ResponseEntity.ok(crudService.save("stock-movements", UUID.randomUUID(), dto));
    }

    @GetMapping("/movements")
    public ResponseEntity<List<StockMovementDto>> movements() {
        return ResponseEntity.ok(crudService.list("stock-movements"));
    }
}
