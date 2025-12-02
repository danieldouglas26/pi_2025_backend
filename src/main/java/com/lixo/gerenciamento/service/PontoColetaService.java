package com.lixo.gerenciamento.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lixo.gerenciamento.model.dto.PontoColetaDTO;
import com.lixo.gerenciamento.model.entity.PontoColeta;
import com.lixo.gerenciamento.model.mapper.PontoColetaMapper;
import com.lixo.gerenciamento.repository.PontoColetaRepository;

@Service
public class PontoColetaService {
    
    @Autowired
    private PontoColetaRepository pontoColetaRepository;
    
    @Autowired
    private PontoColetaMapper pontoColetaMapper;
    
    @Autowired
    private ValidationService validationService;
    
    @Autowired
    private BairroService bairroService;
    
    public List<PontoColetaDTO> findAll() {
        return pontoColetaRepository.findAll()
                .stream()
                .map(pontoColetaMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    public PontoColetaDTO findById(Long id) {
        PontoColeta pontoColeta = pontoColetaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ponto de coleta não encontrado com ID: " + id));
        return pontoColetaMapper.toDTO(pontoColeta);
    }
    
    public PontoColeta findEntityById(Long id) {
        return pontoColetaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ponto de coleta não encontrado com ID: " + id));
    }
    
    public List<PontoColeta> findAllByIds(List<Long> ids) {
        return pontoColetaRepository.findAllById(ids);
    }
    
    public PontoColetaDTO save(PontoColetaDTO pontoColetaDTO) {
        validarPontoColeta(pontoColetaDTO);
        
        PontoColeta pontoColeta = pontoColetaMapper.toEntity(pontoColetaDTO);
        PontoColeta saved = pontoColetaRepository.save(pontoColeta);
        return pontoColetaMapper.toDTO(saved);
    }
    
    public PontoColetaDTO update(Long id, PontoColetaDTO pontoColetaDTO) {
        if (!pontoColetaRepository.existsById(id)) {
            throw new RuntimeException("Ponto de coleta não encontrado com ID: " + id);
        }
        
        validarPontoColeta(pontoColetaDTO);
        
        // Verificar se o nome já existe em outro ponto
        if (pontoColetaRepository.findByNome(pontoColetaDTO.getNome())
                .map(p -> !p.getId().equals(id))
                .orElse(false)) {
            throw new IllegalArgumentException("Já existe um ponto de coleta com o nome: " + pontoColetaDTO.getNome());
        }
        
        PontoColeta pontoColeta = pontoColetaMapper.toEntity(pontoColetaDTO);
        pontoColeta.setId(id);
        PontoColeta updated = pontoColetaRepository.save(pontoColeta);
        return pontoColetaMapper.toDTO(updated);
    }
    
    public void delete(Long id) {
        if (pontoColetaRepository.existsById(id)) {
            pontoColetaRepository.deleteById(id);
        } else {
            throw new RuntimeException("Ponto de coleta não encontrado com ID: " + id);
        }
    }
    
    public List<PontoColetaDTO> findByTipoResiduo(String tipoResiduo) {
        return pontoColetaRepository.findByTipoResiduo(tipoResiduo)
                .stream()
                .map(pontoColetaMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    public List<PontoColetaDTO> findByBairro(Long bairroId) {
        return pontoColetaRepository.findByBairroId(bairroId)
                .stream()
                .map(pontoColetaMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    public List<PontoColetaDTO> findByBairroNome(String bairroNome) {
        return pontoColetaRepository.findByBairroNome(bairroNome)
                .stream()
                .map(pontoColetaMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    private void validarPontoColeta(PontoColetaDTO pontoColetaDTO) {
        if (!validationService.validarNome(pontoColetaDTO.getResponsavel())) {
            throw new IllegalArgumentException("Nome do responsável inválido");
        }
        
        if (pontoColetaDTO.getContato() != null && !pontoColetaDTO.getContato().isEmpty()) {
            if (!validationService.validarEmail(pontoColetaDTO.getContato()) && 
                !validationService.validarTelefone(pontoColetaDTO.getContato())) {
                throw new IllegalArgumentException("Contato inválido. Deve ser um email ou telefone válido");
            }
        }
        
        if (pontoColetaDTO.getBairro() == null || pontoColetaDTO.getBairro().getId() == null) {
            throw new IllegalArgumentException("Bairro é obrigatório");
        }
        
        if (!bairroService.existsByNome(pontoColetaDTO.getBairro().getNome())) {
            throw new IllegalArgumentException("Bairro não encontrado: " + pontoColetaDTO.getBairro().getNome());
        }
        
        if (pontoColetaDTO.getTiposResiduos() != null) {
            for (String tipoResiduo : pontoColetaDTO.getTiposResiduos()) {
                if (!validationService.validarTipoResiduo(tipoResiduo)) {
                    throw new IllegalArgumentException("Tipo de resíduo inválido: " + tipoResiduo);
                }
            }
        }
    }
}
