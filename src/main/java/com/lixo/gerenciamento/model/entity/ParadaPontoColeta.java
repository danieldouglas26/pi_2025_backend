package com.lixo.gerenciamento.model.entity;

import java.util.List;

import com.lixo.gerenciamento.model.enums.TipoResiduo;
import com.lixo.gerenciamento.model.interfaces.Builder;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "paradapontocoleta")
public class ParadaPontoColeta {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paradarotaid", nullable = false)
    private ParadaRota paradaRota;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pontocoletaid", nullable = false)
    private PontoColeta pontoColeta;
    
    @Column(nullable = false)
    private boolean coletado;

    // Construtor padrão (no-args)
    public ParadaPontoColeta() {
        this.coletado = false; // Valor padrão
    }

    // Construtor com todos os campos
    public ParadaPontoColeta(Long id, ParadaRota paradaRota, PontoColeta pontoColeta, boolean coletado) {
        this.id = id;
        this.paradaRota = paradaRota;
        this.pontoColeta = pontoColeta;
        this.coletado = coletado;
    }

    // Construtor simplificado
    public ParadaPontoColeta(ParadaRota paradaRota, PontoColeta pontoColeta, boolean coletado) {
        this.paradaRota = paradaRota;
        this.pontoColeta = pontoColeta;
        this.coletado = coletado;
    }

    // Construtor privado para o Builder
    private ParadaPontoColeta(ParadaPontoColetaBuilder builder) {
        this.id = builder.id;
        this.paradaRota = builder.paradaRota;
        this.pontoColeta = builder.pontoColeta;
        this.coletado = builder.coletado;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ParadaRota getParadaRota() {
        return paradaRota;
    }

    public void setParadaRota(ParadaRota paradaRota) {
        this.paradaRota = paradaRota;
    }

    public PontoColeta getPontoColeta() {
        return pontoColeta;
    }

    public void setPontoColeta(PontoColeta pontoColeta) {
        this.pontoColeta = pontoColeta;
    }

    public boolean isColetado() {
        return coletado;
    }

    public void setColetado(boolean coletado) {
        this.coletado = coletado;
    }

    // Método para registrar coleta
    public void registrarColeta() {
        this.coletado = true;
    }

    // Método para desfazer registro de coleta
    public void desfazerColeta() {
        this.coletado = false;
    }

    // Métodos equals e hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        ParadaPontoColeta that = (ParadaPontoColeta) o;
        
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (paradaRota != null ? !paradaRota.equals(that.paradaRota) : that.paradaRota != null) return false;
        return pontoColeta != null ? pontoColeta.equals(that.pontoColeta) : that.pontoColeta == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (paradaRota != null ? paradaRota.hashCode() : 0);
        result = 31 * result + (pontoColeta != null ? pontoColeta.hashCode() : 0);
        result = 31 * result + (coletado ? 1 : 0);
        return result;
    }

    // Método toString
    @Override
    public String toString() {
        return "ParadaPontoColeta{" +
                "id=" + id +
                ", paradaRota=" + (paradaRota != null ? paradaRota.getId() : "null") +
                ", pontoColeta=" + (pontoColeta != null ? pontoColeta.getId() : "null") +
                ", coletado=" + coletado +
                '}';
    }

    public static ParadaPontoColetaBuilder builder() {
        return new ParadaPontoColetaBuilder();
    }
    
   public static class ParadaPontoColetaBuilder implements Builder<ParadaPontoColeta> {
        private Long id;
        private ParadaRota paradaRota;
        private PontoColeta pontoColeta;
        private boolean coletado = false;
        
        public ParadaPontoColetaBuilder() {
        }
        
        public ParadaPontoColetaBuilder id(Long id) {
            this.id = id;
            return this;
        }
        
        public ParadaPontoColetaBuilder paradaRota(ParadaRota paradaRota) {
            this.paradaRota = paradaRota;
            return this;
        }
        
        public ParadaPontoColetaBuilder pontoColeta(PontoColeta pontoColeta) {
            this.pontoColeta = pontoColeta;
            return this;
        }
        
        public ParadaPontoColetaBuilder coletado(boolean coletado) {
            this.coletado = coletado;
            return this;
        }
        
        public ParadaPontoColetaBuilder coletado() {
            this.coletado = true;
            return this;
        }
        
        public ParadaPontoColetaBuilder naoColetado() {
            this.coletado = false;
            return this;
        }
        
        @Override
        public ParadaPontoColeta build() {
            if (paradaRota == null) {
                throw new IllegalArgumentException("ParadaRota não pode ser nula");
            }
            if (pontoColeta == null) {
                throw new IllegalArgumentException("PontoColeta não pode ser nulo");
            }
            
            if (paradaRota.getBairro() != null && pontoColeta.getBairro() != null) {
                if (!paradaRota.getBairro().equals(pontoColeta.getBairro())) {
                    throw new IllegalArgumentException(
                        "Ponto de coleta deve estar no mesmo bairro da parada. " +
                        "Bairro da parada: " + paradaRota.getBairro().getId() + ", " +
                        "Bairro do ponto: " + pontoColeta.getBairro().getId()
                    );
                }
            }
            
            if (paradaRota.getRota() != null && paradaRota.getRota().getTiposResiduos() != null) {
                verificarCompatibilidadeResiduo();
            }
            
            return new ParadaPontoColeta(this);
        }
        
        private void verificarCompatibilidadeResiduo() {
            TipoResiduo tipoResiduoRota = paradaRota.getRota().getTiposResiduos();
            List<TipoResiduo> tiposResiduoPonto = pontoColeta.getTiposDeResiduo();
            
            if (tiposResiduoPonto != null && !tiposResiduoPonto.isEmpty()) {
                boolean compativel = tiposResiduoPonto.stream()
                    .anyMatch(tipo -> tipo == tipoResiduoRota);
                
                if (!compativel) {
                    throw new IllegalArgumentException(
                        "Tipo de resíduo da rota (" + tipoResiduoRota + ") " +
                        "não é compatível com os tipos aceitos pelo ponto de coleta: " + 
                        tiposResiduoPonto
                    );
                }
            }
        }
    }
}


