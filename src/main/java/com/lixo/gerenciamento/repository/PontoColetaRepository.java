package com.lixo.gerenciamento.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lixo.gerenciamento.model.entity.PontoColeta;

@Repository
public interface PontoColetaRepository extends JpaRepository<PontoColeta, Long> {
    
    Optional<PontoColeta> findByNome(String nome);
    
    @Query("SELECT p FROM PontoColeta p WHERE :tipoResiduo MEMBER OF p.tiposResiduos")
    List<PontoColeta> findByTipoResiduo(@Param("tipoResiduo") String tipoResiduo);
    
    @Query("SELECT p FROM PontoColeta p WHERE p.bairro.id = :bairroId")
    List<PontoColeta> findByBairroId(@Param("bairroId") Long bairroId);
    
    @Query("SELECT p FROM PontoColeta p WHERE p.bairro.nome = :bairroNome")
    List<PontoColeta> findByBairroNome(@Param("bairroNome") String bairroNome);
    
    boolean existsByNome(String nome);
    
    @Query("SELECT p FROM PontoColeta p WHERE p.bairro.temPontoColeta = true")
    List<PontoColeta> findComBairroComPontoColeta();
}
