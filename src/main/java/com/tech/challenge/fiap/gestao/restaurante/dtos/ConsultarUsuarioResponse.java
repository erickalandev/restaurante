package com.tech.challenge.fiap.gestao.restaurante.dtos;

import com.tech.challenge.fiap.gestao.restaurante.model.Endereco;
import com.tech.challenge.fiap.gestao.restaurante.model.Usuario;

import java.util.List;

public record ConsultarUsuarioResponse(
        String nome,
        String tipoUsuario,
        String cpf,
        String email,
        String contato,
        List<EnderecoDTO> enderecos
) {
    public static ConsultarUsuarioResponse toResponse(Usuario usuario) {
        return new ConsultarUsuarioResponse(
                usuario.getNome(),
                usuario.getTipoUsuario() == null ? null : usuario.getTipoUsuario().name().toLowerCase(),
                usuario.getCpf(),
                usuario.getEmail(),
                usuario.getContato(),
                Endereco.toEnderecosDTO(usuario.getEnderecos())
        );
    }
}
