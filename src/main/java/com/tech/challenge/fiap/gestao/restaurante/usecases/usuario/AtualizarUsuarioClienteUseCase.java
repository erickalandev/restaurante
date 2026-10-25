package com.tech.challenge.fiap.gestao.restaurante.usecases.usuario;

import com.tech.challenge.fiap.gestao.restaurante.dtos.EnderecoDTO;
import com.tech.challenge.fiap.gestao.restaurante.dtos.UsuarioClienteDTO;
import com.tech.challenge.fiap.gestao.restaurante.entities.Endereco;
import com.tech.challenge.fiap.gestao.restaurante.entities.Usuario;
import com.tech.challenge.fiap.gestao.restaurante.exception.UsuarioEncontradoException;
import com.tech.challenge.fiap.gestao.restaurante.exception.UsuarioNaoEncontradoException;
import com.tech.challenge.fiap.gestao.restaurante.interfaces.IUsuarioGateway;
import org.springframework.util.ObjectUtils;

public class AtualizarUsuarioClienteUseCase {

    private final IUsuarioGateway usuarioGateway;

    public AtualizarUsuarioClienteUseCase(IUsuarioGateway usuarioGateway) {
        this.usuarioGateway = usuarioGateway;
    }

    public static AtualizarUsuarioClienteUseCase atualizar(IUsuarioGateway usuarioGateway) {
        return new AtualizarUsuarioClienteUseCase(usuarioGateway);
    }

    public Usuario executar(UsuarioClienteDTO usuarioDTO) {
        final Usuario usuarioExistente = usuarioGateway.buscarUsuarioPorCpf(usuarioDTO.cpf());
        if (ObjectUtils.isEmpty(usuarioExistente)) {
            throw new UsuarioNaoEncontradoException("Usuario com CPF " + usuarioDTO.cpf() + " nao cadastrado.");
        }

        Usuario usuario = Usuario.atualizarUsuario(
                usuarioExistente.getUsuarioId(),
                usuarioDTO.nome(),
                usuarioDTO.cpf(),
                usuarioDTO.email(),
                usuarioDTO.contato()
        );

        for (EnderecoDTO dto: usuarioDTO.enderecos()) {
            final Endereco endereco = Endereco.criarEndereco(
                    dto.rua(),
                    dto.cidade(),
                    dto.estado(),
                    dto.cep(),
                    dto.numero(),
                    dto.complemento()
            );
            usuario.adicionarEndereco(endereco);
        }
        return usuarioGateway.atualizarUsuario(usuario);
    }
}
