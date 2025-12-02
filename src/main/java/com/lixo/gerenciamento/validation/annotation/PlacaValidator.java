package com.lixo.gerenciamento.validation.annotation;

import org.springframework.beans.factory.annotation.Autowired;

import com.lixo.gerenciamento.service.ValidationService;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PlacaValidator implements ConstraintValidator<PlacaValida, String> {
    
    @Autowired
    private ValidationService validationService;
    
    @Override
    public void initialize(PlacaValida constraintAnnotation) {
        // Inicialização, se necessário
    }
    
    @Override
    public boolean isValid(String placa, ConstraintValidatorContext context) {
        if (placa == null) {
            return true; // @NotNull deve ser usado para validação de null
        }
        return validationService.validarPlaca(placa);
    }
}
