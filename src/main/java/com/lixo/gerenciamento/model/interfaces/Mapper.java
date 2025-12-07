package com.lixo.gerenciamento.model.interfaces;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface Mapper<ENTITY, REQUEST, RESPONSE> {
    

    ENTITY toEntity(REQUEST request);

    RESPONSE toResponseDTO(ENTITY entity);
    

    ENTITY updateFromDTO(ENTITY entity, REQUEST request);
    

    default List<RESPONSE> toResponseDTOList(List<ENTITY> entities) {
        if (entities == null || entities.isEmpty()) {
            return Collections.emptyList();
        }
        return entities.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }
    

    default List<ENTITY> toEntityList(List<REQUEST> requests) {
        if (requests == null || requests.isEmpty()) {
            return Collections.emptyList();
        }
        return requests.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
    
    default List<ENTITY> updateFromDTOList(List<ENTITY> entities, List<REQUEST> requests) {
        if (entities == null || requests == null) {
            throw new IllegalArgumentException("Listas não podem ser nulas");
        }
        
        if (entities.size() != requests.size()) {
            throw new IllegalArgumentException(
                String.format("Tamanhos das listas não correspondem: %d != %d", 
                    entities.size(), requests.size())
            );
        }
        
        List<ENTITY> updatedEntities = new ArrayList<>();
        for (int i = 0; i < entities.size(); i++) {
            updatedEntities.add(updateFromDTO(entities.get(i), requests.get(i)));
        }
        return updatedEntities;
    }
    

    default Optional<RESPONSE> toResponseDTOOptional(Optional<ENTITY> entity) {
        return entity.map(this::toResponseDTO);
        }

    default Optional<ENTITY> toEntityOptional(Optional<REQUEST> request) {
        return request.map(this::toEntity);
    }
}