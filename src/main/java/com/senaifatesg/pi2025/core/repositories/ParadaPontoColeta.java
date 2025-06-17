package com.senaifatesg.pi2025.core.repositories;

import com.senaifatesg.pi2025.core.models.ParadaRota;
import com.senaifatesg.pi2025.core.models.PontoColeta;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "paradapontocoleta")
@Data
@NoArgsConstructor
@AllArgsConstructor
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
}