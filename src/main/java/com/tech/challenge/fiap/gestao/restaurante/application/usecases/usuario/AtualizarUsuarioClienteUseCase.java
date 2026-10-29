package com.tech.challenge.fiap.gestao.restaurante.application.usecases.usuario;

import com.tech.challenge.fiap.gestao.restaurante.application.dtos.EnderecoDTO;
import com.tech.challenge.fiap.gestao.restaurante.application.dtos.NovoUsuarioClienteDTO;
import com.tech.challenge.fiap.gestao.restaurante.domain.entities.Endereco;
import com.tech.challenge.fiap.gestao.restaurante.domain.entities.Usuario;
import com.tech.challenge.fiap.gestao.restaurante.enums.TipoUsuarioEnum;
import com.tech.challenge.fiap.gestao.restaurante.exception.UsuarioNaoEncontradoException;
import com.tech.challenge.fiap.gestao.restaurante.domain.repository.IUsuarioGateway;

public class AtualizarUsuarioClienteUseCase {

    private final IUsuarioGateway usuarioGateway;

    public AtualizarUsuarioClienteUseCase(IUsuarioGateway usuarioGateway) {
        this.usuarioGateway = usuarioGateway;
    }

    public static AtualizarUsuarioClienteUseCase atualizar(IUsuarioGateway usuarioGateway) {
        return new AtualizarUsuarioClienteUseCase(usuarioGateway);
    }

    public Usuario executar(NovoUsuarioClienteDTO usuarioDTO) {
        final Usuario usuarioExistente = ConsultarUsuarioUseCase.buscar(usuarioGateway).buscarUsuarioPorCpf(usuarioDTO.cpf());
        if (usuarioExistente == null) {
            throw new UsuarioNaoEncontradoException("Usuario nao encontrado.");
        }

        Usuario usuario = Usuario.criarUsuario(
                usuarioExistente.getUsuarioId(),
                usuarioDTO.nome(),
                usuarioDTO.cpf(),
                usuarioDTO.email(),
                usuarioDTO.contato(),
                TipoUsuarioEnum.CLIENTE
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
