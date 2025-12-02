package com.lixo.gerenciamento.model.entity;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "bairros")
public class Bairro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String nome;
    
    @Column(name = "tem_ponto_coleta")
    private Boolean temPontoColeta;
    
    public Bairro() {}
    
    public Bairro(Long id, String nome, Boolean temPontoColeta) {
        this.id = id;
        this.nome = nome;
        this.temPontoColeta = temPontoColeta;
    }
    
    public static BairroBuilder builder() {
        return new BairroBuilder();
    }
    
    public static class BairroBuilder {
        private Long id;
        private String nome;
        private Boolean temPontoColeta;
        
        public BairroBuilder id(Long id) { this.id = id; return this; }
        public BairroBuilder nome(String nome) { this.nome = nome; return this; }
        public BairroBuilder temPontoColeta(Boolean temPontoColeta) { this.temPontoColeta = temPontoColeta; return this; }
        
        public Bairro build() {
            return new Bairro(id, nome, temPontoColeta);
        }
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public Boolean getTemPontoColeta() { return temPontoColeta; }
    public void setTemPontoColeta(Boolean temPontoColeta) { this.temPontoColeta = temPontoColeta; }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Bairro)) return false;
        Bairro bairro = (Bairro) o;
        return Objects.equals(id, bairro.id) && Objects.equals(nome, bairro.nome);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, nome);
    }
    
    @Override
    public String toString() {
        return "Bairro{id=" + id + ", nome='" + nome + "', temPontoColeta=" + temPontoColeta + '}';
    }
}