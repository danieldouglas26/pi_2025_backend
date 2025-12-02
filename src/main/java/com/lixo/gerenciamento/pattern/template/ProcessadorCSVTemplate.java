package com.lixo.gerenciamento.pattern.template;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public abstract class ProcessadorCSVTemplate<T> {
    
    public final List<T> processarArquivo(Reader reader) throws IOException {
        validarArquivo(reader);
        List<String> linhas = lerLinhas(reader);
        if (linhas.isEmpty()) {
            throw new IllegalArgumentException("Arquivo CSV vazio");
        }
        validarCabecalho(linhas.get(0));
        return processarDados(linhas.subList(1, linhas.size()));
    }
    
    protected void validarArquivo(Reader reader) {
        if (reader == null) {
            throw new IllegalArgumentException("Reader não pode ser nulo");
        }
    }
    
    protected List<String> lerLinhas(Reader reader) throws IOException {
        List<String> linhas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(reader)) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (!linha.trim().isEmpty()) {
                    linhas.add(linha);
                }
            }
        }
        return linhas;
    }
    
    protected abstract void validarCabecalho(String cabecalho);
    protected abstract List<T> processarDados(List<String> linhas);
}
