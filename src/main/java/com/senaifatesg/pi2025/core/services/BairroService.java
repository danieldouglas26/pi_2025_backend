package com.senaifatesg.pi2025.core.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.senaifatesg.pi2025.common.BusinessException;
import com.senaifatesg.pi2025.common.ResourceNotFoundException;
import com.senaifatesg.pi2025.core.models.Bairro;
import com.senaifatesg.pi2025.core.repositories.BairroRepository;
import com.senaifatesg.pi2025.core.repositories.RuaRepository;
import com.senaifatesg.pi2025.presentation.dtos.mappers.BairroMapper;
import com.senaifatesg.pi2025.presentation.dtos.requests.BairroRequestDTO;
import com.senaifatesg.pi2025.presentation.dtos.responses.BairroResponseDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class BairroService {

    private final BairroRepository bairroRepository;
    private final BairroMapper bairroMapper;
    private final RuaRepository ruaRepository;

    public Page<BairroResponseDTO> findAll(Pageable pageable) {
        return bairroRepository.findAll(pageable)
                .map(bairroMapper::toResponseDTO);
    }

    public BairroResponseDTO findById(Long id) {
        Bairro bairro = bairroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bairro não encontrado com ID: " + id));
        return bairroMapper.toResponseDTO(bairro);
    }

    public BairroResponseDTO findByNome(String nome) {
        Bairro bairro = bairroRepository.findByNome(nome)
                .orElseThrow(() -> new ResourceNotFoundException("Bairro não encontrado com nome: " + nome));
        return bairroMapper.toResponseDTO(bairro);
    }

    @Transactional
    public BairroResponseDTO create(BairroRequestDTO bairroRequestDTO) {
        if (bairroRepository.findByNome(bairroRequestDTO.getNome()).isPresent()) {
            throw new BusinessException("Já existe um bairro cadastrado com este nome");
        }

        Bairro bairro = bairroMapper.toEntity(bairroRequestDTO);
        Bairro savedBairro = bairroRepository.save(bairro);
        return bairroMapper.toResponseDTO(savedBairro);
    }

    @Transactional
    public BairroResponseDTO update(Long id, BairroRequestDTO bairroRequestDTO) {
        Bairro existingBairro = bairroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bairro não encontrado com ID: " + id));

        bairroRepository.findByNome(bairroRequestDTO.getNome())
                .ifPresent(b -> {
                    if (!b.getId().equals(id)) {
                        throw new BusinessException("Já existe outro bairro cadastrado com este nome");
                    }
                });

        bairroMapper.updateFromDTO(bairroRequestDTO, existingBairro);
        Bairro updatedBairro = bairroRepository.save(existingBairro);
        return bairroMapper.toResponseDTO(updatedBairro);
    }

public Bairro getBairroById(Long id) { // <--- ESTE MÉTODO PRECISA EXISTIR NO SEU BACKEND BAIRROSERVICE
        return bairroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bairro não encontrado com ID: " + id));
    }

    @Transactional
    public void delete(Long id) {
        if (!bairroRepository.existsById(id)) {
            throw new ResourceNotFoundException("Bairro não encontrado com ID: " + id);
        }
        
        if (ruaRepository.existsByOrigemIdOrDestinoId(id, id)) {
            throw new BusinessException("Não é possível excluir o bairro pois ele está vinculado a ruas");
        }
        
        
        bairroRepository.deleteById(id);
    }
}
