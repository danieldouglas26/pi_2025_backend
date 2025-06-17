package com.senaifatesg.pi2025.core.services;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.senaifatesg.pi2025.common.BusinessException;
import com.senaifatesg.pi2025.common.ResourceNotFoundException;
import com.senaifatesg.pi2025.core.models.Caminhao;
import com.senaifatesg.pi2025.core.models.Itinerario;
import com.senaifatesg.pi2025.core.models.Rota;
import com.senaifatesg.pi2025.core.repositories.CaminhaoRepository;
import com.senaifatesg.pi2025.core.repositories.ItinerarioRepository;
import com.senaifatesg.pi2025.core.repositories.RotaRepository;
import com.senaifatesg.pi2025.presentation.dtos.mappers.ItinerarioMapper;
import com.senaifatesg.pi2025.presentation.dtos.requests.ItinerarioRequestDTO;
import com.senaifatesg.pi2025.presentation.dtos.responses.ItinerarioResponseDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ItinerarioService {

    private final ItinerarioRepository itinerarioRepository;
    private final RotaRepository rotaRepository;
    private final CaminhaoRepository caminhaoRepository;
    private final ItinerarioMapper itinerarioMapper;

    @Transactional
    public ItinerarioResponseDTO criarItinerario(ItinerarioRequestDTO requestDTO) {
        if (itinerarioRepository.existsByCaminhaoIdAndData(requestDTO.getCaminhaoId(), requestDTO.getData())) {
            throw new BusinessException("Caminhão já possui itinerário nesta data");
        }

        Rota rota = rotaRepository.findById(requestDTO.getRotaId())
                .orElseThrow(() -> new ResourceNotFoundException("Rota não encontrada"));
        Caminhao caminhao = caminhaoRepository.findById(requestDTO.getCaminhaoId())
                .orElseThrow(() -> new ResourceNotFoundException("Caminhão não encontrado"));

        if (!caminhao.getTipoResiduos().contains(rota.getTiposResiduos())) {
            throw new BusinessException("O caminhão não está habilitado para o tipo de resíduo da rota");
        }
        
        if (!caminhao.equals(rota.getCaminhao())) {
        	throw new BusinessException("O caminhão informado é diferente do caminhão da rota informada");
        }

        Itinerario itinerario = itinerarioMapper.toEntity(requestDTO);
        Itinerario savedItinerario = itinerarioRepository.save(itinerario);
        
        return itinerarioMapper.toResponseDTO(savedItinerario);
    }

    @Transactional(readOnly = true)
    public ItinerarioResponseDTO buscarItinerarioPorId(Long id) {
        Itinerario itinerario = itinerarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Itinerário não encontrado"));
        return itinerarioMapper.toResponseDTO(itinerario);
    }

    @Transactional(readOnly = true)
    public List<ItinerarioResponseDTO> listarItinerariosPorCaminhao(Long caminhaoId) {
        return itinerarioRepository.findByCaminhaoId(caminhaoId).stream()
                .map(itinerarioMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ItinerarioResponseDTO> listarItinerariosPorData(LocalDate data) {
        return itinerarioRepository.findByData(data).stream()
                .map(itinerarioMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ItinerarioResponseDTO> listarItinerariosPorPeriodo(LocalDate inicio, LocalDate fim) {
        return itinerarioRepository.findByPeriodo(inicio, fim).stream()
                .map(itinerarioMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public ItinerarioResponseDTO atualizarItinerario(Long id, ItinerarioRequestDTO requestDTO) {
        Itinerario itinerario = itinerarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Itinerário não encontrado"));

        if (!itinerario.getData().equals(requestDTO.getData()) && 
            itinerarioRepository.existsByCaminhaoIdAndData(itinerario.getCaminhao().getId(), requestDTO.getData())) {
            throw new BusinessException("Caminhão já possui itinerário na nova data");
        }

        if (requestDTO.getRotaId() != null) {
            Rota novaRota = rotaRepository.findById(requestDTO.getRotaId())
                    .orElseThrow(() -> new ResourceNotFoundException("Rota não encontrada"));
            
            if (!itinerario.getCaminhao().getTipoResiduos().contains(novaRota.getTiposResiduos())) {
                throw new BusinessException("O caminhão não está habilitado para o tipo de resíduo da nova rota");
            }
            
            itinerario.setRota(novaRota);
        }

        if (requestDTO.getData() != null) {
            itinerario.setData(requestDTO.getData());
        }

        return itinerarioMapper.toResponseDTO(itinerarioRepository.save(itinerario));
    }

    @Transactional
    public void removerItinerario(Long id) {
        if (!itinerarioRepository.existsById(id)) {
            throw new ResourceNotFoundException("Itinerário não encontrado");
        }
        itinerarioRepository.deleteById(id);
    }
    
}