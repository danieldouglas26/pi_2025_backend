package com.senaifatesg.pi2025.presentation.dtos.responses;

import java.time.LocalDate;
import java.util.List;

import com.senaifatesg.pi2025.common.enums.TipoResiduo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItinerarioResponseDTO {

    private Long id;
    private Long rotaId;
    private String rotaNome;
    private Long caminhaoId;
    private String caminhaoPlaca;
    private String motorista;
    private LocalDate data;
    private Double distanciaTotal;
    private TipoResiduo tipoResiduo;
    private boolean concluido;
    private List<ParadaItinerarioResponseDTO> paradas;
}
