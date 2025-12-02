package com.lixo.gerenciamento.model.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "caminhoes")
public class Caminhao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String placa;
    
    @Column(nullable = false)
    private String motorista;
    
    @Column(name = "capacidade_maxima", nullable = false)
    private Double capacidadeMaxima;
    
    @ElementCollection
    @CollectionTable(name = "caminhao_tipos_residuo", 
                    joinColumns = @JoinColumn(name = "caminhao_id"))
    @Column(name = "tipo_residuo")
    private List<String> tiposResiduos = new ArrayList<>();
    
    public Caminhao() {}
    
    public Caminhao(Long id, String placa, String motorista, Double capacidadeMaxima, List<String> tiposResiduos) {
        this.id = id;
        this.placa = placa;
        this.motorista = motorista;
        this.capacidadeMaxima = capacidadeMaxima;
        this.tiposResiduos = tiposResiduos != null ? tiposResiduos : new ArrayList<>();
    }
    
    public static CaminhaoBuilder builder() {
        return new CaminhaoBuilder();
    }
    
    public static class CaminhaoBuilder {
        private Long id;
        private String placa;
        private String motorista;
        private Double capacidadeMaxima;
        private List<String> tiposResiduos;
        
        public CaminhaoBuilder id(Long id) { this.id = id; return this; }
        public CaminhaoBuilder placa(String placa) { this.placa = placa; return this; }
        public CaminhaoBuilder motorista(String motorista) { this.motorista = motorista; return this; }
        public CaminhaoBuilder capacidadeMaxima(Double capacidadeMaxima) { 
            this.capacidadeMaxima = capacidadeMaxima; return this; 
        }
        public CaminhaoBuilder tiposResiduos(List<String> tiposResiduos) { 
            this.tiposResiduos = tiposResiduos; return this; 
        }
        
        public Caminhao build() {
            return new Caminhao(id, placa, motorista, capacidadeMaxima, tiposResiduos);
        }
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }
    public String getMotorista() { return motorista; }
    public void setMotorista(String motorista) { this.motorista = motorista; }
    public Double getCapacidadeMaxima() { return capacidadeMaxima; }
    public void setCapacidadeMaxima(Double capacidadeMaxima) { this.capacidadeMaxima = capacidadeMaxima; }
    public List<String> getTiposResiduos() { return tiposResiduos; }
    public void setTiposResiduos(List<String> tiposResiduos) { this.tiposResiduos = tiposResiduos; }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Caminhao)) return false;
        Caminhao caminhao = (Caminhao) o;
        return Objects.equals(id, caminhao.id) && Objects.equals(placa, caminhao.placa);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, placa);
    }
    
    @Override
    public String toString() {
        return "Caminhao{id=" + id + ", placa='" + placa + "', motorista='" + motorista + 
               "', capacidade=" + capacidadeMaxima + ", residuos=" + tiposResiduos + '}';
    }
}