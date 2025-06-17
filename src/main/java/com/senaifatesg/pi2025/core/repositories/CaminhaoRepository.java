package com.senaifatesg.pi2025.core.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.senaifatesg.pi2025.core.models.Caminhao;

@Repository
public interface CaminhaoRepository extends JpaRepository<Caminhao,Long> {

    Optional<Caminhao> findByPlaca(String placa);
}