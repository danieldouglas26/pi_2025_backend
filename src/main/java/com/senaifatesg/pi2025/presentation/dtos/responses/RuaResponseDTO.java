package com.senaifatesg.pi2025.presentation.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RuaResponseDTO {
    private Long id;
    private Long origemId;
    private String origemNome;
    private Long destinoId;
    private String destinoNome;
    private Double distancia;
    
    public static RuaResponseDTOBuilder builder() {
        return new RuaResponseDTOBuilder();
    }
}