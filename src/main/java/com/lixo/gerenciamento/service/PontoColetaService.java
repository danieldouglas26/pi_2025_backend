package com.lixo.gerenciamento.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lixo.gerenciamento.exception.BusinessException;
import com.lixo.gerenciamento.exception.ResourceNotFoundException;
import com.lixo.gerenciamento.model.dto.request.PontoColetaRequestDTO;
import com.lixo.gerenciamento.model.entity.PontoColeta;
import com.lixo.gerenciamento.model.mapper.PontoColetaMapper;
import com.lixo.gerenciamento.repository.PontoColetaRepository;

@Service
public class PontoColetaService {

    private final PontoColetaRepository collectionPointRepository;
    private final PontoColetaMapper mapper;

    

    @Autowired
    public PontoColetaService(PontoColetaRepository collectionPointRepository, PontoColetaMapper mapper) {
		super();
		this.collectionPointRepository = collectionPointRepository;
		this.mapper = mapper;
	}

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
