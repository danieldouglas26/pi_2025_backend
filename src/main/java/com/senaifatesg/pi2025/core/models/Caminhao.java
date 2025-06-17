package com.senaifatesg.pi2025.core.models;

import java.util.List;

import com.senaifatesg.pi2025.common.enums.TipoResiduo;

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
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "caminhao")
@Data
@Getter
@Setter
@NoArgsConstructor
public class Caminhao {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private Long chaveModular;

    @Column(unique = true, nullable = false)
    private String placa; 

    @Column(name = "nomemotorista", nullable = false)
    private String nomeMotorista; 

    @Column(name = "capacidade",nullable = false)
    private Double capacidade; 


    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "caminhao_tiporesiduos", joinColumns = @JoinColumn(name = "caminhaoid"))
    @Column(name = "tiporesiduo", nullable = false)
    @Enumerated(EnumType.STRING)
    private List<TipoResiduo> tipoResiduos;

    public Caminhao(String placa, String nomeMotorista, Double capacidade, List<TipoResiduo> tipoResiduos) {
        this.placa = placa;
        this.nomeMotorista = nomeMotorista;
        this.capacidade = capacidade;
        this.tipoResiduos = tipoResiduos;
    }
}