package com.senaifatesg.pi2025.presentation.dtos.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.senaifatesg.pi2025.core.models.ParadaRota;
import com.senaifatesg.pi2025.presentation.dtos.responses.ParadaItinerarioResponseDTO;

@Mapper(componentModel = "spring")
public interface ParadaRotaMapper {
    
    @Mapping(target = "bairroId", source = "bairro.id")
    @Mapping(target = "bairroNome", source = "bairro.nome")
    @Mapping(target = "pontosColeta", source = "paradasPontoColeta")
    ParadaItinerarioResponseDTO toResponseDTO(ParadaRota parada);
}