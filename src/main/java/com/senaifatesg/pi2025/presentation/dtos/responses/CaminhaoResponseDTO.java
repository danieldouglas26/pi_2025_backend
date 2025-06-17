package com.senaifatesg.pi2025.presentation.dtos.responses;

import java.time.LocalDateTime;
import java.util.List;

import com.senaifatesg.pi2025.common.enums.TipoResiduo;

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
public class CaminhaoResponseDTO {
    
    private Long id;
    private String placa;
    private String nomeMotorista;
    private Double capacidade;
    private List<TipoResiduo> tipoResiduos;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
    
    public static CaminhaoResponseDTOBuilder builder() {
        return new CaminhaoResponseDTOBuilder();
    }
}