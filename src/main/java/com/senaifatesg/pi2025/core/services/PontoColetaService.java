package com.senaifatesg.pi2025.core.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.senaifatesg.pi2025.common.BusinessException;
import com.senaifatesg.pi2025.common.ResourceNotFoundException;
import com.senaifatesg.pi2025.core.models.PontoColeta;
import com.senaifatesg.pi2025.core.repositories.PontoColetaRepository;
import com.senaifatesg.pi2025.presentation.dtos.mappers.PontoColetaMapper;
import com.senaifatesg.pi2025.presentation.dtos.requests.PontoColetaRequestDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PontoColetaService {

    private final PontoColetaRepository collectionPointRepository;
    private final PontoColetaMapper mapper;


    public Page<PontoColeta> getAllCollectionPoints(Pageable pageable) {
        return collectionPointRepository.findAll(pageable);
    }

    public PontoColeta getCollectionPointById(Long id) {
        return collectionPointRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Collection point not found with ID: " + id));
    }

    @Transactional
    public PontoColeta createCollectionPoint(PontoColetaRequestDTO requestDTO) {
        if (collectionPointRepository.findByNome(requestDTO.getNome()).isPresent()) {
            throw new BusinessException("Collection point with name '" + requestDTO.getNome() + "' already exists.");
        }

        PontoColeta newPoint = mapper.toEntity(requestDTO); 
        return collectionPointRepository.save(newPoint);
    }

      @Transactional
    public PontoColeta updateCollectionPoint(Long id, PontoColetaRequestDTO requestDTO) {
        PontoColeta existingPoint = getCollectionPointById(id);

        Optional<PontoColeta> pointWithSameName = collectionPointRepository.findByNome(requestDTO.getNome());
        if (pointWithSameName.isPresent() && !pointWithSameName.get().getId().equals(id)) {
            throw new BusinessException("Collection point with name '" + requestDTO.getNome() + "' already exists.");
        }

        mapper.updateFromDTO(requestDTO, existingPoint); 
        return collectionPointRepository.save(existingPoint);
    }

    @Transactional
    public void deleteCollectionPoint(Long id) {
        PontoColeta point = getCollectionPointById(id);
        collectionPointRepository.delete(point);
    }
    
    public Page<PontoColeta> findByTipoResiduo(String tipoResiduo, Pageable pageable) {
        return collectionPointRepository.findByTiposDeResiduoContaining(tipoResiduo, pageable);
    }

    public Page<PontoColeta> findByBairro(String bairro, Pageable pageable) {
        return collectionPointRepository.findByEnderecoContainingIgnoreCase(bairro, pageable);
    }
}