package com.senaifatesg.pi2025.presentation.dtos.responses;

import java.util.List;

import com.senaifatesg.pi2025.common.enums.TipoResiduo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RotaResponseDTO {
    private Long id;
    private String nome;
    private Long caminhaoId;
    private String caminhaoPlaca;
    private Double distanciaTotalKm;
    private TipoResiduo tiposResiduos;
    private List<ParadaRotaResponseDTO> paradas;
    private List<ParadaPontoColetaResponseDTO> pontosColeta;
}
