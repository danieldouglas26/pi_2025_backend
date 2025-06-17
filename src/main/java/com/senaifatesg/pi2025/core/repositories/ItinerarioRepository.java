package com.senaifatesg.pi2025.core.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.senaifatesg.pi2025.core.models.Itinerario;

@Repository
public interface ItinerarioRepository extends JpaRepository<Itinerario,Long> {

    boolean existsByCaminhaoIdAndData(Long caminhaoId, LocalDate data);
    
    List<Itinerario> findByCaminhaoId(Long caminhaoId);
    
    List<Itinerario> findByData(LocalDate data);
    
    @Query("SELECT i FROM Itinerario i WHERE i.data BETWEEN :inicio AND :fim")
    List<Itinerario> findByPeriodo(@Param("inicio") LocalDate inicio, @Param("fim") LocalDate fim);
    
    @Query("SELECT i FROM Itinerario i WHERE i.rota.id = :rotaId")
    List<Itinerario> findByRotaId(Long rotaId);
    

    @Query("SELECT i FROM Itinerario i WHERE i.caminhao.id = :caminhaoId AND i.data BETWEEN :inicio AND :fim ORDER BY i.data")
    List<Itinerario> findItinerariosPorCaminhaoEPeriodo(Long caminhaoId, LocalDate inicio, LocalDate fim);

    @Query("SELECT i FROM Itinerario i JOIN i.rota r JOIN r.paradas p WHERE p.bairro.id = :bairroId AND i.data BETWEEN :inicio AND :fim")
    List<Itinerario> findItinerariosPorBairroEPeriodo(Long bairroId, LocalDate inicio, LocalDate fim);
}