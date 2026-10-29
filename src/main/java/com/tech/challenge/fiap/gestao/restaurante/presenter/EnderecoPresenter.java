package com.tech.challenge.fiap.gestao.restaurante.presenter;

import com.tech.challenge.fiap.gestao.restaurante.application.dtos.EnderecoDTO;
import com.tech.challenge.fiap.gestao.restaurante.domain.entities.Endereco;
import com.tech.challenge.fiap.gestao.restaurante.domain.entities.Restaurante;
import com.tech.challenge.fiap.gestao.restaurante.domain.entities.Usuario;
import com.tech.challenge.fiap.gestao.restaurante.infraestrutura.model.EnderecoEntity;
import com.tech.challenge.fiap.gestao.restaurante.infraestrutura.model.RestauranteEntity;
import com.tech.challenge.fiap.gestao.restaurante.infraestrutura.model.UsuarioEntity;

public abstract class EnderecoPresenter {

    public static EnderecoDTO toDTO(Endereco endereco) {
        if(endereco == null) {
            return null;
        }
        return new EnderecoDTO(
                endereco.getRua(),
                endereco.getCidade(),
                endereco.getEstado(),
                endereco.getCep(),
                endereco.getNumero(),
                endereco.getComplemento());
    }

    public static EnderecoEntity toEntity(Endereco endereco, UsuarioEntity usuarioEntity, RestauranteEntity restauranteEntity) {
        if(endereco == null) {
            return null;
        }

        return new EnderecoEntity(
                endereco.getEnderecoId(),
                endereco.getRua(),
                endereco.getCidade(),
                endereco.getEstado(),
                endereco.getCep(),
                endereco.getNumero(),
                endereco.getComplemento(),
                restauranteEntity,
                usuarioEntity
        );
    }
}
