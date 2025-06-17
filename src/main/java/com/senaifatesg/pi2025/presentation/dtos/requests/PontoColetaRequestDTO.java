// src/main/java/com/senaifatesg/pi2025/presentation/dtos/requests/PontoColetaRequestDTO.java
package com.senaifatesg.pi2025.presentation.dtos.requests;

import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PontoColetaRequestDTO {

    @NotBlank(message = "O nome do ponto de coleta é obrigatório")
    @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres")
    private String nome;

    @NotNull(message = "O ID do bairro é obrigatório")
    private Long idBairro;

    @NotBlank(message = "O nome do responsável é obrigatório")
    @Size(max = 100, message = "O nome do responsável deve ter no máximo 100 caracteres")
    private String nomeResponsavel;

    @NotBlank(message = "O email é obrigatório")
    @Email(message = "Informe um formato de email válido")
    private String email;

    @NotBlank(message = "O telefone é obrigatório")
    @Pattern(regexp = "^\\+?[\\d\\s()-]{10,}$", message = "Informe um telefone válido")
    private String telefone;

    @NotBlank(message = "O endereço é obrigatório")
    @Size(max = 200, message = "O endereço deve ter no máximo 200 caracteres")
    private String endereco;

    private String horarioFuncionamento;

    @NotNull(message = "Pelo menos um tipo de resíduo deve ser informado")
    @Size(min = 1, message = "Deve haver pelo menos um tipo de resíduo")
    private List<String> tiposDeResiduo; 
}