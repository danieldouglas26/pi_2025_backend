package com.lixo.gerenciamento.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.lixo.gerenciamento.model.dto.request.CaminhaoRequestDTO;
import com.lixo.gerenciamento.model.dto.response.CaminhaoResponseDTO;
import com.lixo.gerenciamento.model.entity.Caminhao;

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
