// src/main/java/com/senaifatesg/pi2025/presentation/dtos/responses/PontoColetaResponseDTO.java
package com.senaifatesg.pi2025.presentation.dtos.responses;

import java.time.LocalDateTime;
import java.util.Set; // Certifique-se de usar java.util.Set

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PontoColetaResponseDTO {
    private Long id;
    private String nome;
    private String endereco;
    private String responsavel;
    private String email;
    private String telefone;
    private Set<String> tiposResiduo; 
    private boolean coletado; 
    private String horarioFuncionamento;

    private Long idBairro;

}