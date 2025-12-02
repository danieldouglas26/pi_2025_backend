package com.lixo.gerenciamento.model.mapper;

import org.springframework.stereotype.Component;

import com.lixo.gerenciamento.model.dto.CaminhaoDTO;
import com.lixo.gerenciamento.model.entity.Caminhao;

@Component
public class CaminhaoMapper {
    
    public CaminhaoDTO toDTO(Caminhao entity) {
        if (entity == null) {
            return null;
        }
        
        return CaminhaoDTO.builder()
                .id(entity.getId())
                .placa(entity.getPlaca())
                .motorista(entity.getMotorista())
                .capacidadeMaxima(entity.getCapacidadeMaxima())
                .tiposResiduos(entity.getTiposResiduos())
                .build();
    }
    
    public Caminhao toEntity(CaminhaoDTO dto) {
        if (dto == null) {
            return null;
        }
        
        return Caminhao.builder()
                .id(dto.getId())
                .placa(dto.getPlaca())
                .motorista(dto.getMotorista())
                .capacidadeMaxima(dto.getCapacidadeMaxima())
                .tiposResiduos(dto.getTiposResiduos())
                .build();
    }
}
