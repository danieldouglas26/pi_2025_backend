package com.senaifatesg.pi2025.presentation.controllers;

import java.net.URI;
import java.util.List;

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

import com.senaifatesg.pi2025.core.services.RuaService;
import com.senaifatesg.pi2025.presentation.dtos.requests.RuaRequestDTO;
import com.senaifatesg.pi2025.presentation.dtos.responses.RuaResponseDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/ruas")
@RequiredArgsConstructor
public class RuaController {

    private final RuaService ruaService;

    @GetMapping
    public ResponseEntity<Page<RuaResponseDTO>> listarTodas(Pageable pageable) {
        return ResponseEntity.ok(ruaService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RuaResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(ruaService.findById(id));
    }

    @PostMapping
    public ResponseEntity<RuaResponseDTO> criar(
            @RequestBody RuaRequestDTO requestDTO,
            UriComponentsBuilder uriBuilder) {
        
        RuaResponseDTO ruaCriada = ruaService.create(requestDTO);
        URI uri = uriBuilder.path("/api/ruas/{id}").buildAndExpand(ruaCriada.getId()).toUri();
        
        return ResponseEntity.created(uri).body(ruaCriada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RuaResponseDTO> atualizar(
            @PathVariable Long id,
            @RequestBody RuaRequestDTO requestDTO) {
        
        return ResponseEntity.ok(ruaService.update(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        ruaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/origem/{pontoColetaId}")
    public ResponseEntity<List<RuaResponseDTO>> listarPorOrigem(
            @PathVariable Long pontoColetaId) {
        
        return ResponseEntity.ok(ruaService.findRuasByOrigem(pontoColetaId));
    }

    @GetMapping("/destino/{pontoColetaId}")
    public ResponseEntity<List<RuaResponseDTO>> listarPorDestino(
            @PathVariable Long pontoColetaId) {
        
        return ResponseEntity.ok(ruaService.findRuasByDestino(pontoColetaId));
    }

    @GetMapping("/conexoes/{pontoColetaId}")
    public ResponseEntity<List<RuaResponseDTO>> listarConexoes(
            @PathVariable Long pontoColetaId) {
        
        List<RuaResponseDTO> saidas = ruaService.findRuasByOrigem(pontoColetaId);
        List<RuaResponseDTO> entradas = ruaService.findRuasByDestino(pontoColetaId);
        
        saidas.addAll(entradas);
        return ResponseEntity.ok(saidas);
    }
}
