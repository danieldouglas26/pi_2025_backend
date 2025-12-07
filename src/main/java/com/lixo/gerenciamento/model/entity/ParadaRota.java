package com.lixo.gerenciamento.model.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.lixo.gerenciamento.model.interfaces.Builder;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "paradarota")
public class ParadaRota {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rotaid", nullable = false)
    private Rota rota;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bairroid", nullable = false)
    private Bairro bairro;

    @Column(name = "ordemparada", nullable = false)
    private int ordem;

    @Column(name = "coletado", nullable = false)
    private boolean coletado = false;

    @Column(name = "horacoleta")
    private LocalDateTime horaColeta;

    @Column(name = "observacao", length = 500)
    private String observacao;
    
    @OneToMany(mappedBy = "paradaRota", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ParadaPontoColeta> paradasPontoColeta;

    public ParadaRota() {
        this.paradasPontoColeta = new ArrayList<>();
    }

    public ParadaRota(Long id, Rota rota, Bairro bairro, int ordem, boolean coletado, 
                      LocalDateTime horaColeta, String observacao, List<ParadaPontoColeta> paradasPontoColeta) {
        this.id = id;
        this.rota = rota;
        this.bairro = bairro;
        this.ordem = ordem;
        this.coletado = coletado;
        this.horaColeta = horaColeta;
        this.observacao = observacao;
        this.paradasPontoColeta = paradasPontoColeta != null ? paradasPontoColeta : new ArrayList<>();
    }

    private ParadaRota(ParadaRotaBuilder builder) {
        this.id = builder.id;
        this.rota = builder.rota;
        this.bairro = builder.bairro;
        this.ordem = builder.ordem;
        this.coletado = builder.coletado;
        this.horaColeta = builder.horaColeta;
        this.observacao = builder.observacao;
        this.paradasPontoColeta = builder.paradasPontoColeta != null ? builder.paradasPontoColeta : new ArrayList<>();
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

    public Bairro getBairro() {
        return bairro;
    }

    public void setBairro(Bairro bairro) {
        this.bairro = bairro;
    }

    public int getOrdem() {
        return ordem;
    }

    public void setOrdem(int ordem) {
        this.ordem = ordem;
    }

    public boolean isColetado() {
        return coletado;
    }

    public void setColetado(boolean coletado) {
        this.coletado = coletado;
    }

    public LocalDateTime getHoraColeta() {
        return horaColeta;
    }

    public void setHoraColeta(LocalDateTime horaColeta) {
        this.horaColeta = horaColeta;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public List<ParadaPontoColeta> getParadasPontoColeta() {
        if (paradasPontoColeta == null) {
            paradasPontoColeta = new ArrayList<>();
        }
        return paradasPontoColeta;
    }

    public void setParadasPontoColeta(List<ParadaPontoColeta> paradasPontoColeta) {
        this.paradasPontoColeta = paradasPontoColeta != null ? paradasPontoColeta : new ArrayList<>();
    }

    public void adicionarPontoColeta(PontoColeta pontoColeta, boolean coletado) {
        ParadaPontoColeta paradaPonto = new ParadaPontoColeta();
        paradaPonto.setParadaRota(this);
        paradaPonto.setPontoColeta(pontoColeta);
        paradaPonto.setColetado(coletado);
        getParadasPontoColeta().add(paradaPonto);
    }

    public boolean todosPontosColetados() {
        return !getParadasPontoColeta().isEmpty() && 
               getParadasPontoColeta().stream().allMatch(ParadaPontoColeta::isColetado);
    }

    public void registrarColeta() {
        this.coletado = true;
        this.horaColeta = LocalDateTime.now();
    }

    public void addParadaPontoColeta(ParadaPontoColeta paradaPontoColeta) {
        if (paradasPontoColeta == null) {
            paradasPontoColeta = new ArrayList<>();
        }
        paradaPontoColeta.setParadaRota(this);
        paradasPontoColeta.add(paradaPontoColeta);
    }

    public void removeParadaPontoColeta(ParadaPontoColeta paradaPontoColeta) {
        if (paradasPontoColeta != null) {
            paradasPontoColeta.remove(paradaPontoColeta);
            paradaPontoColeta.setParadaRota(null);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        ParadaRota that = (ParadaRota) o;
        
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (rota != null ? !rota.equals(that.rota) : that.rota != null) return false;
        return bairro != null ? bairro.equals(that.bairro) : that.bairro == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (rota != null ? rota.hashCode() : 0);
        result = 31 * result + (bairro != null ? bairro.hashCode() : 0);
        result = 31 * result + ordem;
        return result;
    }

    @Override
    public String toString() {
        return "ParadaRota{" +
                "id=" + id +
                ", rota=" + (rota != null ? rota.getId() : "null") +
                ", bairro=" + (bairro != null ? bairro.getId() : "null") +
                ", ordem=" + ordem +
                ", coletado=" + coletado +
                ", horaColeta=" + horaColeta +
                ", observacao='" + observacao + '\'' +
                ", paradasPontoColeta=" + (paradasPontoColeta != null ? paradasPontoColeta.size() : 0) +
                '}';
    }

    public static ParadaRotaBuilder builder() {
        return new ParadaRotaBuilder();
    }
    
    public static class ParadaRotaBuilder implements Builder<ParadaRota> {
        private Long id;
        private Rota rota;
        private Bairro bairro;
        private int ordem;
        private boolean coletado = false;
        private LocalDateTime horaColeta;
        private String observacao;
        private List<ParadaPontoColeta> paradasPontoColeta;
        
        public ParadaRotaBuilder() {
        }
        
        public ParadaRotaBuilder id(Long id) {
            this.id = id;
            return this;
        }
        
        public ParadaRotaBuilder rota(Rota rota) {
            this.rota = rota;
            return this;
        }
        
        public ParadaRotaBuilder bairro(Bairro bairro) {
            this.bairro = bairro;
            return this;
        }
        
        public ParadaRotaBuilder ordem(int ordem) {
            this.ordem = ordem;
            return this;
        }
        
        public ParadaRotaBuilder coletado(boolean coletado) {
            this.coletado = coletado;
            return this;
        }
        
        public ParadaRotaBuilder horaColeta(LocalDateTime horaColeta) {
            this.horaColeta = horaColeta;
            return this;
        }
        
        public ParadaRotaBuilder observacao(String observacao) {
            this.observacao = observacao;
            return this;
        }
        
        public ParadaRotaBuilder paradasPontoColeta(List<ParadaPontoColeta> paradasPontoColeta) {
            this.paradasPontoColeta = paradasPontoColeta;
            return this;
        }
        
        public ParadaRotaBuilder paradaPontoColeta(ParadaPontoColeta paradaPontoColeta) {
            if (this.paradasPontoColeta == null) {
                this.paradasPontoColeta = new ArrayList<>();
            }
            this.paradasPontoColeta.add(paradaPontoColeta);
            return this;
        }
        
        public ParadaRotaBuilder comPontoColeta(PontoColeta pontoColeta, boolean coletado) {
            if (this.paradasPontoColeta == null) {
                this.paradasPontoColeta = new ArrayList<>();
            }
            ParadaPontoColeta paradaPonto = new ParadaPontoColeta();
            paradaPonto.setPontoColeta(pontoColeta);
            paradaPonto.setColetado(coletado);
            this.paradasPontoColeta.add(paradaPonto);
            return this;
        }
        
        @Override
        public ParadaRota build() {
            if (rota == null) {
                throw new IllegalArgumentException("Rota não pode ser nula");
            }
            if (bairro == null) {
                throw new IllegalArgumentException("Bairro não pode ser nulo");
            }
            if (ordem < 0) {
                throw new IllegalArgumentException("Ordem não pode ser negativa");
            }
            
            if (coletado && horaColeta == null) {
                horaColeta = LocalDateTime.now();
            }
            
            if (!coletado && horaColeta != null) {
                throw new IllegalArgumentException("Parada não coletada não pode ter hora de coleta");
            }
            
            if (paradasPontoColeta != null) {
                for (ParadaPontoColeta paradaPonto : paradasPontoColeta) {
                    paradaPonto.setParadaRota(new ParadaRota(this)); // Referência temporária
                }
            }
            
            return new ParadaRota(this);
        }
    }
}