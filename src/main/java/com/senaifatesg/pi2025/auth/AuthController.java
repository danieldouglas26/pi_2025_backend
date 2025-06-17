package com.senaifatesg.pi2025.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.senaifatesg.pi2025.auth.models.AuthResponse;
import com.senaifatesg.pi2025.auth.models.LoginRequest;
import com.senaifatesg.pi2025.auth.models.UserRequestDTO;
import com.senaifatesg.pi2025.common.ApiResponse;
import com.senaifatesg.pi2025.core.models.User;

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