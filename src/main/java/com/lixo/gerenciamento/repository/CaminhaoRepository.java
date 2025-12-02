package com.lixo.gerenciamento.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lixo.gerenciamento.model.entity.Caminhao;

@Repository
public interface CaminhaoRepository extends JpaRepository<Caminhao, Long> {
    
    Optional<Caminhao> findByPlaca(String placa);
    
    @Query("SELECT c FROM Caminhao c WHERE :tipoResiduo MEMBER OF c.tiposResiduos")
    List<Caminhao> findByTipoResiduo(@Param("tipoResiduo") String tipoResiduo);
    
    @Query("SELECT c FROM Caminhao c WHERE c.capacidadeMaxima >= :capacidadeMinima")
    List<Caminhao> findByCapacidadeMinima(@Param("capacidadeMinima") Double capacidadeMinima);
    
    boolean existsByPlaca(String placa);
    
    @Query("SELECT c FROM Caminhao c WHERE SIZE(c.tiposResiduos) > 0")
    List<Caminhao> findComTiposResiduosDefinidos();
}
