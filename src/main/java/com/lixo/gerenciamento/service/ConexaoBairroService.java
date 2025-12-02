package com.lixo.gerenciamento.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lixo.gerenciamento.model.entity.ConexaoBairro;
import com.lixo.gerenciamento.repository.ConexaoBairroRepository;

@Service
public class ConexaoBairroService {
    
    @Autowired
    private ConexaoBairroRepository conexaoBairroRepository;
    
    public List<ConexaoBairro> findAll() {
        return conexaoBairroRepository.findAll();
    }
    
    public ConexaoBairro save(ConexaoBairro conexao) {
        return conexaoBairroRepository.save(conexao);
    }
    
    public List<ConexaoBairro> saveAll(List<ConexaoBairro> conexoes) {
        return conexaoBairroRepository.saveAll(conexoes);
    }
    
    public List<ConexaoBairro> findByBairroOrigem(Long bairroOrigemId) {
        return conexaoBairroRepository.findByBairroOrigemId(bairroOrigemId);
    }
    
    public List<ConexaoBairro> findByBairroDestino(Long bairroDestinoId) {
        return conexaoBairroRepository.findByBairroDestinoId(bairroDestinoId);
    }
    
    public Optional<ConexaoBairro> findByOrigemAndDestino(Long origemId, Long destinoId) {
        return conexaoBairroRepository.findByOrigemAndDestino(origemId, destinoId);
    }
    
    public void delete(Long id) {
        if (conexaoBairroRepository.existsById(id)) {
            conexaoBairroRepository.deleteById(id);
        }
    }
}
