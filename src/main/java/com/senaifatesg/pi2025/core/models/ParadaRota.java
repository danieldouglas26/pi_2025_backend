package com.senaifatesg.pi2025.core.models;

import java.time.LocalDateTime;
import java.util.List;

import com.senaifatesg.pi2025.core.repositories.ParadaPontoColeta;

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
import lombok.Data;

@Entity
@Table(name = "paradarota")
@Data
public class ParadaRota  {

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

    public void adicionarPontoColeta(PontoColeta pontoColeta, boolean coletado) {
        ParadaPontoColeta paradaPonto = new ParadaPontoColeta();
        paradaPonto.setParadaRota(this);
        paradaPonto.setPontoColeta(pontoColeta);
        paradaPonto.setColetado(coletado);
        this.paradasPontoColeta.add(paradaPonto);
    }

    public boolean todosPontosColetados() {
        return !paradasPontoColeta.isEmpty() && 
               paradasPontoColeta.stream().allMatch(ParadaPontoColeta::isColetado);
    }

    public void registrarColeta() {
        this.coletado = true;
        this.horaColeta = LocalDateTime.now();
    }
}