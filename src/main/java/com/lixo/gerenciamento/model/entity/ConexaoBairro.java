package com.lixo.gerenciamento.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "conexoes_bairro")
public class ConexaoBairro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "bairro_origem_id", nullable = false)
    private Bairro bairroOrigem;
    
    @ManyToOne
    @JoinColumn(name = "bairro_destino_id", nullable = false)
    private Bairro bairroDestino;
    
    @Column(nullable = false)
    private Double distancia;
    
    @Column(name = "tempo_estimado")
    private Integer tempoEstimado;
    
    public ConexaoBairro() {}
    
    public ConexaoBairro(Long id, Bairro bairroOrigem, Bairro bairroDestino, Double distancia, Integer tempoEstimado) {
        this.id = id;
        this.bairroOrigem = bairroOrigem;
        this.bairroDestino = bairroDestino;
        this.distancia = distancia;
        this.tempoEstimado = tempoEstimado;
    }
    
    public static ConexaoBairroBuilder builder() {
        return new ConexaoBairroBuilder();
    }
    
    public static class ConexaoBairroBuilder {
        private Long id;
        private Bairro bairroOrigem;
        private Bairro bairroDestino;
        private Double distancia;
        private Integer tempoEstimado;
        
        public ConexaoBairroBuilder id(Long id) { this.id = id; return this; }
        public ConexaoBairroBuilder bairroOrigem(Bairro bairroOrigem) { this.bairroOrigem = bairroOrigem; return this; }
        public ConexaoBairroBuilder bairroDestino(Bairro bairroDestino) { this.bairroDestino = bairroDestino; return this; }
        public ConexaoBairroBuilder distancia(Double distancia) { this.distancia = distancia; return this; }
        public ConexaoBairroBuilder tempoEstimado(Integer tempoEstimado) { this.tempoEstimado = tempoEstimado; return this; }
        
        public ConexaoBairro build() {
            return new ConexaoBairro(id, bairroOrigem, bairroDestino, distancia, tempoEstimado);
        }
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Bairro getBairroOrigem() { return bairroOrigem; }
    public void setBairroOrigem(Bairro bairroOrigem) { this.bairroOrigem = bairroOrigem; }
    public Bairro getBairroDestino() { return bairroDestino; }
    public void setBairroDestino(Bairro bairroDestino) { this.bairroDestino = bairroDestino; }
    public Double getDistancia() { return distancia; }
    public void setDistancia(Double distancia) { this.distancia = distancia; }
    public Integer getTempoEstimado() { return tempoEstimado; }
    public void setTempoEstimado(Integer tempoEstimado) { this.tempoEstimado = tempoEstimado; }
}