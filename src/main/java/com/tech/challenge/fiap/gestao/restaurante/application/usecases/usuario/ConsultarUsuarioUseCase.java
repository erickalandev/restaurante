package com.tech.challenge.fiap.gestao.restaurante.application.usecases.usuario;

import com.tech.challenge.fiap.gestao.restaurante.application.dtos.ConsultaUsuarioDTO;
import com.tech.challenge.fiap.gestao.restaurante.domain.entities.Usuario;
import com.tech.challenge.fiap.gestao.restaurante.domain.repository.IUsuarioGateway;
import com.tech.challenge.fiap.gestao.restaurante.utils.CpfUtils;
import com.tech.challenge.fiap.gestao.restaurante.utils.EmailUtils;
import com.tech.challenge.fiap.gestao.restaurante.utils.FiltroUtils;

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
        if (usuarioDTO == null) {
            return usuarioGateway.buscarUsuarios(null, null, null, null);
        }

        String cpfValido = FiltroUtils.normalizarCpf(usuarioDTO.cpf());
        String email = FiltroUtils.normalizarTexto(usuarioDTO.email());
        String nome = FiltroUtils.normalizarTexto(usuarioDTO.nome());
        String contato = FiltroUtils.normalizarTexto(usuarioDTO.contato());

        if (email != null) {
            EmailUtils.validaEmail(email);
        }

        return usuarioGateway.buscarUsuarios(nome, cpfValido, email, contato);
    }

    public Usuario buscarUsuarioPorCpf(String cpf) {
        var cpfValido = CpfUtils.formatarCpf(cpf);
        return usuarioGateway.buscarUsuarioPorCpf(cpfValido);
    }
}
