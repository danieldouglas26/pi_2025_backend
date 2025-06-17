package com.senaifatesg.pi2025.core.models;

import com.senaifatesg.pi2025.core.models.interfaces.IAresta;
import com.senaifatesg.pi2025.core.models.interfaces.IVertice;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "rua")
@Data
@NoArgsConstructor
public class Rua implements IAresta {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "origem_id", nullable = false)
    private Bairro origem;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "destino_id", nullable = false)
    private Bairro destino;
    
    @Column(nullable = false)
    @Min(0)
    private Double distancia;
    
    
    @Override
    public IVertice retornaV1() {
        return origem;
    }

    @Override
    public IVertice retornaV2() {
        return destino;
    }

    @Override
    public Double retornaDistancia() {
        return distancia;
    }
     
}