package com.senaifatesg.pi2025.core.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.senaifatesg.pi2025.core.models.Bairro;
import com.senaifatesg.pi2025.core.models.Rua;

@Repository
public interface RuaRepository extends JpaRepository<Rua,Long> {
    
    boolean existsByOrigemAndDestino(Bairro origem, Bairro destino);
    
    boolean existsByOrigemAndDestinoAndIdNot(Bairro origem, Bairro destino,Long id);
    
    boolean existsByOrigemIdOrDestinoId(Long idOrigem, Long idDestino);
    
    List<Rua> findByOrigem(Bairro origem);
    
    List<Rua> findByDestino(Bairro destino);
    
    Page<Rua> findByOrigemId(Long origemId, Pageable pageable);
    
    Page<Rua> findByDestinoId(Long destinoId, Pageable pageable);
}