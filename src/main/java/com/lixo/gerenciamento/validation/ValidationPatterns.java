package com.lixo.gerenciamento.validation;

public final class ValidationPatterns {
    
    // Padrão de placa (Mercosul: AAA1A11)
    public static final String PLACA_VEICULO = "^[A-Z]{3}[0-9][A-Z0-9][0-9]{2}$";
    
    // Padrão de CPF (formato: 000.000.000-00)
    public static final String CPF_FORMAT = "^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$";
    
    // Padrão de email
    public static final String EMAIL = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    
    // Padrão para nomes (apenas letras, acentuadas e espaços)
    public static final String NOME_PESSOA = "^[A-Za-zÀ-ÿ\\s]{2,100}$";
    
    // Padrão para telefone brasileiro
    public static final String TELEFONE = "^(\\(\\d{2}\\)\\s?)?\\d{4,5}-\\d{4}$";
    
    private ValidationPatterns() {
        throw new AssertionError("Não é possível instanciar esta classe");
    }
}