package com.senaifatesg.pi2025.presentation.dtos.responses;

import java.util.List;

import com.senaifatesg.pi2025.common.enums.TipoResiduo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParadaPontoColetaResponseDTO {
    private Long id;
    private Long pontoColetaId;
    private String pontoColetaNome;
    private List<TipoResiduo> tiposResiduo;
    private boolean coletado;
}
