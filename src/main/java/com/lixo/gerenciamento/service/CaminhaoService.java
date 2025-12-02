package com.lixo.gerenciamento.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lixo.gerenciamento.model.dto.CaminhaoDTO;
import com.lixo.gerenciamento.model.entity.Caminhao;
import com.lixo.gerenciamento.model.mapper.CaminhaoMapper;
import com.lixo.gerenciamento.repository.CaminhaoRepository;
import com.lixo.gerenciamento.validation.annotation.PlacaValida;

@Service
public class CaminhaoService {
    
    @Autowired
    private CaminhaoRepository caminhaoRepository;
    
    @Autowired
    private CaminhaoMapper caminhaoMapper;
    
    @Autowired
    private ValidationService validationService;
    
    public List<CaminhaoDTO> findAll() {
        return caminhaoRepository.findAll()
                .stream()
                .map(caminhaoMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    public CaminhaoDTO findById(Long id) {
        Caminhao caminhao = caminhaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Caminhão não encontrado com ID: " + id));
        return caminhaoMapper.toDTO(caminhao);
    }
    
    public CaminhaoDTO save(CaminhaoDTO caminhaoDTO) {
        validarCaminhao(caminhaoDTO);
        
        Caminhao caminhao = caminhaoMapper.toEntity(caminhaoDTO);
        Caminhao saved = caminhaoRepository.save(caminhao);
        return caminhaoMapper.toDTO(saved);
    }
    
    public CaminhaoDTO update(Long id, CaminhaoDTO caminhaoDTO) {
        if (!caminhaoRepository.existsById(id)) {
            throw new RuntimeException("Caminhão não encontrado com ID: " + id);
        }
        
        validarCaminhao(caminhaoDTO);
        
        // Verificar se a placa já existe em outro caminhão
        if (caminhaoRepository.findByPlaca(caminhaoDTO.getPlaca())
                .map(c -> !c.getId().equals(id))
                .orElse(false)) {
            throw new IllegalArgumentException("Já existe um caminhão com a placa: " + caminhaoDTO.getPlaca());
        }
        
        Caminhao caminhao = caminhaoMapper.toEntity(caminhaoDTO);
        caminhao.setId(id); // Garantir que o ID seja o mesmo
        Caminhao updated = caminhaoRepository.save(caminhao);
        return caminhaoMapper.toDTO(updated);
    }
    
    public void delete(Long id) {
        if (caminhaoRepository.existsById(id)) {
            caminhaoRepository.deleteById(id);
        } else {
            throw new RuntimeException("Caminhão não encontrado com ID: " + id);
        }
    }
    
    public Caminhao findEntityById(Long id) {
        return caminhaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Caminhão não encontrado com ID: " + id));
    }
    
    public boolean validarPlaca(@PlacaValida String placa) {
        return validationService.validarPlaca(placa);
    }
    
    public List<CaminhaoDTO> findByTipoResiduo(String tipoResiduo) {
        return caminhaoRepository.findByTipoResiduo(tipoResiduo)
                .stream()
                .map(caminhaoMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    public List<CaminhaoDTO> findByCapacidadeMinima(Double capacidadeMinima) {
        return caminhaoRepository.findByCapacidadeMinima(capacidadeMinima)
                .stream()
                .map(caminhaoMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    private void validarCaminhao(CaminhaoDTO caminhaoDTO) {
        if (!validationService.validarPlaca(caminhaoDTO.getPlaca())) {
            throw new IllegalArgumentException("Placa inválida: " + caminhaoDTO.getPlaca());
        }
        
        if (!validationService.validarNome(caminhaoDTO.getMotorista())) {
            throw new IllegalArgumentException("Nome do motorista inválido");
        }
        
        if (caminhaoDTO.getCapacidadeMaxima() == null || caminhaoDTO.getCapacidadeMaxima() <= 0) {
            throw new IllegalArgumentException("Capacidade máxima deve ser maior que zero");
        }
        
        if (caminhaoDTO.getTiposResiduos() != null) {
            for (String tipoResiduo : caminhaoDTO.getTiposResiduos()) {
                if (!validationService.validarTipoResiduo(tipoResiduo)) {
                    throw new IllegalArgumentException("Tipo de resíduo inválido: " + tipoResiduo);
                }
            }
        }
    }
}