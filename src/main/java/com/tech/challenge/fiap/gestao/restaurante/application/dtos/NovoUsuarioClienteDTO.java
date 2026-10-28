package com.tech.challenge.fiap.gestao.restaurante.application.dtos;

import java.util.Set;

public record NovoUsuarioClienteDTO(
        String nome,
        String cpf,
        String email,
        String contato,
        Set<EnderecoDTO> enderecos
) {
}
