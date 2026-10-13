package com.tech.challenge.fiap.gestao.restaurante.dtos;

public record ConsultaUsuarioRequest(
        String nome,
        String cpf,
        String email,
        String contato
) {
}
