package com.smartsoft.controller;

import com.smartsoft.dto.AuthRequestDto;
import com.smartsoft.dto.AuthResponseDto;
import com.smartsoft.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@Validated
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@Valid @RequestBody AuthRequestDto request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String token) {
        authService.logout(token);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/permissions")
    public ResponseEntity<String[]> permissions() {
        return ResponseEntity.ok(new String[]{"TENANT_READ", "INVOICE_WRITE"});
    }
}
