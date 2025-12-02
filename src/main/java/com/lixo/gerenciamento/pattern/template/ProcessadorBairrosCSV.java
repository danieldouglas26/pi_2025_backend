package com.lixo.gerenciamento.pattern.template;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.lixo.gerenciamento.model.entity.Bairro;

@Component
public class ProcessadorBairrosCSV extends ProcessadorCSVTemplate<Bairro> {
    
    @Override
    protected void validarCabecalho(String cabecalho) {
        String[] colunasEsperadas = {"id", "nome", "tem_ponto_coleta"};
        String[] colunasRecebidas = cabecalho.split(",");
        
        if (colunasRecebidas.length < colunasEsperadas.length) {
            throw new IllegalArgumentException(
                "Cabeçalho do CSV inválido. Esperado: " + 
                String.join(", ", colunasEsperadas) + 
                ". Recebido: " + cabecalho
            );
        }
    }
    
    @Override
    protected List<Bairro> processarDados(List<String> linhas) {
        List<Bairro> bairros = new ArrayList<>();
        
        for (int i = 0; i < linhas.size(); i++) {
            try {
                String linha = linhas.get(i);
                String[] dados = linha.split(",");
                
                if (dados.length >= 3) {
                    Long id = dados[0].trim().isEmpty() ? null : Long.parseLong(dados[0].trim());
                    String nome = dados[1].trim();
                    Boolean temPontoColeta = Boolean.parseBoolean(dados[2].trim());
                    
                    Bairro bairro = Bairro.builder()
                            .id(id)
                            .nome(nome)
                            .temPontoColeta(temPontoColeta)
                            .build();
                    bairros.add(bairro);
                }
            } catch (Exception e) {
                System.err.println("Erro ao processar linha " + (i + 1) + ": " + e.getMessage());
            }
        }
        
        return bairros;
    }
}
