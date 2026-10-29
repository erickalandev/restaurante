package com.tech.challenge.fiap.gestao.restaurante.presenter;

import com.tech.challenge.fiap.gestao.restaurante.application.dtos.EnderecoDTO;
import com.tech.challenge.fiap.gestao.restaurante.application.dtos.RestauranteDTO;
import com.tech.challenge.fiap.gestao.restaurante.domain.entities.Endereco;
import com.tech.challenge.fiap.gestao.restaurante.domain.entities.Restaurante;
import com.tech.challenge.fiap.gestao.restaurante.infraestrutura.model.EnderecoEntity;
import com.tech.challenge.fiap.gestao.restaurante.infraestrutura.model.ItensCardapioEntity;
import com.tech.challenge.fiap.gestao.restaurante.infraestrutura.model.RestauranteEntity;
import com.tech.challenge.fiap.gestao.restaurante.infraestrutura.model.UsuarioEntity;
import org.springframework.util.ObjectUtils;

import java.util.Set;
import java.util.stream.Collectors;

public abstract class RestaurantePresenter {

    public static RestauranteDTO toDTO(Restaurante restaurante) {
        if(restaurante == null) {
            return null;
        }
        Set<EnderecoDTO> enderecosDTO = restaurante.getEnderecos().stream()
                .map(EnderecoPresenter::toDTO)
                .collect(Collectors.toSet());

        return new RestauranteDTO(
                restaurante.getNome(),
                restaurante.getTipoCozinha().getDescricao(),
                restaurante.getHorarioFuncionamento(),
                restaurante.getContato(),
                enderecosDTO,
                restaurante.getDonoRestaurante().getNome()
        );
    }

    public static Restaurante toDomain(RestauranteEntity restauranteEntity) {
        if (restauranteEntity == null) {
            return null;
        }

        var restaurante = toDomainWithoutItens(restauranteEntity);

        if (!ObjectUtils.isEmpty(restauranteEntity.getEnderecoEntities())) {
            restauranteEntity.getEnderecoEntities().forEach(enderecoEntity -> {
                restaurante.adicionarEndereco(Endereco.criarEndereco(
                        enderecoEntity.getRua(),
                        enderecoEntity.getCidade(),
                        enderecoEntity.getEstado(),
                        enderecoEntity.getCep(),
                        enderecoEntity.getNumero(),
                        enderecoEntity.getComplemento()
                ));
            });
        }

        if (!ObjectUtils.isEmpty(restauranteEntity.getItensCardapioEntity())) {
            var itens = restauranteEntity.getItensCardapioEntity().stream()
                    .map(ItensCardapioPresenter::toDomain)
                    .collect(Collectors.toSet());
            restaurante.setItensCardapio(itens);
        }

        return restaurante;
    }

    public static Restaurante toDomainWithoutItens(RestauranteEntity restauranteEntity) {
        if (restauranteEntity == null) {
            return null;
        }

        var restaurante = Restaurante.criarRestaurante(
                restauranteEntity.getRestauranteId(),
                restauranteEntity.getNome(),
                restauranteEntity.getTipoCozinha().getDescricao(),
                restauranteEntity.getHorarioFuncionamento(),
                restauranteEntity.getContato(),
                restauranteEntity.getDonoRestaurante() != null ? restauranteEntity.getDonoRestaurante().getNome() : null
        );

        if (restauranteEntity.getDonoRestaurante() != null && restauranteEntity.getDonoRestaurante().getUsuarioId() != null) {
            restaurante.getDonoRestaurante().setUsuarioId(restauranteEntity.getDonoRestaurante().getUsuarioId());
        }

        return restaurante;
    }

    public static RestauranteEntity toEntity(Restaurante restaurante) {
        if(restaurante == null) {
            return null;
        }

        UsuarioEntity usuarioEntity = UsuarioPresenter.toEntity(restaurante.getDonoRestaurante());

        var restauranteEntity = new RestauranteEntity(
                restaurante.getRestauranteId(),
                restaurante.getNome(),
                restaurante.getTipoCozinha(),
                restaurante.getHorarioFuncionamento(),
                restaurante.getContato()
        );

        restauranteEntity.vincularUsuario(usuarioEntity);

        if (!ObjectUtils.isEmpty(restaurante.getEnderecos())) {
            Set<EnderecoEntity> enderecoEntity = restaurante.getEnderecos()
                    .stream()
                    .map(endereco -> EnderecoPresenter.toEntity(endereco, null,restauranteEntity))
                    .collect(Collectors.toSet());

            restauranteEntity.vincularEndereco(enderecoEntity);
        }
        return restauranteEntity;
    }
}
