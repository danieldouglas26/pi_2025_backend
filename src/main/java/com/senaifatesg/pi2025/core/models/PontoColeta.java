package com.senaifatesg.pi2025.core.models;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "pontocoleta")
@Data
@Getter
@Setter
@NoArgsConstructor
public class PontoColeta {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) 
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bairroid", nullable = false)
    private Bairro bairro;

    @Column(unique = true, nullable = false)
    private String nome;

    @Column(name = "nome_responsavel", nullable = false) 
    private String nomeResponsavel;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String telefone;

    @Column(nullable = false)
    private String endereco;

    @Column(name = "horario_funcionamento") 
    private String horarioFuncionamento; 

    @Column(name = "tipos_residuo_aceitos") 
    private String tiposResiduoAceitosLegacy; 



    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "pontocoletatiporesiduo", joinColumns = @JoinColumn(name = "pontocoletaid"))
    @Column(name = "tiporesiduo", nullable = false)
    @Enumerated(EnumType.STRING)
    private List<TipoResiduo> tiposDeResiduo;




}