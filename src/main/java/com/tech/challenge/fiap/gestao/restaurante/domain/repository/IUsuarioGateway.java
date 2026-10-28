package com.tech.challenge.fiap.gestao.restaurante.domain.repository;

import com.tech.challenge.fiap.gestao.restaurante.domain.entities.Usuario;

import java.util.List;

public interface IUsuarioGateway {
    Usuario buscarUsuarioPorCpf(String cpf);
    Usuario cadastrarUsuario(Usuario usuario);
    List<Usuario> buscarUsuarios(String nome, String cpf, String email, String contato);
    Usuario atualizarUsuario(Usuario usuario);
    void deletarUsuarioPorId(Long id);
}
