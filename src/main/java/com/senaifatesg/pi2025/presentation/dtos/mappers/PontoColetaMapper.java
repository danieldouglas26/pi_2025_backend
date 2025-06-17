// src/main/java/com/senaifatesg/pi2025/presentation/dtos/mappers/PontoColetaMapper.java
package com.senaifatesg.pi2025.presentation.dtos.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;

import com.senaifatesg.pi2025.core.models.Bairro;
import com.senaifatesg.pi2025.core.models.PontoColeta;
import com.senaifatesg.pi2025.core.services.BairroService;
import com.senaifatesg.pi2025.common.enums.TipoResiduo;
import com.senaifatesg.pi2025.presentation.dtos.requests.PontoColetaRequestDTO;
import com.senaifatesg.pi2025.presentation.dtos.responses.PontoColetaResponseDTO;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE
)
public abstract class PontoColetaMapper {

    @Autowired
    protected BairroService bairroService;

    @Named("mapStringsToResidueTypes")
    protected List<TipoResiduo> mapStringsToResidueTypes(List<String> types) {
        if (types == null) {
            return null;
        }
        return types.stream()
                    .map(TipoResiduo::valueOf) 
                    .collect(Collectors.toList());
    }

    @Named("mapResidueTypesToSetOfStrings")
    protected Set<String> mapResidueTypesToSetOfStrings(List<TipoResiduo> types) {
        if (types == null) {
            return null;
        }
        return types.stream()
                    .map(TipoResiduo::name) 
                    .collect(Collectors.toSet());
    }

    @Named("mapResidueTypesToStringsList")
    protected List<String> mapResidueTypesToStringsList(List<TipoResiduo> types) {
        if (types == null) {
            return null;
        }
        return types.stream()
                    .map(TipoResiduo::name)
                    .collect(Collectors.toList());
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "nomeResponsavel", source = "nomeResponsavel")
    @Mapping(target = "horarioFuncionamento", source = "horarioFuncionamento")
    @Mapping(target = "tiposResiduoAceitosLegacy", ignore = true)
    @Mapping(target = "bairro", expression = "java(bairroService.getBairroById(dto.getIdBairro()))")
    @Mapping(target = "tiposDeResiduo", source = "tiposDeResiduo", qualifiedByName = "mapStringsToResidueTypes")

    public abstract PontoColeta toEntity(PontoColetaRequestDTO dto);

    @Mapping(target = "idBairro", source = "bairro.id")
    @Mapping(target = "tiposDeResiduo", source = "tiposDeResiduo", qualifiedByName = "mapResidueTypesToStringsList") // <--- CORRIGIDO AQUI
    public abstract PontoColetaRequestDTO toRequestDTO(PontoColeta entity);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "nomeResponsavel", source = "dto.nomeResponsavel")
    @Mapping(target = "horarioFuncionamento", source = "dto.horarioFuncionamento")
    @Mapping(target = "tiposResiduoAceitosLegacy", ignore = true)
    @Mapping(target = "bairro", expression = "java(bairroService.getBairroById(dto.getIdBairro()))")
    @Mapping(target = "tiposDeResiduo", source = "dto.tiposDeResiduo", qualifiedByName = "mapStringsToResidueTypes")
 
    public abstract void updateFromDTO(PontoColetaRequestDTO dto, @MappingTarget PontoColeta entity);

    @Mapping(target = "id", source = "pontoColeta.id")
    @Mapping(target = "nome", source = "pontoColeta.nome")
    @Mapping(target = "endereco", source = "pontoColeta.endereco")
    @Mapping(target = "responsavel", source = "pontoColeta.nomeResponsavel")
    @Mapping(target = "email", source = "pontoColeta.email")
    @Mapping(target = "telefone", source = "pontoColeta.telefone")
    @Mapping(target = "tiposResiduo", source = "pontoColeta.tiposDeResiduo", qualifiedByName = "mapResidueTypesToSetOfStrings") // <--- CORRIGIDO AQUI
    @Mapping(target = "horarioFuncionamento", source = "pontoColeta.horarioFuncionamento")
    @Mapping(target = "idBairro", source = "pontoColeta.bairro.id")
    @Mapping(target = "coletado", constant = "false")
    public abstract PontoColetaResponseDTO toResponseDTO(PontoColeta pontoColeta);
}