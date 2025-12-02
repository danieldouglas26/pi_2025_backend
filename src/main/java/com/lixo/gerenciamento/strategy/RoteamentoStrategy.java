package com.lixo.gerenciamento.strategy;

import java.util.List;

import com.lixo.gerenciamento.model.entity.PontoColeta;
import com.lixo.gerenciamento.model.entity.Rota;

public interface RoteamentoStrategy {
    

	Rota  calcularMelhorRota(PontoColeta origem, PontoColeta destino);
    

	Rota  calcularRotaOtimizada(List<PontoColeta> pontos, Long caminhaoId);
    

    String getNomeAlgoritmo();
    

    String getDescricaoAlgoritmo();
}
