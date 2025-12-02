package com.lixo.gerenciamento.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lixo.gerenciamento.exception.ConsultaSyntaxException;

@Service
public class ConsultaService {
    
    @Autowired
    private CaminhaoService caminhaoService;
    
    @Autowired
    private PontoColetaService pontoColetaService;
    
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
    
    public List<Map<String, Object>> executarConsultaAvancada(String consulta) {
        try {
            List<CondicaoConsulta> condicoes = parseConsulta(consulta);
            return executarConsulta(condicoes);
        } catch (ConsultaSyntaxException e) {
            throw e;
        } catch (Exception e) {
            throw new ConsultaSyntaxException("Erro ao executar consulta: " + e.getMessage(), e);
        }
    }
    
    private List<CondicaoConsulta> parseConsulta(String consulta) {
        if (consulta == null || consulta.trim().isEmpty()) {
            throw new ConsultaSyntaxException("Consulta não pode estar vazia");
        }
        
        List<Token> tokens = analiseLexica(consulta);
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
        if ("AND".equalsIgnoreCase(token)) return TokenType.OPERADOR_LOGICO;
        if ("OR".equalsIgnoreCase(token)) return TokenType.OPERADOR_LOGICO;
        
        throw new ConsultaSyntaxException("Token inválido: '" + token + "'");
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
                throw new ConsultaSyntaxException("Campo esperado na posição " + i);
            }
            
            // Operador
            if (i < tokens.size() && tokens.get(i).tipo == TokenType.OPERADOR) {
                condicao.setOperador(tokens.get(i).valor);
                i++;
            } else {
                throw new ConsultaSyntaxException("Operador esperado na posição " + i);
            }
            
            // Valor
            if (i < tokens.size() && (tokens.get(i).tipo == TokenType.STRING || 
                                     tokens.get(i).tipo == TokenType.NUMERO || 
                                     tokens.get(i).tipo == TokenType.DATA)) {
                condicao.setValor(tokens.get(i).valor.replace("\"", ""));
                i++;
            } else {
                throw new ConsultaSyntaxException("Valor esperado na posição " + i);
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
    
    private List<Map<String, Object>> executarConsulta(List<CondicaoConsulta> condicoes) {
        List<Map<String, Object>> resultados = new ArrayList<>();
        
        // Exemplo de implementação - expandir conforme necessidades
        for (CondicaoConsulta condicao : condicoes) {
            if ("residuo.tipo".equals(condicao.getCampo()) && "=".equals(condicao.getOperador())) {
                // Consultar pontos de coleta por tipo de resíduo
                var pontos = pontoColetaService.findByTipoResiduo(condicao.getValor());
                for (var ponto : pontos) {
                    Map<String, Object> resultado = new HashMap<>();
                    resultado.put("tipo", "PONTO_COLETA");
                    resultado.put("nome", ponto.getNome());
                    resultado.put("responsavel", ponto.getResponsavel());
                    resultado.put("bairro", ponto.getBairro().getNome());
                    resultado.put("tipos_residuos", ponto.getTiposResiduos());
                    resultados.add(resultado);
                }
            }
        }
        
        return resultados;
    }
    
    public List<Map<String, Object>> consultarPontosPorTipoResiduo(String tipo) {
        List<Map<String, Object>> resultados = new ArrayList<>();
        var pontos = pontoColetaService.findByTipoResiduo(tipo);
        
        for (var ponto : pontos) {
            Map<String, Object> resultado = new HashMap<>();
            resultado.put("id", ponto.getId());
            resultado.put("nome", ponto.getNome());
            resultado.put("responsavel", ponto.getResponsavel());
            resultado.put("bairro", ponto.getBairro().getNome());
            resultado.put("endereco", ponto.getEndereco());
            resultado.put("tipos_residuos", ponto.getTiposResiduos());
            resultado.put("horario_funcionamento", ponto.getHorarioFuncionamento());
            resultados.add(resultado);
        }
        
        return resultados;
    }
    
    public List<Map<String, Object>> consultarCaminhoesDisponiveis(String data, String tipoResiduo) {
        List<Map<String, Object>> resultados = new ArrayList<>();
        var caminhoes = caminhaoService.findAll();
        
        for (var caminhao : caminhoes) {
            Map<String, Object> resultado = new HashMap<>();
            resultado.put("id", caminhao.getId());
            resultado.put("placa", caminhao.getPlaca());
            resultado.put("motorista", caminhao.getMotorista());
            resultado.put("capacidade_maxima", caminhao.getCapacidadeMaxima());
            resultado.put("tipos_residuos", caminhao.getTiposResiduos());
            resultado.put("disponivel", true); // Implementar lógica de disponibilidade
            resultados.add(resultado);
        }
        
        return resultados;
    }
    
    public Map<String, Object> obterEstatisticasRotas() {
        Map<String, Object> estatisticas = new HashMap<>();
        // Implementar estatísticas
        estatisticas.put("total_rotas", 0);
        estatisticas.put("distancia_media", 0);
        estatisticas.put("rotas_ativas", 0);
        return estatisticas;
    }
    
    public Map<String, Long> obterContagemItinerariosPorStatus() {
        Map<String, Long> contagem = new HashMap<>();
        // Implementar contagem
        contagem.put("AGENDADO", 0L);
        contagem.put("EM_ANDAMENTO", 0L);
        contagem.put("CONCLUIDO", 0L);
        contagem.put("CANCELADO", 0L);
        return contagem;
    }
    
    // Classes internas para parsing
    private static class CondicaoConsulta {
        private String campo;
        private String operador;
        private String valor;
        private String operadorLogico;
        
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
