package com.lixo.gerenciamento.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.lixo.gerenciamento.model.entity.Bairro;
import com.lixo.gerenciamento.model.entity.ConexaoBairro;
import com.lixo.gerenciamento.service.BairroService;
import com.lixo.gerenciamento.service.ConexaoBairroService;

@Component
public class DataLoader implements CommandLineRunner {
    
    @Autowired
    private BairroService bairroService;
    
    @Autowired
    private ConexaoBairroService conexaoBairroService;
    
    @Override
    public void run(String... args) throws Exception {
        carregarDadosIniciais();
    }
    
    private void carregarDadosIniciais() {
        // Carregar bairros básicos
        if (bairroService.findAll().isEmpty()) {
            List<Bairro> bairros = Arrays.asList(
                Bairro.builder().nome("Centro").temPontoColeta(true).build(),
                Bairro.builder().nome("Jardim das Flores").temPontoColeta(true).build(),
                Bairro.builder().nome("Setor Sul").temPontoColeta(true).build(),
                Bairro.builder().nome("Vila Nova").temPontoColeta(false).build(),
                Bairro.builder().nome("Alto da Colina").temPontoColeta(true).build()
            );
            bairroService.saveAll(bairros);
            System.out.println("✅ Bairros carregados: " + bairros.size());
        }
        
        // Carregar conexões básicas
        if (conexaoBairroService.findAll().isEmpty()) {
            Bairro centro = bairroService.findByNome("Centro").orElseThrow();
            Bairro jardim = bairroService.findByNome("Jardim das Flores").orElseThrow();
            Bairro setorSul = bairroService.findByNome("Setor Sul").orElseThrow();
            Bairro vilaNova = bairroService.findByNome("Vila Nova").orElseThrow();
            Bairro altoColina = bairroService.findByNome("Alto da Colina").orElseThrow();
            
            List<ConexaoBairro> conexoes = Arrays.asList(
                ConexaoBairro.builder().bairroOrigem(centro).bairroDestino(jardim).distancia(5.2).tempoEstimado(12).build(),
                ConexaoBairro.builder().bairroOrigem(centro).bairroDestino(setorSul).distancia(3.8).tempoEstimado(9).build(),
                ConexaoBairro.builder().bairroOrigem(jardim).bairroDestino(setorSul).distancia(4.5).tempoEstimado(11).build(),
                ConexaoBairro.builder().bairroOrigem(setorSul).bairroDestino(vilaNova).distancia(2.7).tempoEstimado(7).build(),
                ConexaoBairro.builder().bairroOrigem(jardim).bairroDestino(altoColina).distancia(6.1).tempoEstimado(15).build()
            );
            conexaoBairroService.saveAll(conexoes);
            System.out.println("✅ Conexões carregadas: " + conexoes.size());
        }
    }
}