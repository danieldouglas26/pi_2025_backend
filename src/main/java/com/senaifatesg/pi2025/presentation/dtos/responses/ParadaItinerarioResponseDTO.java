package com.senaifatesg.pi2025.presentation.dtos.responses;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ParadaItinerarioResponseDTO {
    private int ordem;
    private Long bairroId;
    private String bairroNome;
    private List<PontoColetaResponseDTO> pontosColeta;
    private boolean coletado;
    private LocalDateTime horaColeta; 
}