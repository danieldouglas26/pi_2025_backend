package com.lixo.gerenciamento.model.entity;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "rotas")
public class Rota {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String nome;
    
    @ManyToOne
    @JoinColumn(name = "caminhao_id", nullable = false)
    private Caminhao caminhao;
    
    @ManyToMany
    @JoinTable(
        name = "rota_pontos_coleta",
        joinColumns = @JoinColumn(name = "rota_id"),
        inverseJoinColumns = @JoinColumn(name = "ponto_coleta_id")
    )
    private List<PontoColeta> pontosColeta = new ArrayList<>();
    
    @Column(name = "distancia_total")
    private Double distanciaTotal;
    
    @Column(name = "tempo_estimado_minutos")
    private Integer tempoEstimadoMinutos;
    
    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;
    
    @Column(name = "sequencia_otimizada", columnDefinition = "TEXT")
    private String sequenciaOtimizada;
    

    @Transient
    private List<Bairro> caminhoBairros = new ArrayList<>();
    
    @Transient
    private Double distanciaPercorrida;
    
    @Transient
    private String status; // AGENDADA, EM_ANDAMENTO, CONCLUIDA
    
    // Construtores
    public Rota() {}
    
    public Rota(Long id, String nome, Caminhao caminhao, List<PontoColeta> pontosColeta,
               Double distanciaTotal, Integer tempoEstimadoMinutos, LocalDateTime dataCriacao,
               String sequenciaOtimizada) {
        this.id = id;
        this.nome = nome;
        this.caminhao = caminhao;
        this.pontosColeta = pontosColeta != null ? pontosColeta : new ArrayList<>();
        this.distanciaTotal = distanciaTotal;
        this.tempoEstimadoMinutos = tempoEstimadoMinutos;
        this.dataCriacao = dataCriacao;
        this.sequenciaOtimizada = sequenciaOtimizada;
    }
    
    // Builder Pattern
    public static RotaBuilder builder() {
        return new RotaBuilder();
    }
    
    public static class RotaBuilder {
        private Long id;
        private String nome;
        private Caminhao caminhao;
        private List<PontoColeta> pontosColeta;
        private Double distanciaTotal;
        private Integer tempoEstimadoMinutos;
        private LocalDateTime dataCriacao;
        private String sequenciaOtimizada;
        private List<Bairro> caminhoBairros;
        private Double distanciaPercorrida;
        private String status;
        
        public RotaBuilder id(Long id) { this.id = id; return this; }
        public RotaBuilder nome(String nome) { this.nome = nome; return this; }
        public RotaBuilder caminhao(Caminhao caminhao) { this.caminhao = caminhao; return this; }
        public RotaBuilder pontosColeta(List<PontoColeta> pontosColeta) { this.pontosColeta = pontosColeta; return this; }
        public RotaBuilder distanciaTotal(Double distanciaTotal) { this.distanciaTotal = distanciaTotal; return this; }
        public RotaBuilder tempoEstimadoMinutos(Integer tempoEstimadoMinutos) { this.tempoEstimadoMinutos = tempoEstimadoMinutos; return this; }
        public RotaBuilder dataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; return this; }
        public RotaBuilder sequenciaOtimizada(String sequenciaOtimizada) { this.sequenciaOtimizada = sequenciaOtimizada; return this; }
        public RotaBuilder caminhoBairros(List<Bairro> caminhoBairros) { this.caminhoBairros = caminhoBairros; return this; }
        public RotaBuilder distanciaPercorrida(Double distanciaPercorrida) { this.distanciaPercorrida = distanciaPercorrida; return this; }
        public RotaBuilder status(String status) { this.status = status; return this; }
        
        public Rota build() {
            Rota rota = new Rota(id, nome, caminhao, pontosColeta, distanciaTotal, 
                               tempoEstimadoMinutos, dataCriacao, sequenciaOtimizada);
            rota.setCaminhoBairros(caminhoBairros != null ? caminhoBairros : new ArrayList<>());
            rota.setDistanciaPercorrida(distanciaPercorrida);
            rota.setStatus(status);
            return rota;
        }
    }
    
    @PrePersist
    protected void onCreate() {
        dataCriacao = LocalDateTime.now();
    }
    
    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    
    public Caminhao getCaminhao() { return caminhao; }
    public void setCaminhao(Caminhao caminhao) { this.caminhao = caminhao; }
    
    public List<PontoColeta> getPontosColeta() { return pontosColeta; }
    public void setPontosColeta(List<PontoColeta> pontosColeta) { this.pontosColeta = pontosColeta; }
    
    public Double getDistanciaTotal() { return distanciaTotal; }
    public void setDistanciaTotal(Double distanciaTotal) { this.distanciaTotal = distanciaTotal; }
    
    public Integer getTempoEstimadoMinutos() { return tempoEstimadoMinutos; }
    public void setTempoEstimadoMinutos(Integer tempoEstimadoMinutos) { this.tempoEstimadoMinutos = tempoEstimadoMinutos; }
    
    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }
    
    public String getSequenciaOtimizada() { return sequenciaOtimizada; }
    public void setSequenciaOtimizada(String sequenciaOtimizada) { this.sequenciaOtimizada = sequenciaOtimizada; }
    
    public List<Bairro> getCaminhoBairros() { return caminhoBairros; }
    public void setCaminhoBairros(List<Bairro> caminhoBairros) { 
        this.caminhoBairros = caminhoBairros != null ? caminhoBairros : new ArrayList<>(); 
    }
    
    public Double getDistanciaPercorrida() { return distanciaPercorrida; }
    public void setDistanciaPercorrida(Double distanciaPercorrida) { this.distanciaPercorrida = distanciaPercorrida; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    // Métodos auxiliares
    public void adicionarPontoColeta(PontoColeta ponto) {
        if (pontosColeta == null) {
            pontosColeta = new ArrayList<>();
        }
        pontosColeta.add(ponto);
    }
    
    public boolean contemPontoColeta(Long pontoId) {
        return pontosColeta.stream()
                .anyMatch(p -> p.getId().equals(pontoId));
    }
    
    public int getQuantidadePontos() {
        return pontosColeta != null ? pontosColeta.size() : 0;
    }
    
    public Double getDistanciaMediaPorPonto() {
        if (pontosColeta == null || pontosColeta.size() <= 1 || distanciaTotal == null) {
            return 0.0;
        }
        return distanciaTotal / (pontosColeta.size() - 1);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Rota)) return false;
        Rota rota = (Rota) o;
        return Objects.equals(id, rota.id) && Objects.equals(nome, rota.nome);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, nome);
    }
    
    @Override
    public String toString() {
        return "Rota{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", distanciaTotal=" + distanciaTotal +
                ", pontos=" + getQuantidadePontos() +
                '}';
    }
}