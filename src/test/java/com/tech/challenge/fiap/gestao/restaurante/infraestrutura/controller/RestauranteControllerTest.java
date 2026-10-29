package com.tech.challenge.fiap.gestao.restaurante.infraestrutura.controller;

import com.tech.challenge.fiap.gestao.restaurante.application.dtos.AtualizarRestauranteDTO;
import com.tech.challenge.fiap.gestao.restaurante.application.dtos.EnderecoDTO;
import com.tech.challenge.fiap.gestao.restaurante.application.dtos.ConsultarRestauranteDTO;
import com.tech.challenge.fiap.gestao.restaurante.application.dtos.RestauranteDTO;
import com.tech.challenge.fiap.gestao.restaurante.domain.entities.Restaurante;
import com.tech.challenge.fiap.gestao.restaurante.domain.repository.IDataSource;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RestauranteControllerTest {

    @Test
    void deveConsultarRestaurantesComSucesso() {
        IDataSource dataSource = mock(IDataSource.class);
        RestauranteController controller = RestauranteController.create(dataSource);

        Restaurante restaurante = Restaurante.criarRestaurante(
                1L,
                "La Mesa",
                "Italiana",
                "11h às 23h",
                "(11) 3456-7890",
                "Carlos"
        );

        when(dataSource.buscarRestauranteComFiltro(
                "La Mesa",
                null,
                null,
                null,
                "Carlos"
        )).thenReturn(Set.of(restaurante));

        ConsultarRestauranteDTO request = new ConsultarRestauranteDTO("La Mesa", null, null, null, "Carlos");

        ResponseEntity<List<RestauranteDTO>> response = controller.consultarRestaurantes(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals("La Mesa", response.getBody().get(0).nome());
    }

    @Test
    void deveCadastrarRestauranteComSucesso() {
        IDataSource dataSource = mock(IDataSource.class);
        RestauranteController controller = RestauranteController.create(dataSource);

        EnderecoDTO endereco = new EnderecoDTO("Rua das Flores", "São Paulo", "SP", "01000-000", "123", "Apto 2");
        RestauranteDTO request = new RestauranteDTO(
                "La Mesa",
                "Italiana",
                "11h às 23h",
                "(11) 3456-7890",
                Set.of(endereco),
                "Carlos"
        );

        Restaurante restauranteSalvo = Restaurante.criarRestaurante(
                1L,
                "La Mesa",
                "Italiana",
                "11h às 23h",
                "(11) 3456-7890",
                "Carlos"
        );
        restauranteSalvo.adicionarEndereco(com.tech.challenge.fiap.gestao.restaurante.domain.entities.Endereco.criarEndereco(
                "Rua das Flores",
                "São Paulo",
                "SP",
                "01000-000",
                "123",
                "Apto 2"
        ));

        when(dataSource.cadastrarRestaurante(org.mockito.ArgumentMatchers.any(Restaurante.class))).thenReturn(restauranteSalvo);

        ResponseEntity<RestauranteDTO> response = controller.cadastrarRestaurante(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("La Mesa", response.getBody().nome());
    }

    @Test
    void deveAtualizarRestauranteComSucesso() {
        IDataSource dataSource = mock(IDataSource.class);
        RestauranteController controller = RestauranteController.create(dataSource);

        EnderecoDTO endereco = new EnderecoDTO("Rua das Flores", "São Paulo", "SP", "01000-000", "123", "Apto 2");
        AtualizarRestauranteDTO request = new AtualizarRestauranteDTO("Italiana", "12h às 23h", "(11) 99999-8888", Set.of(endereco));

        Restaurante restauranteAtualizado = Restaurante.criarRestaurante(
                1L,
                "La Mesa",
                "Italiana",
                "12h às 23h",
                "(11) 99999-8888",
                "Carlos"
        );
        restauranteAtualizado.adicionarEndereco(com.tech.challenge.fiap.gestao.restaurante.domain.entities.Endereco.criarEndereco(
                "Rua das Flores",
                "São Paulo",
                "SP",
                "01000-000",
                "123",
                "Apto 2"
        ));

        when(dataSource.buscarRestaurantePorNomeEDono("La Mesa", "Carlos")).thenReturn(restauranteAtualizado);
        when(dataSource.atualizarRestaurante(org.mockito.ArgumentMatchers.any(Restaurante.class))).thenReturn(restauranteAtualizado);

        ResponseEntity<RestauranteDTO> response = controller.atualizarRestaurante("La Mesa", "Carlos", request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("12h às 23h", response.getBody().horarioFuncionamento());
    }
}
