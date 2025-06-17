package com.senaifatesg.pi2025.presentation.dtos.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.senaifatesg.pi2025.core.repositories.ParadaPontoColeta;
import com.senaifatesg.pi2025.presentation.dtos.requests.ParadaPontoColetaRequestDTO;
import com.senaifatesg.pi2025.presentation.dtos.responses.ParadaPontoColetaResponseDTO;

@Mapper(componentModel = "spring")
public interface ParadaPontoColetaMapper {
    
    ParadaPontoColeta toEntity(ParadaPontoColetaRequestDTO dto);
    
    @Mapping(target = "pontoColetaId", source = "pontoColeta.id")
    @Mapping(target = "pontoColetaNome", source = "pontoColeta.nome")
    @Mapping(target = "tiposResiduo", source = "pontoColeta.tiposDeResiduo")
    ParadaPontoColetaResponseDTO toResponseDTO(ParadaPontoColeta entity);
    
    List<ParadaPontoColetaResponseDTO> toResponseDTOList(List<ParadaPontoColeta> entities);
}