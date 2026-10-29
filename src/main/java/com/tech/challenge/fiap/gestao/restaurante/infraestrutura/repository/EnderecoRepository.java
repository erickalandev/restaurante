package com.tech.challenge.fiap.gestao.restaurante.infraestrutura.repository;

import com.tech.challenge.fiap.gestao.restaurante.infraestrutura.model.EnderecoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends JpaRepository<EnderecoEntity, Long> {
}
