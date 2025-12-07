package com.lixo.gerenciamento.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lixo.gerenciamento.model.entity.Bairro;

public interface BairroRepository extends JpaRepository<Bairro, Long> {
	Optional<Bairro> findByNome(String nome);
}