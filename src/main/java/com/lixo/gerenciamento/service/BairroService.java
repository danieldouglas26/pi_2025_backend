package com.lixo.gerenciamento.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lixo.gerenciamento.model.entity.Bairro;
import com.lixo.gerenciamento.repository.BairroRepository;

@Service
public class BairroService {
    
    @Autowired
    private BairroRepository bairroRepository;
    
    public List<Bairro> findAll() {
        return bairroRepository.findAll();
    }
    
    public Bairro findById(Long id) {
        return bairroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bairro não encontrado com ID: " + id));
    }
    
    public Bairro save(Bairro bairro) {
        return bairroRepository.save(bairro);
    }
    
    public List<Bairro> saveAll(List<Bairro> bairros) {
        return bairroRepository.saveAll(bairros);
    }
    
    public Optional<Bairro> findByNome(String nome) {
        return bairroRepository.findByNome(nome);
    }
    
    public List<Bairro> findComPontosColeta() {
        return bairroRepository.findByTemPontoColetaTrue();
    }
    
    public boolean existsByNome(String nome) {
        return bairroRepository.existsByNome(nome);
    }
    
    public void delete(Long id) {
        if (bairroRepository.existsById(id)) {
            bairroRepository.deleteById(id);
        } else {
            throw new RuntimeException("Bairro não encontrado com ID: " + id);
        }
    }
}