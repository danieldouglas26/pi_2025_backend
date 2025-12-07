package com.lixo.gerenciamento.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lixo.gerenciamento.model.entity.Bairro;
import com.lixo.gerenciamento.model.entity.Rua;

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
