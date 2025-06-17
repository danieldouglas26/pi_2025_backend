package com.senaifatesg.pi2025.presentation.controllers;

import java.net.URI;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.senaifatesg.pi2025.core.models.PontoColeta;
import com.senaifatesg.pi2025.core.services.PontoColetaService;
import com.senaifatesg.pi2025.presentation.dtos.mappers.PontoColetaMapper;
import com.senaifatesg.pi2025.presentation.dtos.requests.PontoColetaRequestDTO;
import com.senaifatesg.pi2025.presentation.dtos.responses.PontoColetaResponseDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/pontos-coleta")
@RequiredArgsConstructor
public class PontoColetaController {

    private final PontoColetaService pontoColetaService;
    private final PontoColetaMapper pontoColetaMapper;

    @GetMapping
    public ResponseEntity<Page<PontoColetaResponseDTO>> listarTodos(Pageable pageable) {
        Page<PontoColeta> pontos = pontoColetaService.getAllCollectionPoints(pageable);
        return ResponseEntity.ok(pontos.map(pontoColetaMapper::toResponseDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PontoColetaResponseDTO> buscarPorId(@PathVariable Long id) {
        PontoColeta ponto = pontoColetaService.getCollectionPointById(id);
        return ResponseEntity.ok(pontoColetaMapper.toResponseDTO(ponto));
    }

    @PostMapping
    public ResponseEntity<PontoColetaResponseDTO> criar(
            @RequestBody PontoColetaRequestDTO requestDTO,
            UriComponentsBuilder uriBuilder) {
        
        PontoColeta novoPonto = pontoColetaService.createCollectionPoint(requestDTO);
        URI uri = uriBuilder.path("/api/pontos-coleta/{id}").buildAndExpand(novoPonto.getId()).toUri();
        
        return ResponseEntity.created(uri)
                .body(pontoColetaMapper.toResponseDTO(novoPonto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PontoColetaResponseDTO> atualizar(
            @PathVariable Long id,
            @RequestBody PontoColetaRequestDTO requestDTO) {
        
        PontoColeta pontoAtualizado = pontoColetaService.updateCollectionPoint(id, requestDTO);
        return ResponseEntity.ok(pontoColetaMapper.toResponseDTO(pontoAtualizado));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        pontoColetaService.deleteCollectionPoint(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/por-tipo/{tipoResiduo}")
    public ResponseEntity<Page<PontoColetaResponseDTO>> listarPorTipoResiduo(
            @PathVariable String tipoResiduo,
            Pageable pageable) {
        
        Page<PontoColeta> pontos = pontoColetaService.findByTipoResiduo(tipoResiduo, pageable);
        return ResponseEntity.ok(pontos.map(pontoColetaMapper::toResponseDTO));
    }

    @GetMapping("/por-bairro/{bairro}")
    public ResponseEntity<Page<PontoColetaResponseDTO>> listarPorBairro(
            @PathVariable String bairro,
            Pageable pageable) {
        
        Page<PontoColeta> pontos = pontoColetaService.findByBairro(bairro, pageable);
        return ResponseEntity.ok(pontos.map(pontoColetaMapper::toResponseDTO));
    }
}