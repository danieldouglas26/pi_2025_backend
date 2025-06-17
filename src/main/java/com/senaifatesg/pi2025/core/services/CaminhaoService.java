package com.senaifatesg.pi2025.core.services;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.senaifatesg.pi2025.common.BusinessException;
import com.senaifatesg.pi2025.common.ResourceNotFoundException;
import com.senaifatesg.pi2025.core.models.Caminhao;
import com.senaifatesg.pi2025.core.repositories.CaminhaoRepository;
import com.senaifatesg.pi2025.presentation.dtos.mappers.CaminhaoMapper;
import com.senaifatesg.pi2025.presentation.dtos.requests.CaminhaoRequestDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CaminhaoService {

    private final CaminhaoRepository caminhaoRepositorio;
    private final CaminhaoMapper mapper;


    public Page<Caminhao> getAllTrucks(Pageable pageable) {
        return caminhaoRepositorio.findAll(pageable);
    }

    public Caminhao getCaminhaoPorId(Long id) {
        return caminhaoRepositorio.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Truck not found with ID: " + id));
    }

    @Transactional
    public Caminhao criarCaminhao(CaminhaoRequestDTO requestDTO) {
        if (caminhaoRepositorio.findByPlaca(requestDTO.getPlaca()).isPresent()) {
            throw new BusinessException("Caminhão com placa: " + requestDTO.getPlaca()+ " já existe.");
        }
        
        Caminhao novoCaminhao = mapper.toEntity(requestDTO);
        
        //Apenas para compor requisito de Teoria da computação.
        novoCaminhao.setChaveModular(gerarIdModular(novoCaminhao.getPlaca(), LocalDateTime.now().getDayOfMonth()));
        
        return caminhaoRepositorio.save(novoCaminhao);
    }

     @Transactional
    public Caminhao atualizarCaminhao(Long id, CaminhaoRequestDTO requestDTO) {
        Caminhao caminhaoExistente = getCaminhaoPorId(id); 

        Optional<Caminhao> caminhaoComMesmaPlaca = caminhaoRepositorio.findByPlaca(requestDTO.getPlaca());
        if (caminhaoComMesmaPlaca.isPresent() && !caminhaoComMesmaPlaca.get().getId().equals(id)) {
            throw new BusinessException("Caminhão com placa: " + requestDTO.getPlaca() + " já existe.");
        }

        mapper.updateFromDto(requestDTO, caminhaoExistente);

    
        return caminhaoRepositorio.save(caminhaoExistente);
    }
    private Long gerarIdModular(String infoUnica, int dia) {
        int hash = infoUnica.hashCode(); 
        return (long) ((hash + dia) % 997); 
    }
    
    @Transactional
    public void deletarCaminhao(Long id) {
        Caminhao caminhao = getCaminhaoPorId(id);
        caminhaoRepositorio.delete(caminhao);
    }
}