package com.lixo.gerenciamento.model.mapper;

import org.springframework.stereotype.Component;

import com.lixo.gerenciamento.model.dto.PontoColetaDTO;
import com.lixo.gerenciamento.model.entity.PontoColeta;

@Component
public class PontoColetaMapper {
    
    public PontoColetaDTO toDTO(PontoColeta entity) {
        if (entity == null) {
            return null;
        }
        
        return PontoColetaDTO.builder()
                .id(entity.getId())
                .nome(entity.getNome())
                .responsavel(entity.getResponsavel())
                .contato(entity.getContato())
                .endereco(entity.getEndereco())
                .bairro(entity.getBairro())
                .tiposResiduos(entity.getTiposResiduos())
                .horarioFuncionamento(entity.getHorarioFuncionamento())
                .capacidadeDiaria(entity.getCapacidadeDiaria())
                .build();
    }
    
    public PontoColeta toEntity(PontoColetaDTO dto) {
        if (dto == null) {
            return null;
        }
        
        return PontoColeta.builder()
                .id(dto.getId())
                .nome(dto.getNome())
                .responsavel(dto.getResponsavel())
                .contato(dto.getContato())
                .endereco(dto.getEndereco())
                .bairro(dto.getBairro())
                .tiposResiduos(dto.getTiposResiduos())
                .horarioFuncionamento(dto.getHorarioFuncionamento())
                .capacidadeDiaria(dto.getCapacidadeDiaria())
                .build();
    }
}
