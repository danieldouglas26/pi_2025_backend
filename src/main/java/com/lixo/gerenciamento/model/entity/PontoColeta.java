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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "pontos_coleta")
public class PontoColeta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String nome;
    
    @Column(nullable = false)
    private String responsavel;
    
    private String contato;
    
    @Column(nullable = false)
    private String endereco;
    
    @ManyToOne
    @JoinColumn(name = "bairro_id", nullable = false)
    private Bairro bairro;
    
    @ElementCollection
    @CollectionTable(name = "ponto_coleta_tipos_residuo", 
                    joinColumns = @JoinColumn(name = "ponto_coleta_id"))
    @Column(name = "tipo_residuo")
    private List<String> tiposResiduos = new ArrayList<>();
    
    @Column(name = "horario_funcionamento")
    private String horarioFuncionamento;
    
    @Column(name = "capacidade_diaria")
    private Double capacidadeDiaria;
    
    public PontoColeta() {}
    
    public PontoColeta(Long id, String nome, String responsavel, String contato, String endereco,
                      Bairro bairro, List<String> tiposResiduos, String horarioFuncionamento, Double capacidadeDiaria) {
        this.id = id;
        this.nome = nome;
        this.responsavel = responsavel;
        this.contato = contato;
        this.endereco = endereco;
        this.bairro = bairro;
        this.tiposResiduos = tiposResiduos != null ? tiposResiduos : new ArrayList<>();
        this.horarioFuncionamento = horarioFuncionamento;
        this.capacidadeDiaria = capacidadeDiaria;
    }
    
    public static PontoColetaBuilder builder() {
        return new PontoColetaBuilder();
    }
    
    public static class PontoColetaBuilder {
        private Long id;
        private String nome;
        private String responsavel;
        private String contato;
        private String endereco;
        private Bairro bairro;
        private List<String> tiposResiduos;
        private String horarioFuncionamento;
        private Double capacidadeDiaria;
        
        public PontoColetaBuilder id(Long id) { this.id = id; return this; }
        public PontoColetaBuilder nome(String nome) { this.nome = nome; return this; }
        public PontoColetaBuilder responsavel(String responsavel) { this.responsavel = responsavel; return this; }
        public PontoColetaBuilder contato(String contato) { this.contato = contato; return this; }
        public PontoColetaBuilder endereco(String endereco) { this.endereco = endereco; return this; }
        public PontoColetaBuilder bairro(Bairro bairro) { this.bairro = bairro; return this; }
        public PontoColetaBuilder tiposResiduos(List<String> tiposResiduos) { this.tiposResiduos = tiposResiduos; return this; }
        public PontoColetaBuilder horarioFuncionamento(String horarioFuncionamento) { this.horarioFuncionamento = horarioFuncionamento; return this; }
        public PontoColetaBuilder capacidadeDiaria(Double capacidadeDiaria) { this.capacidadeDiaria = capacidadeDiaria; return this; }
        
        public PontoColeta build() {
            return new PontoColeta(id, nome, responsavel, contato, endereco, bairro, 
                                 tiposResiduos, horarioFuncionamento, capacidadeDiaria);
        }
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getResponsavel() { return responsavel; }
    public void setResponsavel(String responsavel) { this.responsavel = responsavel; }
    public String getContato() { return contato; }
    public void setContato(String contato) { this.contato = contato; }
    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }
    public Bairro getBairro() { return bairro; }
    public void setBairro(Bairro bairro) { this.bairro = bairro; }
    public List<String> getTiposResiduos() { return tiposResiduos; }
    public void setTiposResiduos(List<String> tiposResiduos) { this.tiposResiduos = tiposResiduos; }
    public String getHorarioFuncionamento() { return horarioFuncionamento; }
    public void setHorarioFuncionamento(String horarioFuncionamento) { this.horarioFuncionamento = horarioFuncionamento; }
    public Double getCapacidadeDiaria() { return capacidadeDiaria; }
    public void setCapacidadeDiaria(Double capacidadeDiaria) { this.capacidadeDiaria = capacidadeDiaria; }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PontoColeta)) return false;
        PontoColeta that = (PontoColeta) o;
        return Objects.equals(id, that.id) && Objects.equals(nome, that.nome);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, nome);
    }
    
    @Override
    public String toString() {
        return "PontoColeta{id=" + id + ", nome='" + nome + "', bairro=" + 
               (bairro != null ? bairro.getNome() : "null") + '}';
    }
}