package com.lixo.gerenciamento.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lixo.gerenciamento.model.entity.ConexaoBairro;

@Repository
public interface ConexaoBairroRepository extends JpaRepository<ConexaoBairro, Long> {
    
    @Query("SELECT c FROM ConexaoBairro c WHERE c.bairroOrigem.id = :bairroOrigemId")
    List<ConexaoBairro> findByBairroOrigemId(@Param("bairroOrigemId") Long bairroOrigemId);
    
    @Query("SELECT c FROM ConexaoBairro c WHERE c.bairroDestino.id = :bairroDestinoId")
    List<ConexaoBairro> findByBairroDestinoId(@Param("bairroDestinoId") Long bairroDestinoId);
    
    @Query("SELECT c FROM ConexaoBairro c WHERE c.bairroOrigem.id = :origemId AND c.bairroDestino.id = :destinoId")
    Optional<ConexaoBairro> findByOrigemAndDestino(@Param("origemId") Long origemId, @Param("destinoId") Long destinoId);
}
