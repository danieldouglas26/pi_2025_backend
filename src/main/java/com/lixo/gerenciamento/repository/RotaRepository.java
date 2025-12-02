package com.lixo.gerenciamento.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lixo.gerenciamento.model.entity.Rota;

@Repository
public interface RotaRepository extends JpaRepository<Rota, Long> {
    
    List<Rota> findByCaminhaoId(Long caminhaoId);
    
    @Query("SELECT r FROM Rota r WHERE SIZE(r.pontosColeta) >= :minPontos")
    List<Rota> findByMinimoPontos(@Param("minPontos") int minPontos);
    
    @Query("SELECT r FROM Rota r WHERE r.distanciaTotal BETWEEN :min AND :max")
    List<Rota> findByDistanciaBetween(@Param("min") Double min, @Param("max") Double max);
    
    @Query("SELECT r FROM Rota r WHERE r.caminhao.id = :caminhaoId AND r.distanciaTotal > :distancia")
    List<Rota> findByCaminhaoAndDistanciaMinima(@Param("caminhaoId") Long caminhaoId, 
                                               @Param("distancia") Double distancia);
}