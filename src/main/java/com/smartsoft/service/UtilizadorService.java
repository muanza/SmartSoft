package com.smartsoft.service;

import com.smartsoft.dto.UtilizadorRequest;
import com.smartsoft.entity.Tenant;
import com.smartsoft.entity.Utilizador;
import com.smartsoft.exception.BusinessException;
import com.smartsoft.exception.ResourceNotFoundException;
import com.smartsoft.repository.TenantRepository;
import com.smartsoft.repository.UtilizadorRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class UtilizadorService {

    private final UtilizadorRepository utilizadorRepository;
    private final TenantRepository tenantRepository;
    private final PasswordEncoder passwordEncoder;

    public UtilizadorService(UtilizadorRepository utilizadorRepository,
                              TenantRepository tenantRepository,
                              PasswordEncoder passwordEncoder) {
        this.utilizadorRepository = utilizadorRepository;
        this.tenantRepository = tenantRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Utilizador criarUtilizador(UtilizadorRequest request, String tenantNif) {
        Tenant tenant = tenantRepository.findByNifAndAtivoTrue(tenantNif)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant", "nif", tenantNif));

        if (utilizadorRepository.existsByEmailAndTenantNif(request.getEmail(), tenantNif)) {
            throw new BusinessException("Já existe um utilizador com o email: " + request.getEmail());
        }

        Utilizador u = new Utilizador();
        u.setTenant(tenant);
        u.setNomeUtilizador(request.getNomeUtilizador());
        u.setEmail(request.getEmail());
        u.setSenhaHash(passwordEncoder.encode(request.getSenha()));
        u.setPerfil(request.getPerfil() != null ? request.getPerfil() : "operador");
        u.setAtivo(true);
        u.setBloqueado(false);
        u.setNumeroTentativasFalhas(0);

        return utilizadorRepository.save(u);
    }

    @Transactional(readOnly = true)
    public List<Utilizador> listarUtilizadores(String tenantNif) {
        return utilizadorRepository.findByTenantNif(tenantNif);
    }

    @Transactional(readOnly = true)
    public Utilizador buscarUtilizador(UUID id, String tenantNif) {
        Utilizador u = utilizadorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilizador", "id", id));
        if (!u.getTenant().getNif().equals(tenantNif)) {
            throw new BusinessException("Acesso negado a este utilizador");
        }
        return u;
    }

    @Transactional
    public Utilizador atualizarUtilizador(UUID id, UtilizadorRequest request, String tenantNif) {
        Utilizador u = buscarUtilizador(id, tenantNif);
        if (request.getNomeUtilizador() != null) u.setNomeUtilizador(request.getNomeUtilizador());
        if (request.getPerfil() != null) u.setPerfil(request.getPerfil());
        return utilizadorRepository.save(u);
    }

    @Transactional
    public void alterarSenha(UUID id, String senhaAtual, String novaSenha, String tenantNif) {
        Utilizador u = buscarUtilizador(id, tenantNif);
        if (!passwordEncoder.matches(senhaAtual, u.getSenhaHash())) {
            throw new BusinessException("Senha atual incorreta");
        }
        u.setSenhaHash(passwordEncoder.encode(novaSenha));
        u.setNumeroTentativasFalhas(0);
        u.setBloqueado(false);
        utilizadorRepository.save(u);
    }

    @Transactional
    public void desbloquearUtilizador(UUID id, String tenantNif) {
        Utilizador u = buscarUtilizador(id, tenantNif);
        u.setBloqueado(false);
        u.setNumeroTentativasFalhas(0);
        utilizadorRepository.save(u);
    }

    @Transactional
    public void desativarUtilizador(UUID id, String tenantNif) {
        Utilizador u = buscarUtilizador(id, tenantNif);
        u.setAtivo(false);
        utilizadorRepository.save(u);
    }
}
