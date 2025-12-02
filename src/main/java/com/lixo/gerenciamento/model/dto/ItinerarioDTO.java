package com.lixo.gerenciamento.model.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.lixo.gerenciamento.model.entity.Rota;

public class ItinerarioDTO {
    private Long id;
    private Rota rota;
    private LocalDate data;
    private LocalTime horaInicio;
    private LocalTime horaFim;
    private String status;
    private Double distanciaPercorrida;
    private Integer tempoRealizado;
    private String observacoes;
    
    public ItinerarioDTO() {}
    
    public ItinerarioDTO(Long id, Rota rota, LocalDate data, LocalTime horaInicio, 
                        LocalTime horaFim, String status, Double distanciaPercorrida,
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
    
    public static ItinerarioDTOBuilder builder() {
        return new ItinerarioDTOBuilder();
    }
    
    public static class ItinerarioDTOBuilder {
        private Long id;
        private Rota rota;
        private LocalDate data;
        private LocalTime horaInicio;
        private LocalTime horaFim;
        private String status;
        private Double distanciaPercorrida;
        private Integer tempoRealizado;
        private String observacoes;
        
        public ItinerarioDTOBuilder id(Long id) { this.id = id; return this; }
        public ItinerarioDTOBuilder rota(Rota rota) { this.rota = rota; return this; }
        public ItinerarioDTOBuilder data(LocalDate data) { this.data = data; return this; }
        public ItinerarioDTOBuilder horaInicio(LocalTime horaInicio) { this.horaInicio = horaInicio; return this; }
        public ItinerarioDTOBuilder horaFim(LocalTime horaFim) { this.horaFim = horaFim; return this; }
        public ItinerarioDTOBuilder status(String status) { this.status = status; return this; }
        public ItinerarioDTOBuilder distanciaPercorrida(Double distanciaPercorrida) { this.distanciaPercorrida = distanciaPercorrida; return this; }
        public ItinerarioDTOBuilder tempoRealizado(Integer tempoRealizado) { this.tempoRealizado = tempoRealizado; return this; }
        public ItinerarioDTOBuilder observacoes(String observacoes) { this.observacoes = observacoes; return this; }
        
        public ItinerarioDTO build() {
            return new ItinerarioDTO(id, rota, data, horaInicio, horaFim, status,
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
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Double getDistanciaPercorrida() { return distanciaPercorrida; }
    public void setDistanciaPercorrida(Double distanciaPercorrida) { this.distanciaPercorrida = distanciaPercorrida; }
    public Integer getTempoRealizado() { return tempoRealizado; }
    public void setTempoRealizado(Integer tempoRealizado) { this.tempoRealizado = tempoRealizado; }
    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }
}
