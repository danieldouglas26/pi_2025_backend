package com.senaifatesg.pi2025.core.models.interfaces;

import java.util.List;
import java.util.Map;

public interface IGrafo {
    void adicionarVertice(IVertice vertice);
    void adicionarAresta(IAresta aresta);
    void removerTodosVertices();
    void removerTodasArestas();
    Map<Double ,List<String>> calcularRotaMaisCurta(IVertice origem, IVertice destino);
}
