package com.tech.challenge.fiap.gestao.restaurante.application.usecases.restaurante;

import com.tech.challenge.fiap.gestao.restaurante.domain.entities.Restaurante;
import com.tech.challenge.fiap.gestao.restaurante.exception.RestauranteNaoEncontradoException;
import com.tech.challenge.fiap.gestao.restaurante.domain.repository.IRestauranteGateway;

public class DeletarRestauranteClienteUseCase {

    private final IRestauranteGateway restauranteGateway;

    public DeletarRestauranteClienteUseCase(IRestauranteGateway restauranteGateway) {
        this.restauranteGateway = restauranteGateway;
    }

    public static DeletarRestauranteClienteUseCase deletar(IRestauranteGateway restauranteGateway) {
        return new DeletarRestauranteClienteUseCase(restauranteGateway);
    }

    public void executar(String nomeRestaurante, String donoRestaurante) {
        final Restaurante restauranteExistente = restauranteGateway.buscarRestaurantePorNomeEDono(nomeRestaurante, donoRestaurante);
        if (restauranteExistente == null) {
            throw new RestauranteNaoEncontradoException("Restaurante nao cadastrado.");
        }
        restauranteGateway.deletarRestaurante(restauranteExistente.getRestauranteId());
    }
}
