package com.senaifatesg.pi2025.presentation.dtos.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.senaifatesg.pi2025.core.models.Bairro;
import com.senaifatesg.pi2025.presentation.dtos.requests.BairroRequestDTO;
import com.senaifatesg.pi2025.presentation.dtos.responses.BairroResponseDTO;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BairroMapper {
	BairroMapper INSTANCE = Mappers.getMapper(BairroMapper.class);

	Bairro toEntity(BairroRequestDTO dto);

	BairroResponseDTO toResponseDTO(Bairro entity);

	void updateFromDTO(BairroRequestDTO dto, @MappingTarget Bairro entity);
}