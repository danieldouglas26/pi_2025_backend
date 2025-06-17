package com.senaifatesg.pi2025.presentation.dtos.responses;

import java.util.List;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Builder
public class ParadaRotaResponseDTO {
    private int ordem;
    private Long bairroId;
    private String bairroNome;
    private List<ParadaPontoColetaResponseDTO> pontosColeta;
}