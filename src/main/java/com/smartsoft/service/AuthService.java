package com.smartsoft.service;

import com.smartsoft.dto.LoginRequest;
import com.smartsoft.dto.LoginResponse;
import com.smartsoft.entity.Tenant;
import com.smartsoft.entity.Utilizador;
import com.smartsoft.exception.BusinessException;
import com.smartsoft.exception.ResourceNotFoundException;
import com.smartsoft.repository.TenantRepository;
import com.smartsoft.repository.UtilizadorRepository;
import com.smartsoft.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);
    private static final int MAX_FAILED_ATTEMPTS = 5;

    private final UtilizadorRepository utilizadorRepository;
    private final TenantRepository tenantRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UtilizadorRepository utilizadorRepository,
                       TenantRepository tenantRepository,
                       PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil) {
        this.utilizadorRepository = utilizadorRepository;
        this.tenantRepository = tenantRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Transactional
    public LoginResponse login(LoginRequest request, String ipAddress) {
        Tenant tenant = tenantRepository.findByNifAndAtivoTrue(request.getTenantNif())
                .orElseThrow(() -> new ResourceNotFoundException("Empresa não encontrada ou inativa"));

        Utilizador utilizador = utilizadorRepository
                .findByEmailAndTenant(request.getEmail(), tenant)
                .orElseThrow(() -> new BusinessException("Credenciais inválidas"));

        if (Boolean.TRUE.equals(utilizador.getBloqueado())) {
            throw new BusinessException("Utilizador bloqueado. Contacte o administrador.");
        }

        if (!utilizador.getAtivo()) {
            throw new BusinessException("Utilizador inativo.");
        }

        if (!passwordEncoder.matches(request.getSenha(), utilizador.getSenhaHash())) {
            int tentativas = utilizador.getNumeroTentativasFalhas() + 1;
            utilizador.setNumeroTentativasFalhas(tentativas);
            if (tentativas >= MAX_FAILED_ATTEMPTS) {
                utilizador.setBloqueado(true);
                log.warn("Utilizador {} bloqueado após {} tentativas falhadas", utilizador.getEmail(), tentativas);
            }
            utilizadorRepository.save(utilizador);
            throw new BusinessException("Credenciais inválidas");
        }

        utilizador.setNumeroTentativasFalhas(0);
        utilizador.setUltimoAcesso(LocalDateTime.now());
        utilizadorRepository.save(utilizador);

        String token = jwtUtil.generateToken(utilizador.getEmail(), tenant.getNif(), utilizador.getPerfil());

        log.info("Login bem-sucedido: {} @ {}", utilizador.getEmail(), tenant.getNif());

        return new LoginResponse(
                token,
                utilizador.getEmail(),
                utilizador.getNomeUtilizador(),
                utilizador.getPerfil(),
                tenant.getNif(),
                jwtUtil.getExpirationMs()
        );
    }
}
