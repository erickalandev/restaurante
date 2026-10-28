package com.tech.challenge.fiap.gestao.restaurante.application.usecases.restaurante;

import com.tech.challenge.fiap.gestao.restaurante.application.dtos.ConsultarRestauranteDTO;
import com.tech.challenge.fiap.gestao.restaurante.domain.entities.Restaurante;
import com.tech.challenge.fiap.gestao.restaurante.exception.RestauranteNaoEncontradoException;
import com.tech.challenge.fiap.gestao.restaurante.domain.repository.IRestauranteGateway;
import com.tech.challenge.fiap.gestao.restaurante.utils.FiltroUtils;

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
        if (consultaDTO == null) {
            return restauranteGateway.buscarRestauranteComFiltro(null);
        }

        var filtro = new ConsultarRestauranteDTO(
                FiltroUtils.normalizarTexto(consultaDTO.nome()),
                FiltroUtils.normalizarTexto(consultaDTO.estado()),
                FiltroUtils.normalizarTexto(consultaDTO.cidade()),
                FiltroUtils.normalizarTexto(consultaDTO.tipoCozinha()),
                FiltroUtils.normalizarTexto(consultaDTO.donoRestaurante())
        );

        return restauranteGateway.buscarRestauranteComFiltro(filtro);
    }

    public Restaurante restaurantePorNomeEDono(String nomeRestaurante, String donoRestaurante) {
        String nome = FiltroUtils.normalizarTexto(nomeRestaurante);
        String dono = FiltroUtils.normalizarTexto(donoRestaurante);

        if (nome == null || dono == null) {
            throw new IllegalArgumentException("O nome do Restaurante e o nome do dono sao obrigatorios.");
        }

        Restaurante restaurante = restauranteGateway.buscarRestaurantePorNomeEDono(nome, dono);

        if (restaurante == null) {
            throw new RestauranteNaoEncontradoException(
                    "Restaurante nao encontrado para os valores: nome=" + nome + " Dono=" + dono
            );
        }

        return restaurante;
    }
}
