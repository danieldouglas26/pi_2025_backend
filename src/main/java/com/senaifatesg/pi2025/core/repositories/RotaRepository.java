package com.senaifatesg.pi2025.core.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.senaifatesg.pi2025.common.enums.TipoResiduo;
import com.senaifatesg.pi2025.core.models.Rota;

@Repository
public interface RotaRepository extends JpaRepository<Rota,Long> {
    
    List<Rota> findByCaminhaoId(Long caminhaoId);
    
    boolean existsByNome(String nome);
    

    List<Rota> findByTiposResiduos(TipoResiduo tipoResiduo);
}