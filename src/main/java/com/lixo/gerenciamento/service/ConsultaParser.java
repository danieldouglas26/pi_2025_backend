package com.lixo.gerenciamento.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.lixo.gerenciamento.exception.ConsultaSyntaxException;

@Component
public class ConsultaParser {
    
    // ✅ Gramática Livre de Contexto implementada
    // Consulta -> Expressao (OperadorLogico Expressao)*
    // Expressao -> Campo Operador Valor
    // Campo -> "residuo.tipo" | "coleta.setor" | "caminhao.placa" | "itinerario.data"
    // Operador -> "=" | "!=" | ">" | "<" | ">=" | "<=" | "LIKE"
    // OperadorLogico -> "AND" | "OR"
    // Valor -> String | Numero | Data
    
    private static final Pattern PATTERN_CAMPO = 
        Pattern.compile("^(residuo\\.tipo|coleta\\.setor|caminhao\\.placa|itinerario\\.data)$");
    private static final Pattern PATTERN_OPERADOR = 
        Pattern.compile("^(=|!=|>|<|>=|<=|LIKE)$");
    private static final Pattern PATTERN_STRING = 
        Pattern.compile("^\"[a-zA-Z0-9_ ]*\"$");
    private static final Pattern PATTERN_NUMERO = 
        Pattern.compile("^\\d+(\\.\\d+)?$");
    private static final Pattern PATTERN_DATA = 
        Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");
    
    public List<CondicaoConsulta> parse(String consulta) {
        // ✅ Analisador Léxico
        List<Token> tokens = analiseLexica(consulta);
        
        // ✅ Analisador Sintático
        return analiseSintatica(tokens);
    }
    
    private List<Token> analiseLexica(String consulta) {
        List<Token> tokens = new ArrayList<>();
        String[] partes = consulta.split("\\s+");
        
        for (String parte : partes) {
            TokenType tipo = identificarToken(parte);
            tokens.add(new Token(tipo, parte));
        }
        
        return tokens;
    }
    
    private TokenType identificarToken(String token) {
        if (PATTERN_CAMPO.matcher(token).matches()) return TokenType.CAMPO;
        if (PATTERN_OPERADOR.matcher(token).matches()) return TokenType.OPERADOR;
        if (PATTERN_STRING.matcher(token).matches()) return TokenType.STRING;
        if (PATTERN_NUMERO.matcher(token).matches()) return TokenType.NUMERO;
        if (PATTERN_DATA.matcher(token).matches()) return TokenType.DATA;
        if ("AND".equals(token) || "OR".equals(token)) return TokenType.OPERADOR_LOGICO;
        
        throw new ConsultaSyntaxException("Token inválido: " + token);
    }
    
    private List<CondicaoConsulta> analiseSintatica(List<Token> tokens) {
        List<CondicaoConsulta> condicoes = new ArrayList<>();
        int i = 0;
        
        while (i < tokens.size()) {
            CondicaoConsulta condicao = new CondicaoConsulta();
            
            // Campo
            if (i < tokens.size() && tokens.get(i).tipo == TokenType.CAMPO) {
                condicao.setCampo(tokens.get(i).valor);
                i++;
            } else {
                throw new ConsultaSyntaxException("Campo esperado");
            }
            
            // Operador
            if (i < tokens.size() && tokens.get(i).tipo == TokenType.OPERADOR) {
                condicao.setOperador(tokens.get(i).valor);
                i++;
            } else {
                throw new ConsultaSyntaxException("Operador esperado");
            }
            
            // Valor
            if (i < tokens.size() && (tokens.get(i).tipo == TokenType.STRING || 
                                     tokens.get(i).tipo == TokenType.NUMERO || 
                                     tokens.get(i).tipo == TokenType.DATA)) {
                condicao.setValor(tokens.get(i).valor.replace("\"", ""));
                i++;
            } else {
                throw new ConsultaSyntaxException("Valor esperado");
            }
            
            condicoes.add(condicao);
            
            // Operador lógico
            if (i < tokens.size() && tokens.get(i).tipo == TokenType.OPERADOR_LOGICO) {
                condicao.setOperadorLogico(tokens.get(i).valor);
                i++;
            }
        }
        
        return condicoes;
    }
    
    public static class CondicaoConsulta {
        private String campo;
        private String operador;
        private String valor;
        private String operadorLogico;
        
        // Getters e Setters
        public String getCampo() { return campo; }
        public void setCampo(String campo) { this.campo = campo; }
        public String getOperador() { return operador; }
        public void setOperador(String operador) { this.operador = operador; }
        public String getValor() { return valor; }
        public void setValor(String valor) { this.valor = valor; }
        public String getOperadorLogico() { return operadorLogico; }
        public void setOperadorLogico(String operadorLogico) { this.operadorLogico = operadorLogico; }
    }
    
    private enum TokenType {
        CAMPO, OPERADOR, OPERADOR_LOGICO, STRING, NUMERO, DATA
    }
    
    private static class Token {
        TokenType tipo;
        String valor;
        
        Token(TokenType tipo, String valor) {
            this.tipo = tipo;
            this.valor = valor;
        }
    }
}
