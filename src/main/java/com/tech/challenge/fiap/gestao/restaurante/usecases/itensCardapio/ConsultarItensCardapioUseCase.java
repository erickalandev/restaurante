package com.tech.challenge.fiap.gestao.restaurante.usecases.itensCardapio;

import com.tech.challenge.fiap.gestao.restaurante.entities.ItensCardapio;
import com.tech.challenge.fiap.gestao.restaurante.entities.Restaurante;
import com.tech.challenge.fiap.gestao.restaurante.interfaces.IRestauranteGateway;
import com.tech.challenge.fiap.gestao.restaurante.usecases.restaurante.ConsultarRestauranteUseCase;

import java.util.Comparator;
import java.util.List;

public class ConsultarItensCardapioUseCase {

    private final IRestauranteGateway restauranteGateway;

    public ConsultarItensCardapioUseCase(IRestauranteGateway restauranteGateway) {
        this.restauranteGateway = restauranteGateway;
    }

    public static ConsultarItensCardapioUseCase consultar(IRestauranteGateway restauranteGateway) {
        return new ConsultarItensCardapioUseCase(restauranteGateway);
    }

    public List<ItensCardapio> executar(String nomeRestaurante, String donoRestaurante) {
        Restaurante restaurante = ConsultarRestauranteUseCase.consultar(restauranteGateway)
                .restaurantePorNomeEDono(nomeRestaurante,donoRestaurante);
        return restaurante.getItensCardapio().stream()
                .sorted(Comparator.comparing(ItensCardapio::getNome))
                .toList();
    }
}
