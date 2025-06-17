package com.senaifatesg.pi2025.presentation.dtos.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RuaRequestDTO {
    
    @NotNull(message = "O ID do bairro de origem é obrigatório")
    private Long origemId;
    
    @NotNull(message = "O ID do bairro de destino é obrigatório")
    private Long destinoId;
    
    @NotNull(message = "A distância é obrigatória")
    @Positive(message = "A distância deve ser maior que zero")
    private Double distancia;
}
