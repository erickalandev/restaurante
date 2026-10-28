package com.tech.challenge.fiap.gestao.restaurante.application.usecases.itensCardapio;

import com.tech.challenge.fiap.gestao.restaurante.domain.entities.ItensCardapio;
import com.tech.challenge.fiap.gestao.restaurante.domain.entities.Restaurante;
import com.tech.challenge.fiap.gestao.restaurante.domain.repository.IRestauranteGateway;
import com.tech.challenge.fiap.gestao.restaurante.application.usecases.restaurante.ConsultarRestauranteUseCase;
import com.tech.challenge.fiap.gestao.restaurante.utils.FiltroUtils;

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
        String nome = FiltroUtils.normalizarTexto(nomeRestaurante);
        String dono = FiltroUtils.normalizarTexto(donoRestaurante);

        Restaurante restaurante = validarRestaurante(nome, dono);

        return restaurante.getItensCardapio().stream()
                .sorted(Comparator.comparing(ItensCardapio::getNome))
                .toList();
    }

    private Restaurante validarRestaurante(String nome, String dono) {
        return ConsultarRestauranteUseCase.consultar(restauranteGateway)
                .restaurantePorNomeEDono(nome, dono);
    }
}
