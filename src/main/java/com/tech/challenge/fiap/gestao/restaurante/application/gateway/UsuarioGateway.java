package com.tech.challenge.fiap.gestao.restaurante.application.gateway;

import com.tech.challenge.fiap.gestao.restaurante.domain.entities.Usuario;
import com.tech.challenge.fiap.gestao.restaurante.domain.repository.IDataSource;
import com.tech.challenge.fiap.gestao.restaurante.domain.repository.IUsuarioGateway;
import java.util.List;

public class UsuarioGateway implements IUsuarioGateway {

    private final IDataSource dataSource;

    public UsuarioGateway(IDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static UsuarioGateway create(IDataSource dataSource) {
        return new UsuarioGateway(dataSource);
    }

    @Override
    public Usuario buscarUsuarioPorCpf(String cpf) {
        return this.dataSource.buscarUsuarioPorCpf(cpf);
    }

    @Override
    public Usuario cadastrarUsuario(Usuario usuario) {
        return this.dataSource.cadastrarUsuario(usuario);
    }

    @Override
    public List<Usuario> buscarUsuarios(String nome, String cpf, String email, String contato) {
        return this.dataSource.buscarUsuarios(nome, cpf, email, contato).stream().toList();
    }

    @Override
    public Usuario atualizarUsuario(Usuario usuario) {
        return this.dataSource.atualizarUsuario(usuario);
    }

    @Override
    public void deletarUsuarioPorId(Long id) {
        this.dataSource.deletarUsuarioPorId(id);
    }
}
