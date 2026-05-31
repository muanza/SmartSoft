package com.smartsoft.controller;

import com.smartsoft.dto.ApiResponse;
import com.smartsoft.dto.LoginRequest;
import com.smartsoft.dto.LoginResponse;
import com.smartsoft.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(
            @Valid @RequestBody LoginRequest request,
            HttpServletRequest httpRequest) {

        String ip = httpRequest.getRemoteAddr();
        LoginResponse response = authService.login(request, ip);
        return ResponseEntity.ok(ApiResponse.sucesso("Login realizado com sucesso", response));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout() {
        // JWT is stateless — logout is handled client-side by discarding the token.
        return ResponseEntity.ok(ApiResponse.sucesso("Sessão terminada com sucesso", null));
    }
}
