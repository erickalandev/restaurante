package com.tech.challenge.fiap.gestao.restaurante.presenter;

import com.tech.challenge.fiap.gestao.restaurante.application.dtos.ItensCardapioDTO;
import com.tech.challenge.fiap.gestao.restaurante.domain.entities.ItensCardapio;
import com.tech.challenge.fiap.gestao.restaurante.infraestrutura.model.ItensCardapioEntity;

public abstract class ItensCardapioPresenter {

    public static ItensCardapioDTO toDTO(ItensCardapio itensCardapio) {
        if(itensCardapio == null) {
            return null;
        }
        return new ItensCardapioDTO(
                itensCardapio.getCodigoItemCardapio(),
                itensCardapio.getNome(),
                itensCardapio.getPreco(),
                itensCardapio.getDescricao(),
                itensCardapio.getDisponibilidadeComercioLocal(),
                itensCardapio.getFoto()
        );
    }

    public static ItensCardapioEntity toEntity(ItensCardapio itensCardapio) {
        if(itensCardapio == null) {
            return null;
        }
        var restauranteEntity = RestaurantePresenter.toEntity(itensCardapio.getRestaurante());
        return new ItensCardapioEntity(
                itensCardapio.getCodigoItemCardapio(),
                itensCardapio.getNome(),
                itensCardapio.getDescricao(),
                itensCardapio.getPreco(),
                itensCardapio.getDisponibilidadeComercioLocal(),
                itensCardapio.getFoto(),
                restauranteEntity);
    }

    public static ItensCardapio toDomain(ItensCardapioEntity itensCardapioEntity) {
        var itensCardapio = ItensCardapio.criarItemCardapio(itensCardapioEntity.getNome(),
                itensCardapioEntity.getPreco(),
                itensCardapioEntity.getDescricao(),
                itensCardapioEntity.getDisponibilidadeComercioLocal(),
                itensCardapioEntity.getFoto());
        var restaurante = RestaurantePresenter.toDomain(itensCardapioEntity.getRestaurante());
        itensCardapio.adicionarRestaurante(restaurante);

        return itensCardapio;
    }
}
