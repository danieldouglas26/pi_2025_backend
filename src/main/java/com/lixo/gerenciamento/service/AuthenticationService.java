package com.lixo.gerenciamento.service;


import com.lixo.gerenciamento.service.*;
import com.lixo.gerenciamento.model.dto.LoginRequest;
import com.lixo.gerenciamento.model.dto.LoginResponse;
import com.lixo.gerenciamento.model.entity.Usuario;
import com.lixo.gerenciamento.model.enuns.Role;
import com.lixo.gerenciamento.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthenticationService {
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private JwtService jwtService;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public LoginResponse autenticar(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(),
                loginRequest.getSenha()
            )
        );
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        Usuario usuario = (Usuario) authentication.getPrincipal();
        
        // Atualizar último login
        usuario.setUltimoLogin(LocalDateTime.now());
        usuarioRepository.save(usuario);
        
        // Gerar token
        String jwt = jwtService.generateToken(usuario);
        
        // Extrair roles
        Set<String> roles = usuario.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());
        
        Long expiraEm = jwtService.extractExpiration(jwt).getTime();
        
        return new LoginResponse(
            jwt,
            usuario.getId(),
            usuario.getEmail(),
            usuario.getNome(),
            roles,
            expiraEm
        );
    }
    
    public Usuario registrar(Usuario usuario, Set<Role> roles) {
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new RuntimeException("Email já está em uso");
        }
        
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        usuario.setRoles(roles);
        usuario.setAtivo(true);
        
        return usuarioRepository.save(usuario);
    }
    
    public void logout(String token) {
        // Em um sistema mais complexo, você poderia adicionar o token a uma blacklist
        // ou usar Redis para invalidar tokens
        SecurityContextHolder.clearContext();
    }
    
    public LoginResponse refreshToken(String refreshToken) {
        String username = jwtService.extractUsername(refreshToken);
        
        Usuario usuario = usuarioRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        
        if (!jwtService.canTokenBeRefreshed(refreshToken)) {
            throw new RuntimeException("Token de refresh expirado");
        }
        
        String newToken = jwtService.generateToken(usuario);
        Long expiraEm = jwtService.extractExpiration(newToken).getTime();
        
        Set<String> roles = usuario.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());
        
        return new LoginResponse(
            newToken,
            usuario.getId(),
            usuario.getEmail(),
            usuario.getNome(),
            roles,
            expiraEm
        );
    }
}