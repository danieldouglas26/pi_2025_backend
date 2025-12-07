package com.lixo.gerenciamento.model.interfaces;

import java.util.List;
import java.util.Map;

public interface Grafo {
    void adicionarVertice(Vertice vertice);
    void adicionarAresta(Aresta aresta);
    void removerTodosVertices();
    void removerTodasArestas();
    Map<Double ,List<String>> calcularRotaMaisCurta(Vertice origem, Vertice destino);
}