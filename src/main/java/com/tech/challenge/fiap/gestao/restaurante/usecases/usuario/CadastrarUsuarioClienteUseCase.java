package com.tech.challenge.fiap.gestao.restaurante.usecases.usuario;

import com.tech.challenge.fiap.gestao.restaurante.dtos.EnderecoDTO;
import com.tech.challenge.fiap.gestao.restaurante.dtos.UsuarioClienteDTO;
import com.tech.challenge.fiap.gestao.restaurante.entities.Endereco;
import com.tech.challenge.fiap.gestao.restaurante.entities.Usuario;
import com.tech.challenge.fiap.gestao.restaurante.exception.UsuarioEncontradoException;
import com.tech.challenge.fiap.gestao.restaurante.interfaces.IUsuarioGateway;
import org.springframework.util.ObjectUtils;

public class CadastrarUsuarioClienteUseCase {

    private final IUsuarioGateway usuarioGateway;

    public CadastrarUsuarioClienteUseCase(IUsuarioGateway usuarioGateway) {
        this.usuarioGateway = usuarioGateway;
    }

    public static CadastrarUsuarioClienteUseCase criar(IUsuarioGateway usuarioGateway) {
        return new CadastrarUsuarioClienteUseCase(usuarioGateway);
    }

    public Usuario executar(UsuarioClienteDTO usuarioDTO) {
        Usuario usuario = Usuario.criarUsuario(
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

        final Usuario usuarioExistente = usuarioGateway.buscarUsuarioPorCpf(usuarioDTO.cpf());
        if (!ObjectUtils.isEmpty(usuarioExistente)) {
            throw new UsuarioEncontradoException("Usuario com CPF " + usuarioDTO.cpf() + " ja cadastrado.");
        }

        return usuarioGateway.cadastrarUsuario(usuario);
    }
}
