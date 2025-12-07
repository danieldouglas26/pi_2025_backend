package com.lixo.gerenciamento.model.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.lixo.gerenciamento.model.dto.request.ParadaPontoColetaRequestDTO;
import com.lixo.gerenciamento.model.dto.response.ParadaPontoColetaResponseDTO;
import com.lixo.gerenciamento.model.entity.ParadaPontoColeta;

@Mapper(componentModel = "spring")
public interface ParadaPontoColetaMapper {
    
    ParadaPontoColeta toEntity(ParadaPontoColetaRequestDTO dto);
    
    @Mapping(target = "pontoColetaId", source = "pontoColeta.id")
    @Mapping(target = "pontoColetaNome", source = "pontoColeta.nome")
    @Mapping(target = "tiposResiduo", source = "pontoColeta.tiposDeResiduo")
    ParadaPontoColetaResponseDTO toResponseDTO(ParadaPontoColeta entity);
    
    List<ParadaPontoColetaResponseDTO> toResponseDTOList(List<ParadaPontoColeta> entities);
}
