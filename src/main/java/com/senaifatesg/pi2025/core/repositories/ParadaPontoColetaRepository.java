package com.senaifatesg.pi2025.core.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.senaifatesg.pi2025.core.models.ParadaRota;

@Repository
public interface ParadaPontoColetaRepository extends JpaRepository<ParadaPontoColeta, Long> {
    
    List<ParadaPontoColeta> findByParadaRotaId(Long paradaRotaId);
    
    List<ParadaPontoColeta> findByParadaRotaIn(List<ParadaRota> paradas);
    
    void deleteByParadaRotaId(Long paradaRotaId);
    
    @Modifying
    @Query("DELETE FROM ParadaPontoColeta ppc WHERE ppc.paradaRota IN :paradas")
    void deleteByParadaRotaIn(@Param("paradas") List<ParadaRota> paradas);
}