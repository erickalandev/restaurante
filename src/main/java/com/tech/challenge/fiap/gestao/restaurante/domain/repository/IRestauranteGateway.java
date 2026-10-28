package com.tech.challenge.fiap.gestao.restaurante.domain.repository;

import com.tech.challenge.fiap.gestao.restaurante.application.dtos.ConsultarRestauranteDTO;
import com.tech.challenge.fiap.gestao.restaurante.domain.entities.Restaurante;

import java.util.List;

public interface IRestauranteGateway {
    Restaurante buscarRestaurantePorNomeEDono(String nomeRestaurante, String donoRestaurante);
    List<Restaurante> buscarRestauranteComFiltro(ConsultarRestauranteDTO consultaDTO);
    Restaurante cadastrarRestaurante(Restaurante restaurante);
    Restaurante atualizarRestaurante(Restaurante restaurante);
    void deletarRestaurante(Long restauranteId);
}
