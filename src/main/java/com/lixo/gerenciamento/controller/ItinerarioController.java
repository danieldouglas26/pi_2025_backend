package com.lixo.gerenciamento.controller;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lixo.gerenciamento.model.dto.request.ItinerarioRequestDTO;
import com.lixo.gerenciamento.model.dto.response.ItinerarioResponseDTO;
import com.lixo.gerenciamento.service.ItinerarioService;

@RestController
@RequestMapping("/api/itinerarios")
public class ItinerarioController {

    private ItinerarioService itinerarioService;

    
    @Autowired
    public ItinerarioController(ItinerarioService itinerarioService) {
		super();
		this.itinerarioService = itinerarioService;
	}

	@PostMapping
    public ResponseEntity<ItinerarioResponseDTO> criarItinerario(
            @RequestBody ItinerarioRequestDTO requestDTO) {
        ItinerarioResponseDTO response = itinerarioService.criarItinerario(requestDTO);
        return ResponseEntity.created(URI.create("/itinerarios/" + response.getId()))
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItinerarioResponseDTO> buscarItinerario(@PathVariable Long id) {
        return ResponseEntity.ok(itinerarioService.buscarItinerarioPorId(id));
    }

    @GetMapping("/caminhao/{caminhaoId}")
    public ResponseEntity<List<ItinerarioResponseDTO>> listarPorCaminhao(
            @PathVariable Long caminhaoId) {
        return ResponseEntity.ok(itinerarioService.listarItinerariosPorCaminhao(caminhaoId));
    }

    @GetMapping("/data/{data}")
    public ResponseEntity<List<ItinerarioResponseDTO>> listarPorData(
            @PathVariable LocalDate data) {
        return ResponseEntity.ok(itinerarioService.listarItinerariosPorData(data));
    }

    @GetMapping("/periodo")
    public ResponseEntity<List<ItinerarioResponseDTO>> listarPorPeriodo(
            @RequestParam LocalDate inicio, 
            @RequestParam LocalDate fim) {
        return ResponseEntity.ok(itinerarioService.listarItinerariosPorPeriodo(inicio, fim));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItinerarioResponseDTO> atualizarItinerario(
            @PathVariable Long id, 
            @RequestBody ItinerarioRequestDTO requestDTO) {
        return ResponseEntity.ok(itinerarioService.atualizarItinerario(id, requestDTO));
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Void> removerItinerario(@PathVariable Long id) {
        itinerarioService.removerItinerario(id);
        return ResponseEntity.noContent().build();
    }

}
