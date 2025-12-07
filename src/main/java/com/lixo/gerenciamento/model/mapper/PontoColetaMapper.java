package com.lixo.gerenciamento.model.mapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;

import com.lixo.gerenciamento.model.dto.request.PontoColetaRequestDTO;
import com.lixo.gerenciamento.model.dto.response.PontoColetaResponseDTO;
import com.lixo.gerenciamento.model.entity.PontoColeta;
import com.lixo.gerenciamento.model.enums.TipoResiduo;
import com.lixo.gerenciamento.service.BairroService;

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
