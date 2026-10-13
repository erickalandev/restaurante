package com.tech.challenge.fiap.gestao.restaurante.dtos;

public record ConsultarRestauranteRequest(
        String nome,
        String estado,
        String cidade,
        String tipoCozinha,
        String donoRestaurante
) {}
