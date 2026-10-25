package com.tech.challenge.fiap.gestao.restaurante.interfaces;

import com.tech.challenge.fiap.gestao.restaurante.dtos.ConsultarRestauranteDTO;
import com.tech.challenge.fiap.gestao.restaurante.entities.Restaurante;

import java.util.Set;

public interface IRestauranteGateway {
    Restaurante buscarRestaurantePorNomeEDono(String nomeRestaurante, String donoRestaurante);
    Set<Restaurante> buscarRestauranteComFiltro(ConsultarRestauranteDTO consultaDTO);
    Restaurante cadastrarRestaurante(Restaurante restaurante);
    Restaurante atualizarRestaurante(Restaurante restaurante);
    Restaurante buscarPorId(Long restauranteId);

    void deletarRestaurante(Long restauranteId);
}
