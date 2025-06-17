package com.senaifatesg.pi2025.presentation.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeneralErrorDTO {
    private String field;
    private String message;
}