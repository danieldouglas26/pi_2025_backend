package com.senaifatesg.pi2025.common.adapters;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.springframework.stereotype.Component;

import com.senaifatesg.pi2025.core.models.interfaces.IAresta;
import com.senaifatesg.pi2025.core.models.interfaces.IGrafo;
import com.senaifatesg.pi2025.core.models.interfaces.IVertice;

@Component
public class JGraphTAdapter implements IGrafo {

	
	private final Graph<String, DefaultWeightedEdge> grafo;
	
	public JGraphTAdapter() {
        this.grafo = new DefaultDirectedWeightedGraph<>(DefaultWeightedEdge.class);
    }
	
	@Override
	public void adicionarVertice(IVertice vertice) {
		this.grafo.addVertex(vertice.verticeId().toString());
		
	}

	@Override
	public void adicionarAresta(IAresta aresta) {
		DefaultWeightedEdge e = this.grafo.addEdge(aresta.retornaV1().verticeId().toString(), aresta.retornaV2().verticeId().toString());
		grafo.setEdgeWeight(e, aresta.retornaDistancia().doubleValue());
	}

	@Override
	public Map<Double,List<String>> calcularRotaMaisCurta(IVertice origem, IVertice destino) {
		 DijkstraShortestPath<String, DefaultWeightedEdge> dijkstra = 
		            new DijkstraShortestPath<>(this.grafo);
		        
        GraphPath<String, DefaultWeightedEdge> path = 
            dijkstra.getPath(origem.verticeId().toString(), destino.verticeId().toString());
        
        List<String> listaVertices = path != null ? path.getVertexList() : Collections.emptyList();
        Double peso = path != null ? path.getWeight(): 0.0;
        Map<Double, List<String>> resultado = new HashMap<>();
        resultado.put(peso, listaVertices);
        return resultado;
	}

	@Override
	public void removerTodosVertices() {
        Set<String> vertices = new HashSet<>(this.grafo.vertexSet());
        vertices.forEach(this.grafo::removeVertex);
	}

	@Override
	public void removerTodasArestas() {
        Set<DefaultWeightedEdge> arestas = new HashSet<>(this.grafo.edgeSet());
        arestas.forEach(this.grafo::removeEdge);
	}
	



}
