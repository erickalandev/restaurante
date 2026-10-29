package com.tech.challenge.fiap.gestao.restaurante.application.usecases.usuario;

import com.tech.challenge.fiap.gestao.restaurante.application.dtos.EnderecoDTO;
import com.tech.challenge.fiap.gestao.restaurante.application.dtos.NovoUsuarioClienteDTO;
import com.tech.challenge.fiap.gestao.restaurante.domain.entities.Endereco;
import com.tech.challenge.fiap.gestao.restaurante.domain.entities.Usuario;
import com.tech.challenge.fiap.gestao.restaurante.enums.TipoUsuarioEnum;
import com.tech.challenge.fiap.gestao.restaurante.exception.UsuarioEncontradoException;
import com.tech.challenge.fiap.gestao.restaurante.domain.repository.IUsuarioGateway;

public class CadastrarUsuarioClienteUseCase {

    private final IUsuarioGateway usuarioGateway;

    public CadastrarUsuarioClienteUseCase(IUsuarioGateway usuarioGateway) {
        this.usuarioGateway = usuarioGateway;
    }

    public static CadastrarUsuarioClienteUseCase criar(IUsuarioGateway usuarioGateway) {
        return new CadastrarUsuarioClienteUseCase(usuarioGateway);
    }

    public Usuario executar(NovoUsuarioClienteDTO usuarioDTO) {
        Usuario usuario = Usuario.criarUsuario(
    null,
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

        final Usuario usuarioExistente = ConsultarUsuarioUseCase.buscar(usuarioGateway).buscarUsuarioPorCpf(usuario.getCpf());
        if (usuarioExistente != null) {
            throw new UsuarioEncontradoException("CPF ja cadastrado.");
        }

        return usuarioGateway.cadastrarUsuario(usuario);
    }
}
