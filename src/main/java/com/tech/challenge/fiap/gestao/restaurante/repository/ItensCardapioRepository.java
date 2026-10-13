package com.tech.challenge.fiap.gestao.restaurante.repository;

import com.tech.challenge.fiap.gestao.restaurante.model.ItensCardapio;
import com.tech.challenge.fiap.gestao.restaurante.model.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItensCardapioRepository extends JpaRepository<ItensCardapio, Long> {

    @Query("""
            SELECT i
            FROM ItensCardapio i
            INNER JOIN FETCH i.restaurante r
            WHERE i.restaurante.nome = :nomeRestaurante
              AND i.restaurante.donoRestaurante.nome = :donoRestaurante
            """)
    List<ItensCardapio> findByItensCardapio(String nomeRestaurante, String donoRestaurante);
}
