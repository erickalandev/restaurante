package com.tech.challenge.fiap.gestao.restaurante.presenter;

import com.tech.challenge.fiap.gestao.restaurante.application.dtos.EnderecoDTO;
import com.tech.challenge.fiap.gestao.restaurante.application.dtos.UsuarioDTO;
import com.tech.challenge.fiap.gestao.restaurante.domain.entities.Endereco;
import com.tech.challenge.fiap.gestao.restaurante.domain.entities.Restaurante;
import com.tech.challenge.fiap.gestao.restaurante.domain.entities.Usuario;
import com.tech.challenge.fiap.gestao.restaurante.infraestrutura.model.EnderecoEntity;
import com.tech.challenge.fiap.gestao.restaurante.infraestrutura.model.UsuarioEntity;
import org.springframework.util.ObjectUtils;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class UsuarioPresenter {

    public static UsuarioDTO toDTO(Usuario usuario) {
        if(usuario == null) {
            return null;
        }
        Set<EnderecoDTO> enderecosDTO = usuario.getEnderecos().stream()
                .map(EnderecoPresenter::toDTO)
                .collect(Collectors.toSet());

        return new UsuarioDTO(
                usuario.getNome(),
                usuario.getCpf(),
                usuario.getEmail(),
                usuario.getContato(),
                enderecosDTO,
                usuario.getTipoUsuario());
    }

    public static Usuario toDomain(UsuarioEntity entity) {
        if(entity == null) {
            return null;
        }
        Usuario usuario = Usuario.criarUsuario(entity.getUsuarioId(), entity.getNome(), entity.getCpf(), entity.getEmail(), entity.getContato(), entity.getTipoUsuario());
        entity.getEnderecoEntities().forEach( enderecoEntity -> {
                usuario.adicionarEndereco(Endereco.criarEndereco(
                                enderecoEntity.getRua(),
                                enderecoEntity.getCidade(),
                                enderecoEntity.getEstado(),
                                enderecoEntity.getCep(),
                                enderecoEntity.getNumero(),
                                enderecoEntity.getComplemento()));
        });
        return usuario;
    }

    public static UsuarioEntity toEntity(Usuario novoUsuarioCliente) {
        if(novoUsuarioCliente == null) {
            return null;
        }
        var usuarioEntity = new UsuarioEntity(
                novoUsuarioCliente.getUsuarioId(),
                novoUsuarioCliente.getNome(),
                novoUsuarioCliente.getCpf(),
                novoUsuarioCliente.getEmail(),
                novoUsuarioCliente.getContato(),
                novoUsuarioCliente.getTipoUsuario());

        if (!ObjectUtils.isEmpty(novoUsuarioCliente.getEnderecos())) {
            Set<EnderecoEntity> enderecoEntity = novoUsuarioCliente.getEnderecos()
                    .stream()
                    .map(endereco -> EnderecoPresenter.toEntity(endereco, usuarioEntity, null))
                    .collect(Collectors.toSet());

            usuarioEntity.vincularEndereco(enderecoEntity);
        }
        return usuarioEntity;
    }
}
