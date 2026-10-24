package com.tech.challenge.fiap.gestao.restaurante.dtos;

import jakarta.validation.constraints.NotBlank;

import java.util.Set;

public record RestauranteRequest(
        @NotBlank(message = "O nome do restaurante é obrigatório")
        String nome,

        @NotBlank(message = "O tipo de cozinha é obrigatório")
        String tipoCozinha,

        @NotBlank(message = "O horário de funcionamento é obrigatório")
        String horarioFuncionamento,

        @NotBlank(message = "O contato do restaurante é obrigatório")
        String contato,

        Set<EnderecoDTO> enderecos,

        @NotBlank(message = "O dono do restaurante é obrigatório")
        String donoRestaurante
) {
}
