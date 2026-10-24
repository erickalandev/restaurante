package com.tech.challenge.fiap.gestao.restaurante.dtos;

import java.util.Set;

public record UsuarioClienteDTO(
        String nome,
        String cpf,
        String email,
        String contato,
        Set<EnderecoDTO> enderecos
) {
}
