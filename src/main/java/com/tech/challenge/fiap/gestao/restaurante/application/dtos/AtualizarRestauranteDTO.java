package com.tech.challenge.fiap.gestao.restaurante.application.dtos;

import java.util.Set;

public record AtualizarRestauranteDTO(
        String tipoCozinha,
        String horarioFuncionamento,
        String contato,
        Set<EnderecoDTO> enderecos
) {
}
