package com.lixo.gerenciamento.model;

import java.util.HashMap;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;

import com.lixo.gerenciamento.model.entity.Bairro;
import com.lixo.gerenciamento.model.entity.ConexaoBairro;

public class GrafoModel {
    private Graph<Long, DefaultWeightedEdge> grafo;
    private Map<Long, Bairro> verticesMap;
    private Map<String, DefaultWeightedEdge> arestasMap;
    
    public GrafoModel() {
        this.grafo = new DefaultDirectedWeightedGraph<>(DefaultWeightedEdge.class);
        this.verticesMap = new HashMap<>();
        this.arestasMap = new HashMap<>();
    }
    
    public void adicionarVertice(Bairro bairro) {
        grafo.addVertex(bairro.getId());
        verticesMap.put(bairro.getId(), bairro);
    }
    
    public void adicionarAresta(ConexaoBairro conexao) {
        Long origem = conexao.getBairroOrigem().getId();
        Long destino = conexao.getBairroDestino().getId();
        Double peso = conexao.getDistancia();
        
        DefaultWeightedEdge aresta = grafo.addEdge(origem, destino);
        if (aresta != null) {
            grafo.setEdgeWeight(aresta, peso);
            String key = origem + "-" + destino;
            arestasMap.put(key, aresta);
        }
    }
    
    public Graph<Long, DefaultWeightedEdge> getGrafo() {
        return grafo;
    }
    
    public Bairro getBairroPorId(Long id) {
        return verticesMap.get(id);
    }
    
    public Double getPesoAresta(Long origem, Long destino) {
        String key = origem + "-" + destino;
        DefaultWeightedEdge aresta = arestasMap.get(key);
        return aresta != null ? grafo.getEdgeWeight(aresta) : null;
    }
    
    public boolean contemVertice(Long verticeId) {
        return grafo.containsVertex(verticeId);
    }
    
    public boolean contemAresta(Long origem, Long destino) {
        return grafo.containsEdge(origem, destino);
    }
}