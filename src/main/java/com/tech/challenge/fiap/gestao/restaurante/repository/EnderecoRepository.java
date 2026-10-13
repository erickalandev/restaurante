package com.tech.challenge.fiap.gestao.restaurante.repository;

import com.tech.challenge.fiap.gestao.restaurante.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
}
