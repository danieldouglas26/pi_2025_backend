package com.senaifatesg.pi2025.presentation.dtos.requests;

import java.util.List;

import com.senaifatesg.pi2025.common.enums.TipoResiduo;

import jakarta.validation.constraints.NotBlank;
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
public class CaminhaoRequestDTO {
    
    @NotBlank(message = "A placa do caminhão é obrigatória")
    private String placa;
    
    @NotBlank(message = "O nome do motorista é obrigatório")
    private String nomeMotorista;
    
    @NotNull(message = "A capacidade do caminhão é obrigatória")
    @Positive(message = "A capacidade deve ser um valor positivo")
    private Double capacidade;
    
    @NotNull(message = "A lista de tipos de resíduos é obrigatória")
    private List<TipoResiduo> tipoResiduos;


}