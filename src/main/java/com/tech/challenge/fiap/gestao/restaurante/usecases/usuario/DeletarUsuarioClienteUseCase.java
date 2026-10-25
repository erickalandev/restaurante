package com.tech.challenge.fiap.gestao.restaurante.usecases.usuario;

import com.tech.challenge.fiap.gestao.restaurante.entities.Usuario;
import com.tech.challenge.fiap.gestao.restaurante.exception.UsuarioNaoEncontradoException;
import com.tech.challenge.fiap.gestao.restaurante.interfaces.IUsuarioGateway;
import org.springframework.util.ObjectUtils;

public class DeletarUsuarioClienteUseCase {

    private final IUsuarioGateway iUsuarioGateway;

    public DeletarUsuarioClienteUseCase(IUsuarioGateway iUsuarioGateway) {
        this.iUsuarioGateway = iUsuarioGateway;
    }

    public static DeletarUsuarioClienteUseCase deletar(IUsuarioGateway iUsuarioGateway) {
        return new DeletarUsuarioClienteUseCase(iUsuarioGateway);
    }

    public void executar(String cpf) {
        final Usuario usuarioExistente = iUsuarioGateway.buscarUsuarioPorCpf(cpf);
        if (ObjectUtils.isEmpty(usuarioExistente)) {
            throw new UsuarioNaoEncontradoException("Usuario com CPF " + cpf + " nao cadastrado.");
        }
        iUsuarioGateway.deletarUsuario(cpf);
    }
}
