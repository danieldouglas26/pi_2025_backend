package com.lixo.gerenciamento.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lixo.gerenciamento.service.ConsultaService;

@RestController
@RequestMapping("/api/consultas")
public class ConsultaController {
    
    @Autowired
    private ConsultaService consultaService;
    
    @PostMapping("/avancada")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'COLETOR')")
    public ResponseEntity<List<Map<String, Object>>> consultaAvancada(@RequestBody String consulta) {
        List<Map<String, Object>> resultados = consultaService.executarConsultaAvancada(consulta);
        return ResponseEntity.ok(resultados);
    }
    
    @GetMapping("/pontos-coleta/tipo-residuo/{tipo}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'COLETOR')")
    public ResponseEntity<List<Map<String, Object>>> consultarPontosPorTipoResiduo(@PathVariable String tipo) {
        List<Map<String, Object>> pontos = consultaService.consultarPontosPorTipoResiduo(tipo);
        return ResponseEntity.ok(pontos);
    }
    
    @GetMapping("/caminhoes/disponiveis")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'COLETOR')")
    public ResponseEntity<List<Map<String, Object>>> consultarCaminhoesDisponiveis(
            @RequestParam String data,
            @RequestParam(required = false) String tipoResiduo) {
        List<Map<String, Object>> caminhoes = consultaService.consultarCaminhoesDisponiveis(data, tipoResiduo);
        return ResponseEntity.ok(caminhoes);
    }
    
    @GetMapping("/rotas/estatisticas")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'COLETOR')")
    public ResponseEntity<Map<String, Object>> obterEstatisticasRotas() {
        Map<String, Object> estatisticas = consultaService.obterEstatisticasRotas();
        return ResponseEntity.ok(estatisticas);
    }
    
    @GetMapping("/itinerarios/status")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'COLETOR')")
    public ResponseEntity<Map<String, Long>> obterContagemItinerariosPorStatus() {
        Map<String, Long> contagem = consultaService.obterContagemItinerariosPorStatus();
        return ResponseEntity.ok(contagem);
    }
}
