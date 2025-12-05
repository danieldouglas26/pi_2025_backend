package com.lixo.gerenciamento.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lixo.gerenciamento.model.dto.CaminhaoDTO;
import com.lixo.gerenciamento.service.CaminhaoService;
import com.lixo.gerenciamento.validation.annotation.PlacaValida;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/caminhoes")
@Validated
public class CaminhaoController {
    
    @Autowired
    private CaminhaoService caminhaoService;
    
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'COLETOR')")
    public ResponseEntity<List<CaminhaoDTO>> getAllCaminhoes() {
        List<CaminhaoDTO> caminhoes = caminhaoService.findAll();
        return ResponseEntity.ok(caminhoes);
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'COLETOR')")
    public ResponseEntity<CaminhaoDTO> getCaminhaoById(@PathVariable Long id) {
        CaminhaoDTO caminhao = caminhaoService.findById(id);
        return ResponseEntity.ok(caminhao);
    }
    
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'COLETOR')")
    public ResponseEntity<CaminhaoDTO> createCaminhao(@Valid @RequestBody CaminhaoDTO caminhaoDTO) {
        CaminhaoDTO savedCaminhao = caminhaoService.save(caminhaoDTO);
        return ResponseEntity.ok(savedCaminhao);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'COLETOR')")
    public ResponseEntity<CaminhaoDTO> updateCaminhao(@PathVariable Long id, 
                                                     @Valid @RequestBody CaminhaoDTO caminhaoDTO) {
        CaminhaoDTO updatedCaminhao = caminhaoService.update(id, caminhaoDTO);
        return ResponseEntity.ok(updatedCaminhao);
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'COLETOR')")
    public ResponseEntity<Void> deleteCaminhao(@PathVariable Long id) {
        caminhaoService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/placa/{placa}/valida")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'COLETOR')")
    public ResponseEntity<Boolean> validarPlaca(@PathVariable @PlacaValida String placa) {
        boolean isValid = caminhaoService.validarPlaca(placa);
        return ResponseEntity.ok(isValid);
    }
    
    @GetMapping("/tipo-residuo/{tipoResiduo}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'COLETOR')")
    public ResponseEntity<List<CaminhaoDTO>> getCaminhoesPorTipoResiduo(@PathVariable String tipoResiduo) {
        List<CaminhaoDTO> caminhoes = caminhaoService.findByTipoResiduo(tipoResiduo);
        return ResponseEntity.ok(caminhoes);
    }
    
    @GetMapping("/capacidade-minima/{capacidadeMinima}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'COLETOR')")
    public ResponseEntity<List<CaminhaoDTO>> getCaminhoesPorCapacidadeMinima(@PathVariable Double capacidadeMinima) {
        List<CaminhaoDTO> caminhoes = caminhaoService.findByCapacidadeMinima(capacidadeMinima);
        return ResponseEntity.ok(caminhoes);
    }
}
