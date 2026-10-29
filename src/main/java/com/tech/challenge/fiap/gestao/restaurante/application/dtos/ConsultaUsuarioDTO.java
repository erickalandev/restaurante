package com.tech.challenge.fiap.gestao.restaurante.application.dtos;

public record ConsultaUsuarioDTO(
        String nome,
        String cpf,
        String email,
        String contato
) {
}
