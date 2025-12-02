package com.lixo.gerenciamento.exception;

public class ConsultaSyntaxException extends BusinessException {
    
    public ConsultaSyntaxException(String message) {
        super("Erro de sintaxe na consulta: " + message);
    }
    
    public ConsultaSyntaxException(String message, Throwable cause) {
        super("Erro de sintaxe na consulta: " + message, cause);
    }
}
