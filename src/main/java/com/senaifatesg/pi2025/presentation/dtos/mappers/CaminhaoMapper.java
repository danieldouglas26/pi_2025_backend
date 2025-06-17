package com.senaifatesg.pi2025.presentation.dtos.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.senaifatesg.pi2025.core.models.Caminhao;
import com.senaifatesg.pi2025.presentation.dtos.requests.CaminhaoRequestDTO;
import com.senaifatesg.pi2025.presentation.dtos.responses.CaminhaoResponseDTO;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CaminhaoMapper {
    
    @Mapping(target = "id", ignore = true)
    Caminhao toEntity(CaminhaoRequestDTO dto);
    
    CaminhaoResponseDTO toResponseDTO(Caminhao entity);
    
    CaminhaoRequestDTO toRequestDTO(Caminhao entity);

@Mapping(target = "id", ignore = true)
    @Mapping(target = "chaveModular", ignore = true) 
    void updateFromDto(CaminhaoRequestDTO dto, @MappingTarget Caminhao entity);
}