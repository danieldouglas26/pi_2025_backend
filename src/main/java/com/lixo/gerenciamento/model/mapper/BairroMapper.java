//Padrão singleton

package com.lixo.gerenciamento.model.mapper;
import com.lixo.gerenciamento.model.dto.request.BairroRequestDTO;
import com.lixo.gerenciamento.model.dto.response.BairroResponseDTO;
import com.lixo.gerenciamento.model.entity.Bairro;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BairroMapper {
	BairroMapper INSTANCE = Mappers.getMapper(BairroMapper.class);
	Bairro toEntity(BairroRequestDTO dto);
	BairroResponseDTO toResponseDTO(Bairro entity);
	void updateFromDTO(BairroRequestDTO dto, @MappingTarget Bairro entity);
}