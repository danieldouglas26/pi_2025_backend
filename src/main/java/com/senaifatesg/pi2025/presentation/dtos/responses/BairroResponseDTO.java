package com.senaifatesg.pi2025.presentation.dtos.responses;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BairroResponseDTO {
    private Long id;
    private String nome;
}
