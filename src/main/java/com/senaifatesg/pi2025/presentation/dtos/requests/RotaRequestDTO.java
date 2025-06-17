package com.senaifatesg.pi2025.presentation.dtos.requests;

import com.senaifatesg.pi2025.common.enums.TipoResiduo;

import jakarta.validation.constraints.NotNull;
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
public class RotaRequestDTO {
    
    @NotNull(message = "ID do caminhão é obrigatório")
    private Long caminhaoId;
    
    @NotNull(message = "ID do bairro de origem é obrigatório")
    private Long origemId;
    
    @NotNull(message = "ID do bairro de destino é obrigatório")
    private Long destinoId;
    
    @NotNull(message = "Tipo de resíduo é obrigatório")
    private TipoResiduo tipoResiduo;
    
    private String nome; 
}
