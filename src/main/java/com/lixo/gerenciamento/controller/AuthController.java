package com.lixo.gerenciamento.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lixo.gerenciamento.model.dto.request.LoginRequest;
import com.lixo.gerenciamento.model.dto.request.UserRequestDTO;
import com.lixo.gerenciamento.model.dto.response.ApiResponse;
import com.lixo.gerenciamento.model.dto.response.AuthResponse;
import com.lixo.gerenciamento.model.entity.User;
import com.lixo.gerenciamento.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest loginRequest) {
        AuthResponse response = authService.login(loginRequest);
        return ResponseEntity.ok(ApiResponse.success(response, "Login successful"));
    }

     @PostMapping("/register")
     @PreAuthorize("hasRole('ADMIN')")
     public ResponseEntity<ApiResponse<AuthResponse.UserInfo>> register(@Valid @RequestBody UserRequestDTO registerRequest) {
         User newUser = authService.registerUser(registerRequest.getUsername(), registerRequest.getPassword(), registerRequest.getRole());
         AuthResponse.UserInfo userInfo = new AuthResponse.UserInfo(newUser.getId().toString(), newUser.getUsername());
         return new ResponseEntity<>(ApiResponse.success(userInfo, "User registered successfully"), HttpStatus.CREATED);
     }
}