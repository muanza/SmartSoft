package com.smartsoft.service;

import com.smartsoft.dto.AuthRequestDto;
import com.smartsoft.dto.AuthResponseDto;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    public AuthResponseDto login(AuthRequestDto request) {
        return new AuthResponseDto("token-" + request.getEmail().hashCode(), "operador");
    }

    public void logout(String token) {
        // token invalidation hook
    }
}
