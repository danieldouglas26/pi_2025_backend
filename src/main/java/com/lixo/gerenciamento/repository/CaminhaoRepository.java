package com.lixo.gerenciamento.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lixo.gerenciamento.model.entity.Caminhao;

@Repository
public interface CaminhaoRepository extends JpaRepository<Caminhao,Long> {

    Optional<Caminhao> findByPlaca(String placa);
}
