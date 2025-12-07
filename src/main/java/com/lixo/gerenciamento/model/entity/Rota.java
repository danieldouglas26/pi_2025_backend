package com.lixo.gerenciamento.model.entity;

import java.util.ArrayList;
import java.util.List;

import com.lixo.gerenciamento.model.enums.TipoResiduo;
import com.lixo.gerenciamento.model.interfaces.Builder;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;

@Entity
@Table(name = "rota")
public class Rota {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nome;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "caminhaoid")
    private Caminhao caminhao;

    @OneToMany(mappedBy = "rota", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("ordem ASC")
    private List<ParadaRota> paradas = new ArrayList<>();

    @Column(nullable = false)
    private Double distanciaTotalKm;

    @Enumerated(EnumType.STRING)
    @Column(name = "tiporesiduo")
    private TipoResiduo tiposResiduos;

    // Construtor padrão (no-args)
    public Rota() {
    }

    // Construtor com todos os campos
    public Rota(Long id, String nome, Caminhao caminhao, List<ParadaRota> paradas, 
                Double distanciaTotalKm, TipoResiduo tiposResiduos) {
        this.id = id;
        this.nome = nome;
        this.caminhao = caminhao;
        this.paradas = paradas != null ? paradas : new ArrayList<>();
        this.distanciaTotalKm = distanciaTotalKm;
        this.tiposResiduos = tiposResiduos;
    }

    // Construtor privado para o Builder
    private Rota(RotaBuilder builder) {
        this.id = builder.id;
        this.nome = builder.nome;
        this.caminhao = builder.caminhao;
        this.paradas = builder.paradas != null ? builder.paradas : new ArrayList<>();
        this.distanciaTotalKm = builder.distanciaTotalKm;
        this.tiposResiduos = builder.tiposResiduos;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Caminhao getCaminhao() {
        return caminhao;
    }

    public void setCaminhao(Caminhao caminhao) {
        this.caminhao = caminhao;
    }

    public List<ParadaRota> getParadas() {
        if (paradas == null) {
            paradas = new ArrayList<>();
        }
        return paradas;
    }

    public void setParadas(List<ParadaRota> paradas) {
        this.paradas = paradas != null ? paradas : new ArrayList<>();
    }

    public Double getDistanciaTotalKm() {
        return distanciaTotalKm;
    }

    public void setDistanciaTotalKm(Double distanciaTotalKm) {
        this.distanciaTotalKm = distanciaTotalKm;
    }

    public TipoResiduo getTiposResiduos() {
        return tiposResiduos;
    }

    public void setTiposResiduos(TipoResiduo tiposResiduos) {
        this.tiposResiduos = tiposResiduos;
    }

    // Métodos utilitários para manipulação de paradas
    public void addParada(ParadaRota parada) {
        if (paradas == null) {
            paradas = new ArrayList<>();
        }
        parada.setRota(this);
        paradas.add(parada);
    }

    public void removeParada(ParadaRota parada) {
        if (paradas != null) {
            paradas.remove(parada);
            parada.setRota(null);
        }
    }

    // Métodos equals e hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        Rota rota = (Rota) o;
        
        if (id != null ? !id.equals(rota.id) : rota.id != null) return false;
        return nome != null ? nome.equals(rota.nome) : rota.nome == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (nome != null ? nome.hashCode() : 0);
        return result;
    }

    // Método toString
    @Override
    public String toString() {
        return "Rota{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", caminhao=" + (caminhao != null ? caminhao.getId() : "null") +
                ", paradas=" + (paradas != null ? paradas.size() : 0) +
                ", distanciaTotalKm=" + distanciaTotalKm +
                ", tiposResiduos=" + tiposResiduos +
                '}';
    }

    // Método estático para criar Builder
    public static RotaBuilder builder() {
        return new RotaBuilder();
    }
    
    
   public static class RotaBuilder implements Builder<Rota> {
        private Long id;
        private String nome;
        private Caminhao caminhao;
        private List<ParadaRota> paradas;
        private Double distanciaTotalKm;
        private TipoResiduo tiposResiduos;
        
        public RotaBuilder() {
            // Construtor padrão
        }
        
        public RotaBuilder id(Long id) {
            this.id = id;
            return this;
        }
        
        public RotaBuilder nome(String nome) {
            this.nome = nome;
            return this;
        }
        
        public RotaBuilder caminhao(Caminhao caminhao) {
            this.caminhao = caminhao;
            return this;
        }
        
        public RotaBuilder paradas(List<ParadaRota> paradas) {
            this.paradas = paradas;
            return this;
        }
        
        public RotaBuilder parada(ParadaRota parada) {
            if (this.paradas == null) {
                this.paradas = new ArrayList<>();
            }
            this.paradas.add(parada);
            return this;
        }
        
        public RotaBuilder distanciaTotalKm(Double distanciaTotalKm) {
            this.distanciaTotalKm = distanciaTotalKm;
            return this;
        }
        
        public RotaBuilder tiposResiduos(TipoResiduo tiposResiduos) {
            this.tiposResiduos = tiposResiduos;
            return this;
        }
        
        @Override
        public Rota build() {
            // Validações
            if (nome == null || nome.trim().isEmpty()) {
                throw new IllegalArgumentException("Nome da rota não pode ser nulo ou vazio");
            }
            if (distanciaTotalKm == null) {
                throw new IllegalArgumentException("Distância total não pode ser nula");
            }
            if (distanciaTotalKm < 0) {
                throw new IllegalArgumentException("Distância total não pode ser negativa");
            }
            
            // Se houver caminhão e tipos de resíduos especificados, verificar compatibilidade
            if (caminhao != null && tiposResiduos != null) {
                verificarCompatibilidadeCaminhaoResiduo();
            }
            
            // Garantir que as paradas tenham referência à rota
            if (paradas != null) {
                for (ParadaRota parada : paradas) {
                    parada.setRota(new Rota(this)); // Referência temporária, será ajustada no construtor
                }
            }
            
            return new Rota(this);
        }
        
        // Método auxiliar para verificar compatibilidade entre caminhão e tipo de resíduo
        private void verificarCompatibilidadeCaminhaoResiduo() {
            if (caminhao.getTipoResiduos() != null && !caminhao.getTipoResiduos().isEmpty()) {
                boolean compativel = false;
                for (TipoResiduo tipo : caminhao.getTipoResiduos()) {
                    if (tipo == tiposResiduos) {
                        compativel = true;
                        break;
                    }
                }
                if (!compativel) {
                    throw new IllegalArgumentException(
                        "Tipo de resíduo da rota (" + tiposResiduos + ") " +
                        "não é compatível com os tipos aceitos pelo caminhão: " + 
                        caminhao.getTipoResiduos()
                    );
                }
            }
        }
    }
}