package com.tech.challenge.fiap.gestao.restaurante.infraestrutura.repository;

import com.tech.challenge.fiap.gestao.restaurante.infraestrutura.model.ItensCardapioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItensCardapioRepository extends JpaRepository<ItensCardapioEntity, Long> {
}
