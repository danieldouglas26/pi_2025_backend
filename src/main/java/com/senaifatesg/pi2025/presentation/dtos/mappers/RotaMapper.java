package com.senaifatesg.pi2025.presentation.dtos.mappers;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

import com.senaifatesg.pi2025.core.models.Bairro;
import com.senaifatesg.pi2025.core.models.Caminhao;
import com.senaifatesg.pi2025.core.models.ParadaRota;
import com.senaifatesg.pi2025.core.models.Rota;
import com.senaifatesg.pi2025.core.repositories.BairroRepository;
import com.senaifatesg.pi2025.core.repositories.CaminhaoRepository;
import com.senaifatesg.pi2025.core.repositories.PontoColetaRepository;
import com.senaifatesg.pi2025.presentation.dtos.requests.RotaRequestDTO;
import com.senaifatesg.pi2025.presentation.dtos.responses.ParadaPontoColetaResponseDTO;
import com.senaifatesg.pi2025.presentation.dtos.responses.ParadaRotaResponseDTO;
import com.senaifatesg.pi2025.presentation.dtos.responses.RotaResponseDTO;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class RotaMapper {

	@Autowired
	protected CaminhaoRepository caminhaoRepository;

	@Autowired
	protected BairroRepository bairroRepository;

	@Autowired
	protected PontoColetaRepository pontoColetaRepository;

	@Autowired
	protected ParadaPontoColetaMapper paradaPontoColetaMapper;

	public static RotaMapper INSTANCE = Mappers.getMapper(RotaMapper.class);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "paradas", ignore = true)
	@Mapping(target = "nome", source = "dto.nome", defaultExpression = "java(generateRouteName(dto))")
	@Mapping(target = "caminhao", expression = "java(getCaminhao(dto.getCaminhaoId()))")
	@Mapping(target = "distanciaTotalKm", ignore = true)
	@Mapping(target = "tiposResiduos", source = "dto.tipoResiduo")
	public abstract Rota toEntity(RotaRequestDTO dto);

	@Mapping(target = "caminhaoId", source = "caminhao.id")
	@Mapping(target = "caminhaoPlaca", source = "caminhao.placa")
	@Mapping(target = "paradas", expression = "java(mapParadas(rota.getParadas()))")
	@Mapping(target = "pontosColeta", expression = "java(mapPontosColeta(rota))")
	public abstract RotaResponseDTO toResponseDTO(Rota rota);

	protected String generateRouteName(RotaRequestDTO dto) {
		Caminhao caminhao = getCaminhao(dto.getCaminhaoId());
		Bairro origem = getBairro(dto.getOrigemId());
		Bairro destino = getBairro(dto.getDestinoId());

		return String.format("Rota %s - %s para %s", caminhao.getPlaca(), origem.getNome(), destino.getNome());
	}

	protected Caminhao getCaminhao(Long caminhaoId) {
		return caminhaoRepository.findById(caminhaoId)
				.orElseThrow(() -> new IllegalArgumentException("Caminhão não encontrado"));
	}

	protected Bairro getBairro(Long bairroId) {
		return bairroRepository.findById(bairroId)
				.orElseThrow(() -> new IllegalArgumentException("Bairro não encontrado"));
	}

	protected List<ParadaRotaResponseDTO> mapParadas(List<ParadaRota> paradas) {
		if (paradas == null) {
			return Collections.emptyList();
		}

		return paradas.stream().sorted(Comparator.comparingInt(ParadaRota::getOrdem)).map(this::toParadaResponseDTO)
				.collect(Collectors.toList());
	}

	protected List<ParadaPontoColetaResponseDTO> mapPontosColeta(Rota rota) {
	    if (rota.getParadas() == null || rota.getParadas().isEmpty()) {
	        return Collections.emptyList();
	    }

	    return rota.getParadas().stream()
	            .flatMap(parada -> parada.getParadasPontoColeta().stream())
	            .map(paradaPontoColetaMapper::toResponseDTO)
	            .collect(Collectors.toList());
	}

	@Mapping(target = "bairroId", source = "bairro.id")
	@Mapping(target = "bairroNome", source = "bairro.nome")
	@Mapping(target = "pontosColeta", expression = "java(mapPontosColetaParada(parada))")
	protected abstract ParadaRotaResponseDTO toParadaResponseDTO(ParadaRota parada);

	protected List<ParadaPontoColetaResponseDTO> mapPontosColetaParada(ParadaRota parada) {
	    if (parada.getParadasPontoColeta() == null || parada.getParadasPontoColeta().isEmpty()) {
	        return Collections.emptyList();
	    }
	    
	    return parada.getParadasPontoColeta().stream()
	            .map(paradaPontoColetaMapper::toResponseDTO)
	            .collect(Collectors.toList());
	}
}