package com.lixo.gerenciamento.model.entity;

import com.lixo.gerenciamento.model.interfaces.Aresta;
import com.lixo.gerenciamento.model.interfaces.Builder;
import com.lixo.gerenciamento.model.interfaces.Vertice;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;

@Entity
@Table(name = "rua")
public class Rua implements Aresta {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "origem_id", nullable = false)
    private Bairro origem;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "destino_id", nullable = false)
    private Bairro destino;
    
    @Column(nullable = false)
    @Min(0)
    private Double distancia;
    

    public Rua() {
    }
    

    public Rua(Long id, Bairro origem, Bairro destino, Double distancia) {
        this.id = id;
        this.origem = origem;
        this.destino = destino;
        this.distancia = distancia;
    }
    

    private Rua(RuaBuilder builder) {
        this.id = builder.id;
        this.origem = builder.origem;
        this.destino = builder.destino;
        this.distancia = builder.distancia;
    }
    
    // Getters e Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Bairro getOrigem() {
        return origem;
    }
    
    public void setOrigem(Bairro origem) {
        this.origem = origem;
    }
    
    public Bairro getDestino() {
        return destino;
    }
    
    public void setDestino(Bairro destino) {
        this.destino = destino;
    }
    
    public Double getDistancia() {
        return distancia;
    }
    
    public void setDistancia(Double distancia) {
        this.distancia = distancia;
    }
    

    @Override
    public Vertice retornaV1() {
        return origem;
    }
    
    @Override
    public Vertice retornaV2() {
        return destino;
    }
    
    @Override
    public Double retornaDistancia() {
        return distancia;
    }
    

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        Rua rua = (Rua) o;
        
        if (id != null ? !id.equals(rua.id) : rua.id != null) return false;
        if (origem != null ? !origem.equals(rua.origem) : rua.origem != null) return false;
        if (destino != null ? !destino.equals(rua.destino) : rua.destino != null) return false;
        return distancia != null ? distancia.equals(rua.distancia) : rua.distancia == null;
    }
    
    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (origem != null ? origem.hashCode() : 0);
        result = 31 * result + (destino != null ? destino.hashCode() : 0);
        result = 31 * result + (distancia != null ? distancia.hashCode() : 0);
        return result;
    }
    

    @Override
    public String toString() {
        return "Rua{" +
                "id=" + id +
                ", origem=" + (origem != null ? origem.getId() : "null") +
                ", destino=" + (destino != null ? destino.getId() : "null") +
                ", distancia=" + distancia +
                '}';
    }
    

    public static RuaBuilder builder() {
        return new RuaBuilder();
    }
    
    public static class RuaBuilder implements Builder<Rua> {
        private Long id;
        private Bairro origem;
        private Bairro destino;
        private Double distancia;
        
        private RuaBuilder() {

        }
        
        public RuaBuilder id(Long id) {
            this.id = id;
            return this;
        }
        
        public RuaBuilder origem(Bairro origem) {
            this.origem = origem;
            return this;
        }
        
        public RuaBuilder destino(Bairro destino) {
            this.destino = destino;
            return this;
        }
        
        public RuaBuilder distancia(Double distancia) {
            this.distancia = distancia;
            return this;
        }
        
        @Override
        public Rua build() {
            if (origem == null) {
                throw new IllegalArgumentException("Origem não pode ser nula");
            }
            if (destino == null) {
                throw new IllegalArgumentException("Destino não pode ser nulo");
            }
            if (distancia == null) {
                throw new IllegalArgumentException("Distância não pode ser nula");
            }
            if (distancia < 0) {
                throw new IllegalArgumentException("Distância não pode ser negativa");
            }
            if (origem.equals(destino)) {
                throw new IllegalArgumentException("Origem e destino não podem ser o mesmo bairro");
            }
            
            return new Rua(this);
        }
    }
}