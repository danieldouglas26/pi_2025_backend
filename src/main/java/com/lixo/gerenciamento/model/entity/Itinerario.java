package com.lixo.gerenciamento.model.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "itinerarios")
public class Itinerario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "rota_id", nullable = false)
    private Rota rota;
    
    @Column(nullable = false)
    private LocalDate data;
    
    @Column(name = "hora_inicio")
    private LocalTime horaInicio;
    
    @Column(name = "hora_fim")
    private LocalTime horaFim;
    
    @Enumerated(EnumType.STRING)
    private StatusItinerario status;
    
    @Column(name = "distancia_percorrida")
    private Double distanciaPercorrida;
    
    @Column(name = "tempo_realizado")
    private Integer tempoRealizado;
    
    private String observacoes;
    
    public enum StatusItinerario {
        AGENDADO, EM_ANDAMENTO, CONCLUIDO, CANCELADO
    }
    
    public Itinerario() {}
    
    public Itinerario(Long id, Rota rota, LocalDate data, LocalTime horaInicio, 
                     LocalTime horaFim, StatusItinerario status, Double distanciaPercorrida,
                     Integer tempoRealizado, String observacoes) {
        this.id = id;
        this.rota = rota;
        this.data = data;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
        this.status = status;
        this.distanciaPercorrida = distanciaPercorrida;
        this.tempoRealizado = tempoRealizado;
        this.observacoes = observacoes;
    }
    
    public static ItinerarioBuilder builder() {
        return new ItinerarioBuilder();
    }
    
    public static class ItinerarioBuilder {
        private Long id;
        private Rota rota;
        private LocalDate data;
        private LocalTime horaInicio;
        private LocalTime horaFim;
        private StatusItinerario status;
        private Double distanciaPercorrida;
        private Integer tempoRealizado;
        private String observacoes;
        
        public ItinerarioBuilder id(Long id) { this.id = id; return this; }
        public ItinerarioBuilder rota(Rota rota) { this.rota = rota; return this; }
        public ItinerarioBuilder data(LocalDate data) { this.data = data; return this; }
        public ItinerarioBuilder horaInicio(LocalTime horaInicio) { this.horaInicio = horaInicio; return this; }
        public ItinerarioBuilder horaFim(LocalTime horaFim) { this.horaFim = horaFim; return this; }
        public ItinerarioBuilder status(StatusItinerario status) { this.status = status; return this; }
        public ItinerarioBuilder distanciaPercorrida(Double distanciaPercorrida) { this.distanciaPercorrida = distanciaPercorrida; return this; }
        public ItinerarioBuilder tempoRealizado(Integer tempoRealizado) { this.tempoRealizado = tempoRealizado; return this; }
        public ItinerarioBuilder observacoes(String observacoes) { this.observacoes = observacoes; return this; }
        
        public Itinerario build() {
            return new Itinerario(id, rota, data, horaInicio, horaFim, status,
                                distanciaPercorrida, tempoRealizado, observacoes);
        }
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Rota getRota() { return rota; }
    public void setRota(Rota rota) { this.rota = rota; }
    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }
    public LocalTime getHoraInicio() { return horaInicio; }
    public void setHoraInicio(LocalTime horaInicio) { this.horaInicio = horaInicio; }
    public LocalTime getHoraFim() { return horaFim; }
    public void setHoraFim(LocalTime horaFim) { this.horaFim = horaFim; }
    public StatusItinerario getStatus() { return status; }
    public void setStatus(StatusItinerario status) { this.status = status; }
    public Double getDistanciaPercorrida() { return distanciaPercorrida; }
    public void setDistanciaPercorrida(Double distanciaPercorrida) { this.distanciaPercorrida = distanciaPercorrida; }
    public Integer getTempoRealizado() { return tempoRealizado; }
    public void setTempoRealizado(Integer tempoRealizado) { this.tempoRealizado = tempoRealizado; }
    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Itinerario)) return false;
        Itinerario that = (Itinerario) o;
        return Objects.equals(id, that.id) && Objects.equals(rota, that.rota) && Objects.equals(data, that.data);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, rota, data);
    }
    
    @Override
    public String toString() {
        return "Itinerario{id=" + id + ", data=" + data + ", status=" + status + '}';
    }
}