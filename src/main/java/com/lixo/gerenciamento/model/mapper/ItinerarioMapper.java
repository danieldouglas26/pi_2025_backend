package com.lixo.gerenciamento.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.lixo.gerenciamento.model.dto.request.ItinerarioRequestDTO;
import com.lixo.gerenciamento.model.dto.response.ItinerarioResponseDTO;
import com.lixo.gerenciamento.model.entity.Itinerario;

@Mapper(componentModel = "spring", uses = { ParadaRotaMapper.class,
		PontoColetaMapper.class }, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ItinerarioMapper {

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "rota", expression = "java(mapRota(dto.getRotaId()))")
	@Mapping(target = "caminhao", expression = "java(mapCaminhao(dto.getCaminhaoId()))")
	Itinerario toEntity(ItinerarioRequestDTO dto);

	@Mapping(target = "rotaId", source = "rota.id")
	@Mapping(target = "rotaNome", source = "rota.nome")
	@Mapping(target = "caminhaoId", source = "caminhao.id")
	@Mapping(target = "caminhaoPlaca", source = "caminhao.placa")
	@Mapping(target = "motorista", source = "caminhao.nomeMotorista")
	@Mapping(target = "distanciaTotal", source = "rota.distanciaTotalKm")
	@Mapping(target = "tipoResiduo", source = "rota.tiposResiduos")
	@Mapping(target = "paradas", source = "rota.paradas")
	ItinerarioResponseDTO toResponseDTO(Itinerario itinerario);






}
