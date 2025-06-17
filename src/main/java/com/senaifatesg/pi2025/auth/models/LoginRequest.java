package com.senaifatesg.pi2025.auth.models;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank(message = "É preciso informar o nome de usuario")
    private String username;

    @NotBlank(message = "É preciso informar a senha")
    private String password;
    
}