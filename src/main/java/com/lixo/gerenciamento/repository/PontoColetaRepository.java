package com.lixo.gerenciamento.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.lixo.gerenciamento.model.entity.Bairro;
import com.lixo.gerenciamento.model.entity.PontoColeta;
import com.lixo.gerenciamento.model.enums.TipoResiduo;

@Repository
public interface PontoColetaRepository extends JpaRepository<PontoColeta, Long>, JpaSpecificationExecutor<PontoColeta> {    Optional<PontoColeta> findByNome(String nome);
    
    Page<PontoColeta> findByTiposDeResiduoContaining(String tipoResiduo, Pageable pageable);
    
    Page<PontoColeta> findByEnderecoContainingIgnoreCase(String bairro, Pageable pageable);
    
    List<PontoColeta> findByBairroAndTiposDeResiduoContaining(Bairro bairro, TipoResiduo tipoResiduo);
}