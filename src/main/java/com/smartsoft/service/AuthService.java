package com.smartsoft.service;

import com.smartsoft.dto.AuthRequestDto;
import com.smartsoft.dto.AuthResponseDto;
import com.smartsoft.exception.ApiException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AuthService {

    private final Set<String> activeTokens = ConcurrentHashMap.newKeySet();

    @Value("${smartsoft.auth.demo-user:admin@smartsoft.local}")
    private String demoUser;

    @Value("${smartsoft.auth.demo-password:ChangeMe123!}")
    private String demoPassword;

    public AuthResponseDto login(AuthRequestDto request) {
        if (!demoUser.equalsIgnoreCase(request.getEmail()) || !demoPassword.equals(request.getPassword())) {
            throw new ApiException("Invalid credentials");
        }
        String token = UUID.randomUUID().toString();
        activeTokens.add(token);
        return new AuthResponseDto(token, "administrador");
    }

    public void logout(String token) {
        if (token == null || token.isBlank()) {
            throw new ApiException("Token is required");
        }
        activeTokens.remove(token.replace("Bearer ", "").trim());
    }
}
