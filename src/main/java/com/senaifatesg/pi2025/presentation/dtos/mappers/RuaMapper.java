package com.senaifatesg.pi2025.presentation.dtos.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

import com.senaifatesg.pi2025.core.models.Bairro;
import com.senaifatesg.pi2025.core.models.Rua;
import com.senaifatesg.pi2025.core.repositories.BairroRepository;
import com.senaifatesg.pi2025.presentation.dtos.requests.RuaRequestDTO;
import com.senaifatesg.pi2025.presentation.dtos.responses.RuaResponseDTO;

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
        return bairroRepository.findById(bairroid)
                .orElseThrow(() -> new IllegalArgumentException("PontoColeta não encontrado com ID: " + bairroid));
    }

    @Mapping(target = "origemId", source = "origem.id")
    @Mapping(target = "destinoId", source = "destino.id")
    public abstract RuaRequestDTO toRequestDTO(Rua entity);
}
