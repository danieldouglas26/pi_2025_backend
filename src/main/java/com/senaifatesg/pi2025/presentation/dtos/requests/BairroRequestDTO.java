package com.senaifatesg.pi2025.presentation.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BairroRequestDTO {
    @NotBlank(message = "O nome é obrigatório")
    private String nome;
}