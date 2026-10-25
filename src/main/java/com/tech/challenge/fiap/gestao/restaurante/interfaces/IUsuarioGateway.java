package com.tech.challenge.fiap.gestao.restaurante.interfaces;

import com.tech.challenge.fiap.gestao.restaurante.entities.Usuario;

import java.util.List;

public interface IUsuarioGateway {
    Usuario buscarUsuarioPorCpf(String cpf);
    Usuario cadastrarUsuario(Usuario usuario);
    List<Usuario> listarUsuarios(String nome, String cpf, String email, String contato);
    Usuario atualizarUsuario(Usuario usuario);
    void deletarUsuario(String cpf);
}
