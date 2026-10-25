package com.tech.challenge.fiap.gestao.restaurante.usecases.usuario;

import com.tech.challenge.fiap.gestao.restaurante.dtos.ConsultaUsuarioDTO;
import com.tech.challenge.fiap.gestao.restaurante.entities.Usuario;
import com.tech.challenge.fiap.gestao.restaurante.interfaces.IUsuarioGateway;

import java.util.List;

public class ConsultarUsuarioUseCase {

    private final IUsuarioGateway usuarioGateway;

    public ConsultarUsuarioUseCase(IUsuarioGateway usuarioGateway) {
        this.usuarioGateway = usuarioGateway;
    }

    public static ConsultarUsuarioUseCase buscar(IUsuarioGateway usuarioGateway) {
        return new ConsultarUsuarioUseCase(usuarioGateway);
    }

    public List<Usuario> executar(ConsultaUsuarioDTO usuarioDTO) {
        return usuarioGateway.listarUsuarios(
                usuarioDTO.nome(),
                usuarioDTO.cpf(),
                usuarioDTO.email(),
                usuarioDTO.contato());
    }
}
