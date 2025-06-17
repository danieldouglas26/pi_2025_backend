package com.senaifatesg.pi2025.core.models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.senaifatesg.pi2025.common.enums.TipoResiduo;

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
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "rota")
@Data
@Getter
@Setter
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
}