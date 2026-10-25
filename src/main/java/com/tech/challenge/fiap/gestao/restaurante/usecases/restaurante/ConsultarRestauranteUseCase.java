package com.tech.challenge.fiap.gestao.restaurante.usecases.restaurante;

import com.tech.challenge.fiap.gestao.restaurante.dtos.ConsultarRestauranteDTO;
import com.tech.challenge.fiap.gestao.restaurante.dtos.ConsultarRestauranteResponse;
import com.tech.challenge.fiap.gestao.restaurante.entities.ItensCardapio;
import com.tech.challenge.fiap.gestao.restaurante.entities.Restaurante;
import com.tech.challenge.fiap.gestao.restaurante.exception.RestauranteNaoEncontradoException;
import com.tech.challenge.fiap.gestao.restaurante.interfaces.IRestauranteGateway;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Comparator;
import java.util.List;

public class ConsultarRestauranteUseCase {

    private final IRestauranteGateway restauranteGateway;

    public ConsultarRestauranteUseCase(IRestauranteGateway restauranteGateway) {
        this.restauranteGateway = restauranteGateway;
    }

    public static ConsultarRestauranteUseCase consultar(IRestauranteGateway iRestauranteGateway) {
        return new ConsultarRestauranteUseCase(iRestauranteGateway);
    }

    public List<Restaurante> executar(ConsultarRestauranteDTO consultaDTO) {
        return restauranteGateway.buscarRestauranteComFiltro(consultaDTO)
                .stream()
                .toList();
    }

    public Restaurante restaurantePorNomeEDono(String nomeRestaurante, String donoRestaurante) {
        if (StringUtils.hasText(nomeRestaurante) || !StringUtils.hasText(donoRestaurante)) {
            throw new IllegalArgumentException("O nome do Restaurante e o nome do dono sao obrigatorios.");
        }
        Restaurante restaurante = restauranteGateway.buscarRestaurantePorNomeEDono(nomeRestaurante, donoRestaurante);

        if (ObjectUtils.isEmpty(restaurante)) {
            throw new RestauranteNaoEncontradoException("Restaurante nao encontrado para os valores: nome=" + nomeRestaurante + " Dono=" + donoRestaurante);
        }

        return restaurante;
    }
}
