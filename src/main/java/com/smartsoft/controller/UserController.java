package com.smartsoft.controller;

import com.smartsoft.dto.PasswordChangeDto;
import com.smartsoft.dto.UserDto;
import com.smartsoft.service.GenericCrudService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final GenericCrudService crudService;

    public UserController(GenericCrudService crudService) {
        this.crudService = crudService;
    }

    @PostMapping
    public ResponseEntity<UserDto> create(@Valid @RequestBody UserDto dto) {
        return ResponseEntity.ok(crudService.save("users", UUID.randomUUID(), dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> update(@PathVariable UUID id, @Valid @RequestBody UserDto dto) {
        return ResponseEntity.ok(crudService.save("users", id, dto));
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> list() {
        return ResponseEntity.ok(crudService.list("users"));
    }

    @PostMapping("/{id}/change-password")
    public ResponseEntity<Void> changePassword(@PathVariable UUID id, @Valid @RequestBody PasswordChangeDto request) {
        return ResponseEntity.noContent().build();
    }
}
