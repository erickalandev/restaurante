package com.tech.challenge.fiap.gestao.restaurante.application.usecases.restaurante;

import com.tech.challenge.fiap.gestao.restaurante.application.dtos.ConsultarRestauranteDTO;
import com.tech.challenge.fiap.gestao.restaurante.application.usecases.restaurante.ConsultarRestauranteUseCase;
import com.tech.challenge.fiap.gestao.restaurante.domain.entities.Restaurante;
import com.tech.challenge.fiap.gestao.restaurante.exception.RestauranteNaoEncontradoException;
import com.tech.challenge.fiap.gestao.restaurante.domain.repository.IRestauranteGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ConsultarRestauranteUseCaseTest {

    @Mock
    private IRestauranteGateway restauranteGateway;

    private ConsultarRestauranteUseCase useCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        useCase = ConsultarRestauranteUseCase.consultar(restauranteGateway);
    }

    @Test
    void deveConsultarRestaurantesComFiltro() {
        Restaurante restaurante1 = Restaurante.criarRestaurante(
                null,
                "La Mesa",
                "Italiana",
                "11h às 23h",
                "(11) 3456-7890",
                "Carlos"
        );
        restaurante1.setRestauranteId(1L);

        Restaurante restaurante2 = Restaurante.criarRestaurante(
                null,
                "Bistro do Chef",
                "Brasileira",
                "12h às 22h",
                "(11) 98765-4321",
                "Ana"
        );
        restaurante2.setRestauranteId(2L);

        List<Restaurante> restaurantes = new ArrayList<>();
        restaurantes.add(restaurante1);
        restaurantes.add(restaurante2);

        ConsultarRestauranteDTO dto = new ConsultarRestauranteDTO("", "", null, null, null);

        when(restauranteGateway.buscarRestauranteComFiltro(any(ConsultarRestauranteDTO.class))).thenReturn(restaurantes);

        List<Restaurante> resultado = useCase.executar(dto);

        assertEquals(2, resultado.size());
        assertEquals("La Mesa", resultado.get(0).getNome());
        assertEquals("Bistro do Chef", resultado.get(1).getNome());
        verify(restauranteGateway).buscarRestauranteComFiltro(any(ConsultarRestauranteDTO.class));
    }

    @Test
    void deveLancarExcecaoQuandoRestauranteNaoExistePorNomeEDono() {
        when(restauranteGateway.buscarRestaurantePorNomeEDono("Restaurante Inexistente", "Carlos")).thenReturn(null);

        RestauranteNaoEncontradoException exception = assertThrows(
                RestauranteNaoEncontradoException.class,
                () -> useCase.restaurantePorNomeEDono("Restaurante Inexistente", "Carlos")
        );

        assertEquals(
                "Restaurante nao encontrado para os valores: nome=Restaurante Inexistente Dono=Carlos",
                exception.getMessage()
        );
    }
}