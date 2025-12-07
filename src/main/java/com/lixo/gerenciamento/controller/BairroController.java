package com.lixo.gerenciamento.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.lixo.gerenciamento.model.dto.request.BairroRequestDTO;
import com.lixo.gerenciamento.model.dto.response.BairroResponseDTO;
import com.lixo.gerenciamento.service.BairroService;

@RestController
@RequestMapping("/api/bairros")
public class BairroController {

    private BairroService bairroService;

    @Autowired
    public BairroController(BairroService bairroService) {
		super();
		this.bairroService = bairroService;
	}

	@GetMapping
    public ResponseEntity<Page<BairroResponseDTO>> listarTodos(Pageable pageable) {
        return ResponseEntity.ok(bairroService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BairroResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(bairroService.findById(id));
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<BairroResponseDTO> buscarPorNome(@PathVariable String nome) {
        return ResponseEntity.ok(bairroService.findByNome(nome));
    }

    @PostMapping
    public ResponseEntity<BairroResponseDTO> criar(
            @RequestBody BairroRequestDTO requestDTO,
            UriComponentsBuilder uriBuilder) {
        
        BairroResponseDTO bairroCriado = bairroService.create(requestDTO);
        URI uri = uriBuilder.path("/api/bairros/{id}").buildAndExpand(bairroCriado.getId()).toUri();
        
        return ResponseEntity.created(uri).body(bairroCriado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BairroResponseDTO> atualizar(
            @PathVariable Long id,
            @RequestBody BairroRequestDTO requestDTO) {
        
        return ResponseEntity.ok(bairroService.update(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        bairroService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
