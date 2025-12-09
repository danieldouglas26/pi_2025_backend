package com.lixo.gerenciamento.model.entity;

import java.time.LocalDate;

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
@Table(name = "itinerario")
public class Itinerario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rotaid", nullable = false)
    private Rota rota;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "caminhaoid", nullable = false)
    private Caminhao caminhao;

    @Column(nullable = false)
    private LocalDate data;

    public Itinerario() {
        this.data = LocalDate.now();
    }

    public Itinerario(Rota rota, Caminhao caminhao, LocalDate data) {
        this.rota = rota;
        this.caminhao = caminhao;
        this.data = data != null ? data : LocalDate.now();
    }

    public Itinerario(Long id, Rota rota, Caminhao caminhao, LocalDate data) {
        this.id = id;
        this.rota = rota;
        this.caminhao = caminhao;
        this.data = data != null ? data : LocalDate.now();
    }

    private Itinerario(ItinerarioBuilder builder) {
        this.id = builder.id;
        this.rota = builder.rota;
        this.caminhao = builder.caminhao;
        this.data = builder.data != null ? builder.data : LocalDate.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Rota getRota() {
        return rota;
    }

    public void setRota(Rota rota) {
        this.rota = rota;
    }

    public Caminhao getCaminhao() {
        return caminhao;
    }

    public void setCaminhao(Caminhao caminhao) {
        this.caminhao = caminhao;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data != null ? data : LocalDate.now();
    }

    // Métodos utilitários
    public boolean isDataFutura() {
        return data.isAfter(LocalDate.now());
    }

    public boolean isDataPassada() {
        return data.isBefore(LocalDate.now());
    }

    public boolean isDataHoje() {
        return data.isEqual(LocalDate.now());
    }

    public boolean isValido() {
        return rota != null && caminhao != null && data != null;
    }

    public void atualizarParaHoje() {
        this.data = LocalDate.now();
    }

    public void agendarPara(LocalDate novaData) {
        if (novaData != null && !novaData.isBefore(LocalDate.now())) {
            this.data = novaData;
        } else {
            throw new IllegalArgumentException("Data de agendamento deve ser hoje ou no futuro");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        Itinerario that = (Itinerario) o;
        
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (rota != null ? !rota.equals(that.rota) : that.rota != null) return false;
        if (caminhao != null ? !caminhao.equals(that.caminhao) : that.caminhao != null) return false;
        return data != null ? data.equals(that.data) : that.data == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (rota != null ? rota.hashCode() : 0);
        result = 31 * result + (caminhao != null ? caminhao.hashCode() : 0);
        result = 31 * result + (data != null ? data.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Itinerario{" +
                "id=" + id +
                ", rota=" + (rota != null ? rota.getId() : "null") +
                ", caminhao=" + (caminhao != null ? caminhao.getId() : "null") +
                ", data=" + data +
                '}';
    }

    public static ItinerarioBuilder builder() {
        return new ItinerarioBuilder();
    }
    
    public static class ItinerarioBuilder implements Builder<Itinerario> {
        private Long id;
        private Rota rota;
        private Caminhao caminhao;
        private LocalDate data;
        
        public ItinerarioBuilder() {
            // Construtor padrão
            this.data = LocalDate.now(); // Valor padrão
        }
        
        public ItinerarioBuilder id(Long id) {
            this.id = id;
            return this;
        }
        
        public ItinerarioBuilder rota(Rota rota) {
            this.rota = rota;
            return this;
        }
        
        public ItinerarioBuilder caminhao(Caminhao caminhao) {
            this.caminhao = caminhao;
            return this;
        }
        
        public ItinerarioBuilder data(LocalDate data) {
            this.data = data;
            return this;
        }
        
        public ItinerarioBuilder data(int ano, int mes, int dia) {
            this.data = LocalDate.of(ano, mes, dia);
            return this;
        }
        
        public ItinerarioBuilder paraHoje() {
            this.data = LocalDate.now();
            return this;
        }
        
        public ItinerarioBuilder paraAmanha() {
            this.data = LocalDate.now().plusDays(1);
            return this;
        }
        
        public ItinerarioBuilder paraProximaSemana() {
            this.data = LocalDate.now().plusWeeks(1);
            return this;
        }
        
        @Override
        public Itinerario build() {

               
            return new Itinerario(this);
        }

    }
}

