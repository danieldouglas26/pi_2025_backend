package com.lixo.gerenciamento.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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

import com.lixo.gerenciamento.model.dto.ItinerarioDTO;
import com.lixo.gerenciamento.service.PlanejamentoService;

@RestController
@RequestMapping("/api/planejamento")
public class PlanejamentoController {
    
    @Autowired
    private PlanejamentoService planejamentoService;
    
    @PostMapping("/agendar")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'COLETOR')")
    public ResponseEntity<ItinerarioDTO> agendarItinerario(@RequestBody AgendamentoRequest request) {
        ItinerarioDTO itinerario = planejamentoService.agendarItinerario(
            request.getRotaId(),
            request.getCaminhaoId(), 
            request.getData()
        );
        return ResponseEntity.ok(itinerario);
    }
    
    @GetMapping("/cronograma-mensal")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'COLETOR')")
    public ResponseEntity<List<ItinerarioDTO>> getCronogramaMensal(
            @RequestParam Integer mes,
            @RequestParam Integer ano) {
        List<ItinerarioDTO> itinerarios = planejamentoService.gerarCronogramaMensal(mes, ano);
        return ResponseEntity.ok(itinerarios);
    }
    
    @GetMapping("/itinerarios-caminhao")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'COLETOR')")
    public ResponseEntity<List<ItinerarioDTO>> getItinerariosPorCaminhao(
            @RequestParam Long caminhaoId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {
        List<ItinerarioDTO> itinerarios = planejamentoService.consultarItinerariosPorCaminhao(caminhaoId, dataInicio, dataFim);
        return ResponseEntity.ok(itinerarios);
    }
    
    @GetMapping("/itinerarios-data")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'COLETOR')")
    public ResponseEntity<List<ItinerarioDTO>> getItinerariosPorData(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
        List<ItinerarioDTO> itinerarios = planejamentoService.consultarItinerariosPorData(data);
        return ResponseEntity.ok(itinerarios);
    }
    
    @PutMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'COLETOR')")
    public ResponseEntity<ItinerarioDTO> atualizarStatusItinerario(
            @PathVariable Long id,
            @RequestParam String status) {
        ItinerarioDTO itinerario = planejamentoService.atualizarStatusItinerario(id, status);
        return ResponseEntity.ok(itinerario);
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'COLETOR')")
    public ResponseEntity<Void> cancelarItinerario(@PathVariable Long id) {
        planejamentoService.cancelarItinerario(id);
        return ResponseEntity.noContent().build();
    }
    
    public static class AgendamentoRequest {
        private Long rotaId;
        private Long caminhaoId;
        private LocalDate data;
        
        public Long getRotaId() { return rotaId; }
        public void setRotaId(Long rotaId) { this.rotaId = rotaId; }
        public Long getCaminhaoId() { return caminhaoId; }
        public void setCaminhaoId(Long caminhaoId) { this.caminhaoId = caminhaoId; }
        public LocalDate getData() { return data; }
        public void setData(LocalDate data) { this.data = data; }
    }
}