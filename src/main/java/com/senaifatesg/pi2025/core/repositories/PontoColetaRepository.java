package com.senaifatesg.pi2025.core.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.senaifatesg.pi2025.common.enums.TipoResiduo;
import com.senaifatesg.pi2025.core.models.Bairro;
import com.senaifatesg.pi2025.core.models.PontoColeta;

@Repository
public interface PontoColetaRepository extends JpaRepository<PontoColeta,Long> {
    Optional<PontoColeta> findByNome(String nome);
    
    Page<PontoColeta> findByTiposDeResiduoContaining(String tipoResiduo, Pageable pageable);
    
    Page<PontoColeta> findByEnderecoContainingIgnoreCase(String bairro, Pageable pageable);
    
    List<PontoColeta> findByBairroAndTiposDeResiduoContaining(Bairro bairro, TipoResiduo tipoResiduo);
}