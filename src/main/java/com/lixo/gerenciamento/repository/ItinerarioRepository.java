package com.lixo.gerenciamento.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lixo.gerenciamento.model.entity.Caminhao;
import com.lixo.gerenciamento.model.entity.Itinerario;

@Repository
public interface ItinerarioRepository extends JpaRepository<Itinerario, Long> {
    
    List<Itinerario> findByRotaCaminhaoId(Long caminhaoId);
    
    List<Itinerario> findByData(LocalDate data);
    
    @Query("SELECT i FROM Itinerario i WHERE i.data BETWEEN :dataInicio AND :dataFim")
    List<Itinerario> findByPeriodo(@Param("dataInicio") LocalDate dataInicio, 
                                  @Param("dataFim") LocalDate dataFim);
    
    @Query("SELECT i FROM Itinerario i WHERE i.rota.caminhao = :caminhao AND i.data = :data")
    List<Itinerario> findByCaminhaoAndData(@Param("caminhao") Caminhao caminhao, 
                                          @Param("data") LocalDate data);
    
    @Query("SELECT i FROM Itinerario i WHERE i.rota.caminhao = :caminhao AND i.data BETWEEN :dataInicio AND :dataFim")
    List<Itinerario> findByCaminhaoAndPeriodo(@Param("caminhao") Caminhao caminhao,
                                             @Param("dataInicio") LocalDate dataInicio,
                                             @Param("dataFim") LocalDate dataFim);
    
    @Query("SELECT i FROM Itinerario i WHERE i.status = :status")
    List<Itinerario> findByStatus(@Param("status") Itinerario.StatusItinerario status);
    
    @Query("SELECT i FROM Itinerario i WHERE EXTRACT(MONTH FROM i.data) = :mes AND EXTRACT(YEAR FROM i.data) = :ano")
    List<Itinerario> findByMesAndAno(@Param("mes") int mes, @Param("ano") int ano);
    
    @Query("SELECT i FROM Itinerario i WHERE i.rota.caminhao.id = :caminhaoId AND i.data = :data")
    Optional<Itinerario> findByCaminhaoIdAndData(@Param("caminhaoId") Long caminhaoId, 
                                                @Param("data") LocalDate data);
}