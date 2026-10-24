package com.tech.challenge.fiap.gestao.restaurante.dtos;


public record EnderecoDTO(
        String rua,
        String cidade,
        String estado,
        String cep,
        String numero,
        String complemento
) {
}
