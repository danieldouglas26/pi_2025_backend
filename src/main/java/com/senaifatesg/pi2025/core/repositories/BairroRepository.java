package com.senaifatesg.pi2025.core.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.senaifatesg.pi2025.core.models.Bairro;

public interface BairroRepository extends JpaRepository<Bairro, Long> {
	Optional<Bairro> findByNome(String nome);
}