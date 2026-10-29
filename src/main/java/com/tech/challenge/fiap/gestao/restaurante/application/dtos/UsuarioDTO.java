package com.tech.challenge.fiap.gestao.restaurante.application.dtos;

import com.tech.challenge.fiap.gestao.restaurante.enums.TipoUsuarioEnum;

import java.util.Set;

public record UsuarioDTO(
        String nome,
        String cpf,
        String email,
        String contato,
        Set<EnderecoDTO>enderecos,
        TipoUsuarioEnum tipoUsuario
) {

}
