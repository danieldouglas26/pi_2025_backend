package com.lixo.gerenciamento.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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

import com.lixo.gerenciamento.exception.BusinessException;
import com.lixo.gerenciamento.exception.ResourceNotFoundException;
import com.lixo.gerenciamento.model.dto.request.CaminhaoRequestDTO;
import com.lixo.gerenciamento.model.dto.response.CaminhaoResponseDTO;
import com.lixo.gerenciamento.model.entity.Caminhao;
import com.lixo.gerenciamento.model.mapper.CaminhaoMapper;
import com.lixo.gerenciamento.service.CaminhaoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/caminhoes")
public class CaminhaoController {

    private final CaminhaoService caminhaoService;
    private final CaminhaoMapper caminhaoMapper;

    @Autowired
    public CaminhaoController(CaminhaoService caminhaoService, CaminhaoMapper caminhaoMapper) {
		super();
		this.caminhaoService = caminhaoService;
		this.caminhaoMapper = caminhaoMapper;
	}

	@GetMapping
    public ResponseEntity<Page<CaminhaoResponseDTO>> listarCaminhoes(
            Pageable pageable) {
        Page<Caminhao> caminhoes = caminhaoService.getAllTrucks(pageable);
        Page<CaminhaoResponseDTO> responseDTOs = caminhoes.map(caminhaoMapper::toResponseDTO);
        return ResponseEntity.ok(responseDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CaminhaoResponseDTO> buscarCaminhaoPorId(
            @PathVariable Long id) {
        try {
            Caminhao caminhao = caminhaoService.getCaminhaoPorId(id);
            return ResponseEntity.ok(caminhaoMapper.toResponseDTO(caminhao));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<CaminhaoResponseDTO> criarCaminhao(
            @Valid @RequestBody CaminhaoRequestDTO requestDTO) {
        try {
            Caminhao caminhao = caminhaoService.criarCaminhao(requestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(caminhaoMapper.toResponseDTO(caminhao));
        } catch (BusinessException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CaminhaoResponseDTO> atualizarCaminhao(
            @PathVariable Long id,
            @Valid @RequestBody CaminhaoRequestDTO requestDTO) {
        try {
            Caminhao caminhao = caminhaoService.atualizarCaminhao(id, requestDTO);
            return ResponseEntity.ok(caminhaoMapper.toResponseDTO(caminhao));
        } catch (BusinessException e) {
            return ResponseEntity.badRequest().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Void> removerCaminhao(
            @PathVariable Long id) {
        try {
            caminhaoService.deletarCaminhao(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
