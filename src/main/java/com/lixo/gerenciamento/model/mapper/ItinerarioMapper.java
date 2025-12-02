package com.lixo.gerenciamento.model.mapper;

import org.springframework.stereotype.Component;

import com.lixo.gerenciamento.model.dto.ItinerarioDTO;
import com.lixo.gerenciamento.model.entity.Itinerario;

@Component
public class ItinerarioMapper {
    
    public ItinerarioDTO toDTO(Itinerario entity) {
        if (entity == null) {
            return null;
        }
        
        return ItinerarioDTO.builder()
                .id(entity.getId())
                .rota(entity.getRota())
                .data(entity.getData())
                .horaInicio(entity.getHoraInicio())
                .horaFim(entity.getHoraFim())
                .status(entity.getStatus() != null ? entity.getStatus().name() : null)
                .distanciaPercorrida(entity.getDistanciaPercorrida())
                .tempoRealizado(entity.getTempoRealizado())
                .observacoes(entity.getObservacoes())
                .build();
    }
    
    public Itinerario toEntity(ItinerarioDTO dto) {
        if (dto == null) {
            return null;
        }
        
        return Itinerario.builder()
                .id(dto.getId())
                .rota(dto.getRota())
                .data(dto.getData())
                .horaInicio(dto.getHoraInicio())
                .horaFim(dto.getHoraFim())
                .status(dto.getStatus() != null ? Itinerario.StatusItinerario.valueOf(dto.getStatus()) : null)
                .distanciaPercorrida(dto.getDistanciaPercorrida())
                .tempoRealizado(dto.getTempoRealizado())
                .observacoes(dto.getObservacoes())
                .build();
    }
}
