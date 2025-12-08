package com.lixo.gerenciamento.service; 

import org.springframework.data.jpa.domain.Specification;
import com.lixo.gerenciamento.model.entity.PontoColeta;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleQueryParser {

    // 1. ANALISADOR LÉXICO (Tokenizer)
    // Separa a string: 'residuo.tipo = "plastico"' -> [residuo.tipo, =, plastico]
    private List<String> tokenizar(String consulta) {
        List<String> tokens = new ArrayList<>();
        // Regex pega: palavras, operadores, ou strings entre aspas
        Pattern pattern = Pattern.compile("([a-zA-Z.]+)|(=|!=|LIKE)|(\"[^\"]*\")|(AND|OR)");
        Matcher matcher = pattern.matcher(consulta);
        
        while (matcher.find()) {
            tokens.add(matcher.group().replace("\"", "")); // Remove aspas
        }
        return tokens;
    }

    // 2. ANALISADOR SINTÁTICO E SEMÂNTICO (Parser)
    // Lê os tokens e valida se faz sentido, criando a Specification do JPA
    public Specification<PontoColeta> parse(String consulta) {
        List<String> tokens = tokenizar(consulta);
        Specification<PontoColeta> spec = Specification.where(null);
        
        int i = 0;
        while (i < tokens.size()) {
            // Espera-se: CAMPO OPERADOR VALOR
            if (i + 2 >= tokens.size()) {
                throw new IllegalArgumentException("Erro de Sintaxe: Comando incompleto");
            }

            String campo = tokens.get(i++);
            String operador = tokens.get(i++);
            String valor = tokens.get(i++);

            // Cria o filtro (Semântico)
            Specification<PontoColeta> novaRegra = criarRegra(campo, operador, valor);
            
            // Verifica se tem conectivo lógico (AND/OR)
            if (i < tokens.size()) {
                String conectivo = tokens.get(i++);
                if ("OR".equalsIgnoreCase(conectivo)) {
                    spec = spec.or(novaRegra);
                } else { // Assume AND por padrão
                    spec = spec.and(novaRegra);
                }
            } else {
                spec = spec.and(novaRegra);
            }
        }
        return spec;
    }

    // Tradução para o Banco de Dados
    private Specification<PontoColeta> criarRegra(String campo, String operador, String valor) {
        return (root, query, criteriaBuilder) -> {
            // Mapeamento simples de campos (Exemplo)
            if (campo.equals("residuo.tipo")) {
                // Join se necessário, ou filtro direto. Exemplo simplificado:
                // Supondo que queira filtrar por string dentro de uma lista ou campo simples
                return criteriaBuilder.like(root.get("tiposResiduoAceitosLegacy"), "%" + valor + "%");
            } else if (campo.equals("coleta.setor")) {
                // Acessando relacionamento Bairro -> Nome
                return criteriaBuilder.equal(root.get("bairro").get("nome"), valor);
            }
            return null;
        };
    }
}