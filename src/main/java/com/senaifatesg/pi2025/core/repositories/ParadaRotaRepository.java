package com.senaifatesg.pi2025.core.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.senaifatesg.pi2025.core.models.ParadaRota;
import com.senaifatesg.pi2025.core.models.Rota; 

@Repository
public interface ParadaRotaRepository extends JpaRepository<ParadaRota, Long> {

    @Query("SELECT pr FROM ParadaRota pr WHERE pr.rota.id = :rotaId ORDER BY pr.ordem ASC")
    List<ParadaRota> findByRotaIdOrderByOrdem(@Param("rotaId") Long rotaId);

    @Query("SELECT pr FROM ParadaRota pr WHERE pr.bairro.id = :bairroId")
    List<ParadaRota> findByBairroId(@Param("bairroId") Long bairroId);

    long countByRotaId(Long rotaId);

    boolean existsByRotaIdAndOrdem(Long rotaId, int ordem);

    @Modifying
    @Query("UPDATE ParadaRota pr SET pr.ordem = pr.ordem + 1 WHERE pr.rota.id = :rotaId AND pr.ordem >= :ordem")
    void incrementOrdemAfter(@Param("rotaId") Long rotaId, @Param("ordem") int ordem);

    List<ParadaRota> findByRotaOrderByOrdem(Rota rota);

    @Modifying
    @Query("DELETE FROM ParadaRota p WHERE p.rota.id = :rotaId")
    void deleteByRotaId(Long rotaId);

    boolean existsByBairroId(Long bairroId);

    @Query("SELECT DISTINCT p.rota.id FROM ParadaRota p WHERE p.bairro.id = :bairroId")
    List<Long> findDistinctRotaIdsByBairroId(@Param("bairroId") Long bairroId);
}