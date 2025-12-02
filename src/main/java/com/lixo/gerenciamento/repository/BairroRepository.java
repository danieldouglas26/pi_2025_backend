package com.lixo.gerenciamento.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lixo.gerenciamento.model.entity.Bairro;

@Repository
public interface BairroRepository extends JpaRepository<Bairro, Long> {
    Optional<Bairro> findByNome(String nome);
    List<Bairro> findByTemPontoColetaTrue();
    boolean existsByNome(String nome);
}