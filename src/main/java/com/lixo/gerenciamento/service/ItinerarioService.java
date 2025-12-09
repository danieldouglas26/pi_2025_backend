package com.lixo.gerenciamento.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lixo.gerenciamento.exception.BusinessException;
import com.lixo.gerenciamento.exception.ResourceNotFoundException;
import com.lixo.gerenciamento.model.dto.request.ItinerarioRequestDTO;
import com.lixo.gerenciamento.model.dto.response.ItinerarioResponseDTO;
import com.lixo.gerenciamento.model.entity.Caminhao;
import com.lixo.gerenciamento.model.entity.Itinerario;
import com.lixo.gerenciamento.model.entity.Rota;
import com.lixo.gerenciamento.model.mapper.ItinerarioMapper;
import com.lixo.gerenciamento.repository.CaminhaoRepository;
import com.lixo.gerenciamento.repository.ItinerarioRepository;
import com.lixo.gerenciamento.repository.RotaRepository;

@Service
@Transactional
public class ItinerarioService {

    private final ItinerarioRepository itinerarioRepository;
    private final RotaRepository rotaRepository;
    private final CaminhaoRepository caminhaoRepository;
    private final ItinerarioMapper itinerarioMapper;

    @Autowired
    public ItinerarioService(ItinerarioRepository itinerarioRepository, RotaRepository rotaRepository,
			CaminhaoRepository caminhaoRepository, ItinerarioMapper itinerarioMapper) {
		super();
		this.itinerarioRepository = itinerarioRepository;
		this.rotaRepository = rotaRepository;
		this.caminhaoRepository = caminhaoRepository;
		this.itinerarioMapper = itinerarioMapper;
	}

	@Transactional
    public ItinerarioResponseDTO criarItinerario(ItinerarioRequestDTO requestDTO) {
        if (itinerarioRepository.existsByCaminhaoIdAndData(requestDTO.getCaminhaoId(), requestDTO.getData())) {
            throw new BusinessException("Caminhão já possui itinerário nesta data");
        }

        Rota rota = rotaRepository.findById(requestDTO.getRotaId()).get();
        Caminhao caminhao = caminhaoRepository.findById(requestDTO.getCaminhaoId()).get();

        if (!caminhao.getTipoResiduos().contains(rota.getTiposResiduos())) {
            throw new BusinessException("O caminhão não está habilitado para o tipo de resíduo da rota");
        }
        
        if (!caminhao.getId().equals(rota.getCaminhao().getId())) {
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
