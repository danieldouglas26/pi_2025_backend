package com.lixo.gerenciamento.service;

import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.lixo.gerenciamento.validation.ValidationPatterns;

@Service
public class ValidationService {
    
    private final Pattern placaPattern = Pattern.compile(ValidationPatterns.PLACA_VEICULO);
    private final Pattern cpfPattern = Pattern.compile(ValidationPatterns.CPF_FORMAT);
    private final Pattern emailPattern = Pattern.compile(ValidationPatterns.EMAIL);
    private final Pattern nomePattern = Pattern.compile(ValidationPatterns.NOME_PESSOA);
    private final Pattern telefonePattern = Pattern.compile(ValidationPatterns.TELEFONE);
    
    public boolean validarPlaca(String placa) {
        if (placa == null || placa.trim().isEmpty()) {
            return false;
        }
        
        String placaNormalizada = placa.toUpperCase().replaceAll("[^A-Z0-9]", "");
        
        // Primeiro valida com regex
        if (!placaPattern.matcher(placaNormalizada).matches()) {
            return false;
        }
        
        // Depois valida com autômato finito
        return validarPlacaAutomato(placaNormalizada);
    }
    
    private boolean validarPlacaAutomato(String placa) {
        if (placa.length() != 7) return false;
        
        // Estado S0-S2: Verificar 3 letras
        for (int i = 0; i < 3; i++) {
            if (!Character.isLetter(placa.charAt(i))) return false;
        }
        
        // Estado S3: Verificar 1 número
        if (!Character.isDigit(placa.charAt(3))) return false;
        
        // Estado S4: Verificar 1 letra ou número
        char char4 = placa.charAt(4);
        if (!Character.isLetterOrDigit(char4)) return false;
        
        // Estado S5 e S6: Verificar 2 números
        for (int i = 5; i < 7; i++) {
            if (!Character.isDigit(placa.charAt(i))) return false;
        }
        
        return true; // Estado ACEITA
    }
    
    public boolean validarEmail(String email) {
        return email != null && emailPattern.matcher(email).matches();
    }
    
    public boolean validarCPF(String cpf) {
        if (cpf == null || !cpfPattern.matcher(cpf).matches()) {
            return false;
        }
        return validarDigitosVerificadoresCPF(cpf);
    }
    
    public boolean validarNome(String nome) {
        return nome != null && nomePattern.matcher(nome.trim()).matches();
    }
    
    public boolean validarTelefone(String telefone) {
        return telefone != null && telefonePattern.matcher(telefone).matches();
    }
    
    public boolean validarTipoResiduo(String tipoResiduo) {
        if (tipoResiduo == null) return false;
        
        String[] tiposValidos = {"PLASTICO", "PAPEL", "METAL", "ORGANICO", "VIDRO"};
        for (String tipoValido : tiposValidos) {
            if (tipoValido.equalsIgnoreCase(tipoResiduo.trim())) {
                return true;
            }
        }
        return false;
    }
    
    private boolean validarDigitosVerificadoresCPF(String cpf) {
        String cpfNumerico = cpf.replaceAll("[^0-9]", "");
        
        if (cpfNumerico.length() != 11 || cpfNumerico.chars().distinct().count() == 1) {
            return false;
        }
        
        try {
            int[] digitos = new int[11];
            for (int i = 0; i < 11; i++) {
                digitos[i] = Integer.parseInt(cpfNumerico.substring(i, i + 1));
            }
            
            // Cálculo do primeiro dígito verificador
            int soma = 0;
            for (int i = 0; i < 9; i++) {
                soma += digitos[i] * (10 - i);
            }
            int resto = soma % 11;
            int digito1 = resto < 2 ? 0 : 11 - resto;
            
            if (digito1 != digitos[9]) return false;
            
            // Cálculo do segundo dígito verificador
            soma = 0;
            for (int i = 0; i < 10; i++) {
                soma += digitos[i] * (11 - i);
            }
            resto = soma % 11;
            int digito2 = resto < 2 ? 0 : 11 - resto;
            
            return digito2 == digitos[10];
            
        } catch (NumberFormatException e) {
            return false;
        }
    }
}