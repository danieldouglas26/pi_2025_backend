package com.lixo.gerenciamento.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lixo.gerenciamento.model.entity.Rota;
import com.lixo.gerenciamento.service.RoteamentoService;

@RestController
@RequestMapping("/api/rotas")
public class RoteamentoController {
    
    @Autowired
    private RoteamentoService roteamentoService;
    
    @PostMapping("/calcular")
    public ResponseEntity<Rota> calcularRota(@RequestBody CalculoRotaRequest request) {
        Rota rota = roteamentoService.calcularRotaOtimizada(
            request.getOrigemId(), 
            request.getDestinoId(), 
            request.getAlgoritmo()
        );
        return ResponseEntity.ok(rota);
    }
    
    @PostMapping("/calcular-multiplos")
    public ResponseEntity<Rota> calcularRotaMultiplosPontos(@RequestBody CalculoRotaMultiplaRequest request) {
        Rota rota = roteamentoService.calcularRotaMultiplosPontos(
            request.getPontosColetaIds(),
            request.getCaminhaoId(),
            request.getAlgoritmo()
        );
        return ResponseEntity.ok(rota);
    }
    
    @GetMapping("/calcular-padrao")
    public ResponseEntity<Rota> calcularRotaPadrao(
            @RequestParam Long origemId,
            @RequestParam Long destinoId) {
        Rota rota = roteamentoService.calcularRotaPadrao(origemId, destinoId);
        return ResponseEntity.ok(rota);
    }
    
    @GetMapping("/validar-conexao")
    public ResponseEntity<Boolean> validarConexao(
            @RequestParam Long origemId,
            @RequestParam Long destinoId) {
        boolean conectado = roteamentoService.validarConexao(origemId, destinoId);
        return ResponseEntity.ok(conectado);
    }
    
    @GetMapping("/algoritmos")
    public ResponseEntity<Map<String, String>> getAlgoritmosDisponiveis() {
        Map<String, String> algoritmos = roteamentoService.getAlgoritmosDisponiveis();
        return ResponseEntity.ok(algoritmos);
    }
    
    @PostMapping("/salvar")
    public ResponseEntity<Rota> salvarRota(@RequestBody Rota rota) {
        Rota salva = roteamentoService.salvarRota(rota);
        return ResponseEntity.ok(salva);
    }
    
    public static class CalculoRotaRequest {
        private Long origemId;
        private Long destinoId;
        private String algoritmo;
        
        public Long getOrigemId() { return origemId; }
        public void setOrigemId(Long origemId) { this.origemId = origemId; }
        public Long getDestinoId() { return destinoId; }
        public void setDestinoId(Long destinoId) { this.destinoId = destinoId; }
        public String getAlgoritmo() { return algoritmo; }
        public void setAlgoritmo(String algoritmo) { this.algoritmo = algoritmo; }
    }
    
    public static class CalculoRotaMultiplaRequest {
        private List<Long> pontosColetaIds;
        private Long caminhaoId;
        private String algoritmo;
        
        public List<Long> getPontosColetaIds() { return pontosColetaIds; }
        public void setPontosColetaIds(List<Long> pontosColetaIds) { this.pontosColetaIds = pontosColetaIds; }
        public Long getCaminhaoId() { return caminhaoId; }
        public void setCaminhaoId(Long caminhaoId) { this.caminhaoId = caminhaoId; }
        public String getAlgoritmo() { return algoritmo; }
        public void setAlgoritmo(String algoritmo) { this.algoritmo = algoritmo; }
    }
}
