package com.lixo.gerenciamento.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lixo.gerenciamento.model.entity.PontoColeta;
import com.lixo.gerenciamento.model.entity.Rota;
import com.lixo.gerenciamento.pattern.factory.RoteamentoStrategyFactory;
import com.lixo.gerenciamento.strategy.RoteamentoStrategy;

@Service
public class RoteamentoService {
    
    @Autowired
    private RoteamentoStrategyFactory strategyFactory;
    
    @Autowired
    private PontoColetaService pontoColetaService;
    
    @Autowired
    private BairroService bairroService;
    
    public Rota calcularRotaOtimizada(Long origemId, Long destinoId, String algoritmo) {
        PontoColeta origem = pontoColetaService.findEntityById(origemId);
        PontoColeta destino = pontoColetaService.findEntityById(destinoId);
        
        RoteamentoStrategy strategy = strategyFactory.getStrategy(algoritmo);
        return strategy.calcularMelhorRota(origem, destino);
    }
    
    public Rota calcularRotaMultiplosPontos(List<Long> pontosColetaIds, Long caminhaoId, String algoritmo) {
        List<PontoColeta> pontos = pontoColetaService.findAllByIds(pontosColetaIds);
        
        if (pontos.size() < 2) {
            throw new IllegalArgumentException("São necessários pelo menos 2 pontos para calcular rota");
        }
        
        RoteamentoStrategy strategy = strategyFactory.getStrategy(algoritmo);
        return strategy.calcularRotaOtimizada(pontos, caminhaoId);
    }
    
    public Rota calcularRotaPadrao(Long origemId, Long destinoId) {
        return calcularRotaOtimizada(origemId, destinoId, "DIJKSTRA");
    }
    
    public Map<String, String> getAlgoritmosDisponiveis() {
        return strategyFactory.getAlgoritmosDisponiveis();
    }
    
    public boolean validarConexao(Long origemId, Long destinoId) {
        try {
            calcularRotaOtimizada(origemId, destinoId, "DIJKSTRA");
            return true;
        } catch (Exception e) {
            System.err.println("Conexão inválida: " + e.getMessage());
            return false;
        }
    }
    
    public Rota salvarRota(Rota rota) {
        return rota; 
    }
}
