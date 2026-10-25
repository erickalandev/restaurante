package com.tech.challenge.fiap.gestao.restaurante.dtos;

import jakarta.validation.constraints.NotBlank;

import java.util.Set;

public record RestauranteDTO(
        String nome,
        String tipoCozinha,
        String horarioFuncionamento,
        String contato,
        Set<EnderecoDTO> enderecos,
        String donoRestaurante
) {
}
