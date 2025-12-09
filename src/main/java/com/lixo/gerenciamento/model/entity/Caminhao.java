package com.lixo.gerenciamento.model.entity;

import java.util.ArrayList;
import java.util.List;

import com.lixo.gerenciamento.model.enums.TipoResiduo;
import com.lixo.gerenciamento.model.interfaces.Builder;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "caminhao")
public class Caminhao {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private Long chaveModular;

    @Column(unique = true, nullable = false)
    private String placa; 

    @Column(name = "nomemotorista", nullable = false)
    private String nomeMotorista; 

    @Column(name = "capacidade", nullable = false)
    private Double capacidade; 

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "caminhao_tiporesiduos", joinColumns = @JoinColumn(name = "caminhaoid"))
    @Column(name = "tiporesiduo", nullable = false)
    @Enumerated(EnumType.STRING)
    private List<TipoResiduo> tipoResiduos;

    public Caminhao() {
    }

    public Caminhao(String placa, String nomeMotorista, Double capacidade, List<TipoResiduo> tipoResiduos) {
        this.placa = placa;
        this.nomeMotorista = nomeMotorista;
        this.capacidade = capacidade;
        this.tipoResiduos = tipoResiduos;
    }

    // Construtor privado para o Builder
    private Caminhao(CaminhaoBuilder builder) {
        this.id = builder.id;
        this.chaveModular = builder.chaveModular;
        this.placa = builder.placa;
        this.nomeMotorista = builder.nomeMotorista;
        this.capacidade = builder.capacidade;
        this.tipoResiduos = builder.tipoResiduos;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getChaveModular() {
        return chaveModular;
    }

    public void setChaveModular(Long chaveModular) {
        this.chaveModular = chaveModular;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getNomeMotorista() {
        return nomeMotorista;
    }

    public void setNomeMotorista(String nomeMotorista) {
        this.nomeMotorista = nomeMotorista;
    }

    public Double getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(Double capacidade) {
        this.capacidade = capacidade;
    }

    public List<TipoResiduo> getTipoResiduos() {
        if (tipoResiduos == null) {
            tipoResiduos = new ArrayList<>();
        }
        return tipoResiduos;
    }

    public void setTipoResiduos(List<TipoResiduo> tipoResiduos) {
        this.tipoResiduos = tipoResiduos;
    }

    // Métodos equals e hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        Caminhao caminhao = (Caminhao) o;
        
        if (id != null ? !id.equals(caminhao.id) : caminhao.id != null) return false;
        return placa != null ? placa.equals(caminhao.placa) : caminhao.placa == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (placa != null ? placa.hashCode() : 0);
        return result;
    }

    // Método toString
    @Override
    public String toString() {
        return "Caminhao{" +
                "id=" + id +
                ", chaveModular=" + chaveModular +
                ", placa='" + placa + '\'' +
                ", nomeMotorista='" + nomeMotorista + '\'' +
                ", capacidade=" + capacidade +
                ", tipoResiduos=" + tipoResiduos +
                '}';
    }

    // Builder Pattern
    public static CaminhaoBuilder builder() {
        return new CaminhaoBuilder();
    }

    public static class CaminhaoBuilder implements Builder<Caminhao> {
        private Long id;
        private Long chaveModular;
        private String placa;
        private String nomeMotorista;
        private Double capacidade;
        private List<TipoResiduo> tipoResiduos;

        private CaminhaoBuilder() {
        	
        }

        public CaminhaoBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public CaminhaoBuilder chaveModular(Long chaveModular) {
            this.chaveModular = chaveModular;
            return this;
        }

        public CaminhaoBuilder placa(String placa) {
            this.placa = placa;
            return this;
        }

        public CaminhaoBuilder nomeMotorista(String nomeMotorista) {
            this.nomeMotorista = nomeMotorista;
            return this;
        }

        public CaminhaoBuilder capacidade(Double capacidade) {
            this.capacidade = capacidade;
            return this;
        }

        public CaminhaoBuilder tipoResiduos(List<TipoResiduo> tipoResiduos) {
            this.tipoResiduos = tipoResiduos;
            return this;
        }

        public CaminhaoBuilder tipoResiduo(TipoResiduo tipoResiduo) {
            if (this.tipoResiduos == null) {
                this.tipoResiduos = new ArrayList<>();
            }
            this.tipoResiduos.add(tipoResiduo);
            return this;
        }

        public Caminhao build() {
            return new Caminhao(this);
        }
    }
}