package com.lixo.gerenciamento.validation.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = PlacaValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface PlacaValida {
    String message() default "Placa de veículo inválida. Formato esperado: AAA1A11 (Mercosul)";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}