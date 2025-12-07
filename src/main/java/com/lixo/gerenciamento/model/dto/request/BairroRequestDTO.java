package com.lixo.gerenciamento.model.dto.request;

import jakarta.validation.constraints.NotBlank;

public class BairroRequestDTO {
    
    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    public BairroRequestDTO() {
    }

    public BairroRequestDTO(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        BairroRequestDTO that = (BairroRequestDTO) o;
        return nome != null ? nome.equals(that.nome) : that.nome == null;
    }

    @Override
    public int hashCode() {
        return nome != null ? nome.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "BairroRequestDTO{" +
                "nome='" + nome + '\'' +
                '}';
    }

}
