package com.lixo.gerenciamento.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lixo.gerenciamento.model.entity.PontoColeta;
import com.lixo.gerenciamento.repository.PontoColetaRepository;
import com.lixo.gerenciamento.service.SimpleQueryParser;

@RestController
@RequestMapping("/api/admin/relatorios")
public class RelatorioAdminController {

    @Autowired
    private PontoColetaRepository repository;

    @PostMapping("/consulta-avancada")
    @PreAuthorize("hasRole('ADMIN')") // Apenas admin acessa
    public ResponseEntity<List<PontoColeta>> consultar(@RequestBody String comando) {
        try {
            SimpleQueryParser parser = new SimpleQueryParser();
            
            // 1. Compila o texto em uma Specification (Query DB)
            Specification<PontoColeta> spec = parser.parse(comando);
            
            // 2. Executa no banco
            List<PontoColeta> resultados = repository.findAll(spec);
            
            return ResponseEntity.ok(resultados);
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}