package com.senaifatesg.pi2025.presentation.controllers;

import java.util.List;

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

import com.senaifatesg.pi2025.common.BusinessException;
import com.senaifatesg.pi2025.common.ResourceNotFoundException;
import com.senaifatesg.pi2025.core.services.RotaService;
import com.senaifatesg.pi2025.presentation.dtos.requests.RotaRequestDTO;
import com.senaifatesg.pi2025.presentation.dtos.responses.RotaResponseDTO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/rotas")
@RequiredArgsConstructor
public class RotaController {

	private final RotaService rotaService;

	@PostMapping
	public ResponseEntity<RotaResponseDTO> criarRota(@Valid @RequestBody RotaRequestDTO requestDTO) {
		try {
			RotaResponseDTO responseDTO = rotaService.criarRota(requestDTO);
			return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
		} catch (BusinessException e) {
			return ResponseEntity.badRequest().build();
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<RotaResponseDTO> buscarRotaPorId(@PathVariable Long id) {
		try {
			RotaResponseDTO responseDTO = rotaService.buscarRotaCompleta(id);
			return ResponseEntity.ok(responseDTO);
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/caminhao/{caminhaoId}")
	public ResponseEntity<List<RotaResponseDTO>> listarRotasPorCaminhao(@PathVariable Long caminhaoId) {
		List<RotaResponseDTO> rotas = rotaService.listarRotasPorCaminhao(caminhaoId);
		return ResponseEntity.ok(rotas);
	}

	@PutMapping("/{id}")
	public ResponseEntity<RotaResponseDTO> atualizarRota(@PathVariable Long id,
			@Valid @RequestBody RotaRequestDTO requestDTO) {
		try {
			RotaResponseDTO responseDTO = rotaService.atualizarRota(id, requestDTO);
			return ResponseEntity.ok(responseDTO);
		} catch (BusinessException e) {
			return ResponseEntity.badRequest().build();
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/{id}")
	 @PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Void> removerRota(@PathVariable Long id) {
		try {
			rotaService.removerRota(id);
			return ResponseEntity.noContent().build();
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping
    public ResponseEntity<List<RotaResponseDTO>> listarTodasAsRotas() {
        List<RotaResponseDTO> rotas = rotaService.listarTodasAsRotas();
        return ResponseEntity.ok(rotas);
    }

	@PostMapping("/{id}/recalcular")
	public ResponseEntity<RotaResponseDTO> recalcularRota(@PathVariable Long id) {
		try {
			RotaResponseDTO responseDTO = rotaService.recalcularRota(id);
			return ResponseEntity.ok(responseDTO);
		} catch (BusinessException e) {
			return ResponseEntity.badRequest().build();
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}
}