package com.lixo.gerenciamento.strategy;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lixo.gerenciamento.model.GrafoModel;
import com.lixo.gerenciamento.model.entity.Bairro;
import com.lixo.gerenciamento.model.entity.ConexaoBairro;
import com.lixo.gerenciamento.model.entity.PontoColeta;
import com.lixo.gerenciamento.model.entity.Rota;
import com.lixo.gerenciamento.service.BairroService;
import com.lixo.gerenciamento.service.ConexaoBairroService;

@Component
public class DijkstraJGraphTStrategy implements RoteamentoStrategy {
    
    @Autowired
    private BairroService bairroService;
    
    @Autowired
    private ConexaoBairroService conexaoBairroService;
    
    private GrafoModel grafoModel;
    private DijkstraShortestPath<Long, DefaultWeightedEdge> dijkstra;
    
    @Override
    public Rota calcularMelhorRota(PontoColeta origem, PontoColeta destino) {
        inicializarGrafo();
        
        Long verticeOrigem = origem.getBairro().getId();
        Long verticeDestino = destino.getBairro().getId();
        
        if (!grafoModel.contemVertice(verticeOrigem) || !grafoModel.contemVertice(verticeDestino)) {
            throw new IllegalArgumentException("Vértices de origem ou destino não encontrados no grafo");
        }
        
        GraphPath<Long, DefaultWeightedEdge> caminho = dijkstra.getPath(verticeOrigem, verticeDestino);
        
        if (caminho == null) {
            throw new RuntimeException("Não foi possível encontrar um caminho entre os pontos " + 
                                     origem.getNome() + " e " + destino.getNome());
        }
        
        return construirRota(origem, destino, caminho);
    }
    
    @Override
    public Rota  calcularRotaOtimizada(List<PontoColeta> pontos, Long caminhaoId) {
        if (pontos == null || pontos.size() < 2) {
            throw new IllegalArgumentException("São necessários pelo menos 2 pontos para calcular rota");
        }
        
        inicializarGrafo();
        
        List<PontoColeta> pontosNaoVisitados = new ArrayList<>(pontos);
        List<PontoColeta> rotaOtimizada = new ArrayList<>();
        
        PontoColeta atual = pontosNaoVisitados.remove(0);
        rotaOtimizada.add(atual);
        
        while (!pontosNaoVisitados.isEmpty()) {
            PontoColeta maisProximo = encontrarPontoMaisProximo(atual, pontosNaoVisitados);
            rotaOtimizada.add(maisProximo);
            pontosNaoVisitados.remove(maisProximo);
            atual = maisProximo;
        }
        
        double distanciaTotal = calcularDistanciaRota(rotaOtimizada);
        
        return Rota .builder()
                .nome("Rota Otimizada - " + rotaOtimizada.size() + " pontos")
                .pontosColeta(rotaOtimizada)
                .distanciaTotal(distanciaTotal)
                .tempoEstimadoMinutos(calcularTempoEstimado(distanciaTotal))
                .build();
    }
    
    @Override
    public String getNomeAlgoritmo() {
        return "DIJKSTRA_JGRAPHT";
    }
    
    @Override
    public String getDescricaoAlgoritmo() {
        return "Algoritmo de Dijkstra implementado com JGraphT para cálculo do caminho mais curto em grafos ponderados";
    }
    
    private void inicializarGrafo() {
        if (grafoModel == null) {
            grafoModel = new GrafoModel();
            carregarDadosGrafo();
            
            dijkstra = new DijkstraShortestPath<>(grafoModel.getGrafo());
        }
    }
    
    private void carregarDadosGrafo() {

        List<Bairro> bairros = bairroService.findAll();
        for (Bairro bairro : bairros) {
            grafoModel.adicionarVertice(bairro);
        }
        
        List<ConexaoBairro> conexoes = conexaoBairroService.findAll();
        for (ConexaoBairro conexao : conexoes) {
            grafoModel.adicionarAresta(conexao);
        }
        
        System.out.println("✅ Grafo carregado: " + bairros.size() + " vértices, " + 
                          conexoes.size() + " arestas");
    }
    
    private Rota  construirRota(PontoColeta origem, PontoColeta destino, 
                              GraphPath<Long, DefaultWeightedEdge> caminho) {
        
        List<Bairro> bairrosCaminho = caminho.getVertexList().stream()
                .map(bairroId -> grafoModel.getBairroPorId(bairroId))
                .collect(Collectors.toList());
        
        List<PontoColeta> pontosRota = new ArrayList<>();
        pontosRota.add(origem);
        pontosRota.add(destino);
        
        double distanciaTotal = caminho.getWeight();
        int tempoEstimado = calcularTempoEstimado(distanciaTotal);
        
        return Rota .builder()
                .nome("Rota " + origem.getNome() + " → " + destino.getNome())
                .pontosColeta(pontosRota)
                .caminhoBairros(bairrosCaminho)
                .distanciaTotal(distanciaTotal)
                .tempoEstimadoMinutos(tempoEstimado)
                .build();
    }
    
    private PontoColeta encontrarPontoMaisProximo(PontoColeta origem, List<PontoColeta> pontos) {
        PontoColeta maisProximo = null;
        double menorDistancia = Double.MAX_VALUE;
        
        for (PontoColeta ponto : pontos) {
            try {
            	Rota  rota = calcularMelhorRota(origem, ponto);
                if (rota.getDistanciaTotal() < menorDistancia) {
                    menorDistancia = rota.getDistanciaTotal();
                    maisProximo = ponto;
                }
            } catch (Exception e) {
                System.err.println("⚠️  Erro ao calcular rota para ponto: " + ponto.getNome() + " - " + e.getMessage());
            }
        }
        
        if (maisProximo == null && !pontos.isEmpty()) {
            maisProximo = pontos.get(0);
        }
        
        return maisProximo;
    }
    
    private double calcularDistanciaRota(List<PontoColeta> pontos) {
        double distanciaTotal = 0.0;
        
        for (int i = 0; i < pontos.size() - 1; i++) {
            try {
            	Rota  trecho = calcularMelhorRota(pontos.get(i), pontos.get(i + 1));
                distanciaTotal += trecho.getDistanciaTotal();
            } catch (Exception e) {
                System.err.println("⚠️  Erro no trecho " + i + "-" + (i+1) + ": " + e.getMessage());
                distanciaTotal += 5.0;
            }
        }
        
        return distanciaTotal;
    }
    
    private int calcularTempoEstimado(double distanciaKm) {
        double tempoHoras = distanciaKm / 40.0;
        return (int) Math.ceil((tempoHoras) * 60); 
    }
}
