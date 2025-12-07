package com.lixo.gerenciamento.model.dto.response;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.lixo.gerenciamento.model.entity.Bairro;

public class BairroResponseDTO {
    private Long id;
    private String nome;

    public BairroResponseDTO() {
    }

    public BairroResponseDTO(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public BairroResponseDTO(String nome) {
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        
        BairroResponseDTO that = (BairroResponseDTO) o;
        
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        return nome != null ? nome.equals(that.nome) : that.nome == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (nome != null ? nome.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BairroResponseDTO{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                '}';
    }

    // Builder Pattern
    public static BairroResponseDTOBuilder builder() {
        return new BairroResponseDTOBuilder();
    }

    public static class BairroResponseDTOBuilder {
        private Long id;
        private String nome;

        private BairroResponseDTOBuilder() {
        }

        public BairroResponseDTOBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public BairroResponseDTOBuilder nome(String nome) {
            this.nome = nome;
            return this;
        }

        public BairroResponseDTO build() {
            return new BairroResponseDTO(id, nome);
        }
    }

    public static BairroResponseDTO of(Long id, String nome) {
        return new BairroResponseDTO(id, nome);
    }

    public static BairroResponseDTO of(String nome) {
        return new BairroResponseDTO(nome);
    }

    public static BairroResponseDTO fromEntity(Bairro bairro) {
        if (bairro == null) {
            return null;
        }
        return new BairroResponseDTO(bairro.getId(), bairro.getNome());
    }

    public static List<BairroResponseDTO> fromEntities(List<Bairro> bairros) {
        if (bairros == null) {
            return Collections.emptyList();
        }
        return bairros.stream()
                .map(BairroResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }
}
