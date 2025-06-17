package com.senaifatesg.pi2025.core.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.senaifatesg.pi2025.common.BusinessException;
import com.senaifatesg.pi2025.common.ResourceNotFoundException;
import com.senaifatesg.pi2025.core.models.Bairro;
import com.senaifatesg.pi2025.core.models.Rua;
import com.senaifatesg.pi2025.core.repositories.BairroRepository;
import com.senaifatesg.pi2025.core.repositories.RuaRepository;
import com.senaifatesg.pi2025.presentation.dtos.mappers.RuaMapper;
import com.senaifatesg.pi2025.presentation.dtos.requests.RuaRequestDTO;
import com.senaifatesg.pi2025.presentation.dtos.responses.RuaResponseDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class RuaService {

    private final RuaRepository ruaRepository;
    private final BairroRepository bairroRepository;
    private final RuaMapper ruaMapper;

    public Page<RuaResponseDTO> findAll(Pageable pageable) {
        return ruaRepository.findAll(pageable)
                .map(ruaMapper::toResponseDTO);
    }

    public RuaResponseDTO findById(Long id) {
        Rua rua = ruaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rua não encontrada com ID: " + id));
        return ruaMapper.toResponseDTO(rua);
    }

    @Transactional
    public RuaResponseDTO create(RuaRequestDTO ruaRequestDTO) {
        Bairro origem = bairroRepository.findById(ruaRequestDTO.getOrigemId())
                .orElseThrow(() -> new ResourceNotFoundException("Ponto de coleta de origem não encontrado"));
        
        Bairro destino = bairroRepository.findById(ruaRequestDTO.getDestinoId())
                .orElseThrow(() -> new ResourceNotFoundException("Ponto de coleta de destino não encontrado"));

        if (origem.getId().equals(destino.getId())) {
            throw new BusinessException("A origem e o destino não podem ser o mesmo ponto de coleta");
        }

        if (ruaRepository.existsByOrigemAndDestino(origem, destino)  ||  ruaRepository.existsByOrigemAndDestino(destino,origem)) {
            throw new BusinessException("Já existe uma rua cadastrada com estes pontos de origem e destino");
        }

        Rua rua = ruaMapper.toEntity(ruaRequestDTO);
        rua.setOrigem(origem);
        rua.setDestino(destino);
        
        Rua savedRua = ruaRepository.save(rua);
        return ruaMapper.toResponseDTO(savedRua);
    }

    @Transactional
    public RuaResponseDTO update(Long id, RuaRequestDTO ruaRequestDTO) {
        Rua existingRua = ruaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rua não encontrada com ID: " + id));

        Bairro origem = bairroRepository.findById(ruaRequestDTO.getOrigemId())
                .orElseThrow(() -> new ResourceNotFoundException("Ponto de coleta de origem não encontrado"));
        
        Bairro destino = bairroRepository.findById(ruaRequestDTO.getDestinoId())
                .orElseThrow(() -> new ResourceNotFoundException("Ponto de coleta de destino não encontrado"));

        if (origem.getId().equals(destino.getId())) {
            throw new BusinessException("A origem e o destino não podem ser o mesmo ponto de coleta");
        }

        if (ruaRepository.existsByOrigemAndDestinoAndIdNot(origem, destino, id)) {
            throw new BusinessException("Já existe outra rua cadastrada com estes pontos de origem e destino");
        }

        existingRua.setOrigem(origem);
        existingRua.setDestino(destino);
        existingRua.setDistancia(ruaRequestDTO.getDistancia());
        
        Rua updatedRua = ruaRepository.save(existingRua);
        return ruaMapper.toResponseDTO(updatedRua);
    }

    @Transactional
    public void delete(Long id) {
        if (!ruaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Rua não encontrada com ID: " + id);
        }
        ruaRepository.deleteById(id);
    }


    public List<RuaResponseDTO> findRuasByOrigem(Long pontoColetaId) {
        Bairro pontoColeta = bairroRepository.findById(pontoColetaId)
                .orElseThrow(() -> new ResourceNotFoundException("Ponto de coleta não encontrado"));
        
        return ruaRepository.findByOrigem(pontoColeta)
                .stream()
                .map(ruaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<RuaResponseDTO> findRuasByDestino(Long pontoColetaId) {
        Bairro pontoColeta = bairroRepository.findById(pontoColetaId)
                .orElseThrow(() -> new ResourceNotFoundException("Ponto de coleta não encontrado"));
        
        return ruaRepository.findByDestino(pontoColeta)
                .stream()
                .map(ruaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}
