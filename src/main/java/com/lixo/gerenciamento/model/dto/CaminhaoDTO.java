package com.lixo.gerenciamento.model.dto;

import java.util.List;

public class CaminhaoDTO {
    private Long id;
    private String placa;
    private String motorista;
    private Double capacidadeMaxima;
    private List<String> tiposResiduos;
    
    public CaminhaoDTO() {}
    
    public CaminhaoDTO(Long id, String placa, String motorista, Double capacidadeMaxima, List<String> tiposResiduos) {
        this.id = id;
        this.placa = placa;
        this.motorista = motorista;
        this.capacidadeMaxima = capacidadeMaxima;
        this.tiposResiduos = tiposResiduos;
    }
    
    public static CaminhaoDTOBuilder builder() {
        return new CaminhaoDTOBuilder();
    }
    
    public static class CaminhaoDTOBuilder {
        private Long id;
        private String placa;
        private String motorista;
        private Double capacidadeMaxima;
        private List<String> tiposResiduos;
        
        public CaminhaoDTOBuilder id(Long id) { this.id = id; return this; }
        public CaminhaoDTOBuilder placa(String placa) { this.placa = placa; return this; }
        public CaminhaoDTOBuilder motorista(String motorista) { this.motorista = motorista; return this; }
        public CaminhaoDTOBuilder capacidadeMaxima(Double capacidadeMaxima) { 
            this.capacidadeMaxima = capacidadeMaxima; return this; 
        }
        public CaminhaoDTOBuilder tiposResiduos(List<String> tiposResiduos) { 
            this.tiposResiduos = tiposResiduos; return this; 
        }
        
        public CaminhaoDTO build() {
            return new CaminhaoDTO(id, placa, motorista, capacidadeMaxima, tiposResiduos);
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
}