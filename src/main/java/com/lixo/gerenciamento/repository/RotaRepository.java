package com.lixo.gerenciamento.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lixo.gerenciamento.model.entity.Rota;
import com.lixo.gerenciamento.model.enums.TipoResiduo;

@Repository
public interface RotaRepository extends JpaRepository<Rota,Long> {
    
    List<Rota> findByCaminhaoId(Long caminhaoId);
    
    boolean existsByNome(String nome);
    
    List<Rota> findByTiposResiduos(TipoResiduo tipoResiduo);
}
