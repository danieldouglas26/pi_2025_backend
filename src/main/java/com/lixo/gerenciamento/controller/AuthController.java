package com.lixo.gerenciamento.controller;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lixo.gerenciamento.model.dto.LoginRequest;
import com.lixo.gerenciamento.model.dto.LoginResponse;
import com.lixo.gerenciamento.model.entity.Usuario;
import com.lixo.gerenciamento.model.enuns.Role;
import com.lixo.gerenciamento.service.AuthenticationService;
import com.lixo.gerenciamento.service.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    @Autowired
    private AuthenticationService authenticationService;
    
    @Autowired
    private UsuarioService usuarioService;
    
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        LoginResponse response = authenticationService.autenticar(loginRequest);
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/registrar")
    public ResponseEntity<LoginResponse> registrar(@Valid @RequestBody Usuario usuario) {
        // Por padrão, novos usuários recebem role VISITANTE
        Set<Role> roles = new HashSet<>();
        roles.add(Role.ROLE_VISITANTE);
        
        Usuario usuarioRegistrado = authenticationService.registrar(usuario, roles);
        
        LoginRequest loginRequest = new LoginRequest(
            usuarioRegistrado.getEmail(),
            usuario.getSenha() // Senha em texto plano (será reautenticada)
        );
        
        LoginResponse response = authenticationService.autenticar(loginRequest);
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String token) {
        String jwt = token.substring(7); // Remove "Bearer "
        authenticationService.logout(jwt);
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refreshToken(@RequestParam String refreshToken) {
        LoginResponse response = authenticationService.refreshToken(refreshToken);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/me")
    public ResponseEntity<Usuario> getUsuarioAtual() {
        String email = org.springframework.security.core.context.SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
        
        Usuario usuario = usuarioService.loadUsuarioByEmail(email);
        usuario.setSenha(null); // Não retornar senha
        return ResponseEntity.ok(usuario);
    }
}