package com.lixo.gerenciamento.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lixo.gerenciamento.model.dto.PontoColetaDTO;
import com.lixo.gerenciamento.service.PontoColetaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/pontos-coleta")
public class PontoColetaController {
    
    @Autowired
    private PontoColetaService pontoColetaService;
    
    @GetMapping
    public ResponseEntity<List<PontoColetaDTO>> getAllPontosColeta() {
        List<PontoColetaDTO> pontos = pontoColetaService.findAll();
        return ResponseEntity.ok(pontos);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<PontoColetaDTO> getPontoColetaById(@PathVariable Long id) {
        PontoColetaDTO ponto = pontoColetaService.findById(id);
        return ResponseEntity.ok(ponto);
    }
    
    @PostMapping
    public ResponseEntity<PontoColetaDTO> createPontoColeta(@Valid @RequestBody PontoColetaDTO pontoColetaDTO) {
        PontoColetaDTO savedPonto = pontoColetaService.save(pontoColetaDTO);
        return ResponseEntity.ok(savedPonto);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<PontoColetaDTO> updatePontoColeta(@PathVariable Long id, 
                                                           @Valid @RequestBody PontoColetaDTO pontoColetaDTO) {
        PontoColetaDTO updatedPonto = pontoColetaService.update(id, pontoColetaDTO);
        return ResponseEntity.ok(updatedPonto);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePontoColeta(@PathVariable Long id) {
        pontoColetaService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/tipo-residuo/{tipoResiduo}")
    public ResponseEntity<List<PontoColetaDTO>> getPontosPorTipoResiduo(@PathVariable String tipoResiduo) {
        List<PontoColetaDTO> pontos = pontoColetaService.findByTipoResiduo(tipoResiduo);
        return ResponseEntity.ok(pontos);
    }
    
    @GetMapping("/bairro/{bairroId}")
    public ResponseEntity<List<PontoColetaDTO>> getPontosPorBairro(@PathVariable Long bairroId) {
        List<PontoColetaDTO> pontos = pontoColetaService.findByBairro(bairroId);
        return ResponseEntity.ok(pontos);
    }
    
    @GetMapping("/bairro-nome/{bairroNome}")
    public ResponseEntity<List<PontoColetaDTO>> getPontosPorBairroNome(@PathVariable String bairroNome) {
        List<PontoColetaDTO> pontos = pontoColetaService.findByBairroNome(bairroNome);
        return ResponseEntity.ok(pontos);
    }
}
