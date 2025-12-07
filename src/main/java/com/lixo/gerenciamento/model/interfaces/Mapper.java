package com.lixo.gerenciamento.model.interfaces;

public interface Mapper<E, RQ, RP> {
    

    E toEntity(RQ request);

    RP toResponseDTO(E entity);
    
    E updateFromDTO(E entity, RQ request);
    
}