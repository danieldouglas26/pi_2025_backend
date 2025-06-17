package com.senaifatesg.pi2025.presentation.dtos.requests;

import java.time.LocalDate;

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
public class ItinerarioRequestDTO {

    @NotNull(message = "ID da rota é obrigatório")
    private Long rotaId;

    @NotNull(message = "ID do caminhão é obrigatório")
    private Long caminhaoId;

    @NotNull(message = "Data do itinerário é obrigatória")
    private LocalDate data;
}