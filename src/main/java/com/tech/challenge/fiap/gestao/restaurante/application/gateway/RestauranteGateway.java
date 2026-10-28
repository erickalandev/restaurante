package com.tech.challenge.fiap.gestao.restaurante.application.gateway;

import com.tech.challenge.fiap.gestao.restaurante.application.dtos.ConsultarRestauranteDTO;
import com.tech.challenge.fiap.gestao.restaurante.domain.entities.Restaurante;
import com.tech.challenge.fiap.gestao.restaurante.domain.repository.IDataSource;
import com.tech.challenge.fiap.gestao.restaurante.domain.repository.IRestauranteGateway;

import java.util.List;

public class RestauranteGateway implements IRestauranteGateway {

    private final IDataSource dataSource;

    public RestauranteGateway(IDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static RestauranteGateway create(IDataSource dataSource) {
        return new RestauranteGateway(dataSource);
    }

    @Override
    public Restaurante buscarRestaurantePorNomeEDono(String nomeRestaurante, String donoRestaurante) {
        return this.dataSource.buscarRestaurantePorNomeEDono(nomeRestaurante, donoRestaurante);
    }

    @Override
    public List<Restaurante> buscarRestauranteComFiltro(ConsultarRestauranteDTO consultaDTO) {
        return this.dataSource.buscarRestauranteComFiltro(
                consultaDTO.nome(),
                consultaDTO.estado(),
                consultaDTO.cidade(),
                consultaDTO.tipoCozinha(),
                consultaDTO.donoRestaurante()
        ).stream().toList();
    }

    @Override
    public Restaurante cadastrarRestaurante(Restaurante restaurante) {
        return this.dataSource.cadastrarRestaurante(restaurante);
    }

    @Override
    public Restaurante atualizarRestaurante(Restaurante restaurante) {
        return this.dataSource.atualizarRestaurante(restaurante);
    }

    @Override
    public void deletarRestaurante(Long restauranteId) {
        this.dataSource.deletarRestaurante(restauranteId);
    }
}
