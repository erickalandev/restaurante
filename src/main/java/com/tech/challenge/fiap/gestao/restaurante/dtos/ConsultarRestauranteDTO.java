package com.tech.challenge.fiap.gestao.restaurante.dtos;

public record ConsultarRestauranteDTO(
        String nome,
        String estado,
        String cidade,
        String tipoCozinha,
        String donoRestaurante
) {}
