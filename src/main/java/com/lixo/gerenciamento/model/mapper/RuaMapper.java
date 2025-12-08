
package com.lixo.gerenciamento.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

import com.lixo.gerenciamento.model.dto.request.RuaRequestDTO;
import com.lixo.gerenciamento.model.dto.response.RuaResponseDTO;
import com.lixo.gerenciamento.model.entity.Bairro;
import com.lixo.gerenciamento.model.entity.Rua;
import com.lixo.gerenciamento.repository.BairroRepository;

@Mapper(componentModel = "spring",
nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class RuaMapper {

@Autowired
protected BairroRepository bairroRepository;

public static RuaMapper INSTANCE = Mappers.getMapper(RuaMapper.class);

@Mapping(target = "id", ignore = true)
@Mapping(target = "origem", expression = "java(mapBairro(dto.getOrigemId()))")
@Mapping(target = "destino", expression = "java(mapBairro(dto.getDestinoId()))")
public abstract Rua toEntity(RuaRequestDTO dto);

@Mapping(target = "origemId", source = "origem.id")
@Mapping(target = "origemNome", source = "origem.nome")
@Mapping(target = "destinoId", source = "destino.id")
@Mapping(target = "destinoNome", source = "destino.nome")
public abstract RuaResponseDTO toResponseDTO(Rua entity);

@Mapping(target = "id", ignore = true)
@Mapping(target = "origem", expression = "java(mapBairro(dto.getOrigemId()))")
@Mapping(target = "destino", expression = "java(mapBairro(dto.getDestinoId()))")
public abstract void updateFromDTO(RuaRequestDTO dto, @MappingTarget Rua entity);

protected Bairro mapBairro(Long bairroid) {
if (bairroid == null) {
    return null;
}
    
    return bairroRepository.findById(bairroid).orElse(null); 
}

@Mapping(target = "origemId", source = "origem.id")
@Mapping(target = "destinoId", source = "destino.id")
public abstract RuaRequestDTO toRequestDTO(Rua entity);
}