package com.lixo.gerenciamento.model.entity;

import com.lixo.gerenciamento.model.interfaces.Builder;
import com.lixo.gerenciamento.model.interfaces.Vertice;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "bairro")
public class Bairro implements Vertice {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String nome;

    public Bairro() {
    }

    public Bairro(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    private Bairro(BairroBuilder builder) {
        this.id = builder.id;
        this.nome = builder.nome;
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
    public Long verticeId() {
        return this.id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        Bairro bairro = (Bairro) o;
        
        if (id != null ? !id.equals(bairro.id) : bairro.id != null) return false;
        return nome != null ? nome.equals(bairro.nome) : bairro.nome == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (nome != null ? nome.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Bairro{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                '}';
    }

    public static BairroBuilder builder() {
        return new BairroBuilder();
    }

    public static class BairroBuilder implements Builder<Bairro> {
        private Long id;
        private String nome;

        private BairroBuilder() {

        }

        public BairroBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public BairroBuilder nome(String nome) {
            this.nome = nome;
            return this;
        }

        @Override
        public Bairro build() {
            return new Bairro(this);
        }
    }
}