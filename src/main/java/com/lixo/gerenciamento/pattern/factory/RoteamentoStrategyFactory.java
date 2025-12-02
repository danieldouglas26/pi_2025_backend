package com.lixo.gerenciamento.pattern.factory;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lixo.gerenciamento.strategy.DijkstraJGraphTStrategy;
import com.lixo.gerenciamento.strategy.RoteamentoStrategy;

@Component
public class RoteamentoStrategyFactory {
    
    private final Map<String, RoteamentoStrategy> strategies;
    
    @Autowired
    public RoteamentoStrategyFactory(DijkstraJGraphTStrategy dijkstraStrategy) {
        this.strategies = new HashMap<>();
        
        strategies.put("DIJKSTRA", dijkstraStrategy);
        strategies.put("DIJKSTRA_JGRAPHT", dijkstraStrategy);
        strategies.put("PADRAO", dijkstraStrategy); 
    }
    
    public RoteamentoStrategy getStrategy(String algoritmo) {
        String key = (algoritmo != null && !algoritmo.trim().isEmpty()) ? 
                     algoritmo.toUpperCase() : "PADRAO";
        
        RoteamentoStrategy strategy = strategies.get(key);
        
        if (strategy == null) {
            throw new IllegalArgumentException(
                "Algoritmo de roteamento não suportado: '" + algoritmo + 
                "'. Algoritmos disponíveis: " + String.join(", ", strategies.keySet())
            );
        }
        
        return strategy;
    }
    
    public Map<String, String> getAlgoritmosDisponiveis() {
        Map<String, String> algoritmos = new HashMap<>();
        for (Map.Entry<String, RoteamentoStrategy> entry : strategies.entrySet()) {
            algoritmos.put(entry.getKey(), entry.getValue().getDescricaoAlgoritmo());
        }
        return algoritmos;
    }
}
