package com.lixo.gerenciamento.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.lixo.gerenciamento.model.dto.response.ParadaItinerarioResponseDTO;
import com.lixo.gerenciamento.model.entity.ParadaRota;

@Mapper(componentModel = "spring")
public interface ParadaRotaMapper {
    
    @Mapping(target = "bairroId", source = "bairro.id")
    @Mapping(target = "bairroNome", source = "bairro.nome")
    @Mapping(target = "pontosColeta", source = "paradasPontoColeta")
    ParadaItinerarioResponseDTO toResponseDTO(ParadaRota parada);
}
