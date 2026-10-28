package com.tech.challenge.fiap.gestao.restaurante.application.usecases.usuario;

import com.tech.challenge.fiap.gestao.restaurante.domain.entities.Usuario;
import com.tech.challenge.fiap.gestao.restaurante.exception.UsuarioNaoEncontradoException;
import com.tech.challenge.fiap.gestao.restaurante.domain.repository.IUsuarioGateway;

public class DeletarUsuarioClienteUseCase {

    private final IUsuarioGateway usuarioGateway;

    public DeletarUsuarioClienteUseCase(IUsuarioGateway usuarioGateway) {
        this.usuarioGateway = usuarioGateway;
    }

    public static DeletarUsuarioClienteUseCase deletar(IUsuarioGateway usuarioGateway) {
        return new DeletarUsuarioClienteUseCase(usuarioGateway);
    }

    public void executar(String cpf) {
        final Usuario usuarioExistente = ConsultarUsuarioUseCase.buscar(usuarioGateway).buscarUsuarioPorCpf(cpf);
        if (usuarioExistente==null) {
            throw new UsuarioNaoEncontradoException("Usuario nao encontrado.");
        }
        usuarioGateway.deletarUsuarioPorId(usuarioExistente.getUsuarioId());
    }
}
