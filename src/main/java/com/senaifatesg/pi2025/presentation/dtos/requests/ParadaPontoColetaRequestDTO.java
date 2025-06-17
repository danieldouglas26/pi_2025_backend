package com.senaifatesg.pi2025.presentation.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParadaPontoColetaRequestDTO {
    private Long pontoColetaId;
    private boolean coletado;
}
