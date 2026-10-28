package com.tech.challenge.fiap.gestao.restaurante.usecases.itensCardapio;

import com.tech.challenge.fiap.gestao.restaurante.application.usecases.itensCardapio.ConsultarItensCardapioUseCase;
import com.tech.challenge.fiap.gestao.restaurante.domain.entities.ItensCardapio;
import com.tech.challenge.fiap.gestao.restaurante.domain.entities.Restaurante;
import com.tech.challenge.fiap.gestao.restaurante.exception.RestauranteNaoEncontradoException;
import com.tech.challenge.fiap.gestao.restaurante.domain.repository.IRestauranteGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ConsultarItensCardapioUseCaseTest {

    @Mock
    private IRestauranteGateway restauranteGateway;

    private ConsultarItensCardapioUseCase useCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        useCase = ConsultarItensCardapioUseCase.consultar(restauranteGateway);
    }

    @Test
    void deveConsultarItensOrdenadosPorNome() {
        Restaurante restaurante = Restaurante.criarRestaurante(
                null,
                "La Mesa",
                "Italiana",
                "11h às 23h",
                "(11) 3456-7890",
                "Carlos"
        );

        ItensCardapio item2 = ItensCardapio.criarItemCardapio(
                "Pizza Pepperoni",
                new BigDecimal("45.50"),
                "Pizza com pepperoni",
                true,
                "pepperoni.jpg"
        );
        ItensCardapio item1 = ItensCardapio.criarItemCardapio(
                "Pastel de Camarão",
                new BigDecimal("30.00"),
                "Pastel crocante",
                true,
                "pastel.jpg"
        );

        restaurante.setItensCardapio(Set.of(item2, item1));

        when(restauranteGateway.buscarRestaurantePorNomeEDono("La Mesa", "Carlos")).thenReturn(restaurante);

        List<ItensCardapio> resultado = useCase.executar("La Mesa", "Carlos");

        assertEquals(2, resultado.size());
        assertEquals("Pastel de Camarão", resultado.get(0).getNome());
        assertEquals("Pizza Pepperoni", resultado.get(1).getNome());
        verify(restauranteGateway).buscarRestaurantePorNomeEDono("La Mesa", "Carlos");
    }

    @Test
    void deveLancarExcecaoQuandoRestauranteNaoRetornaConsulta() {
        when(restauranteGateway.buscarRestaurantePorNomeEDono("Restaurante Inexistente", "Carlos"))
                .thenReturn(null);

        RestauranteNaoEncontradoException exception = assertThrows(
                RestauranteNaoEncontradoException.class,
                () -> useCase.executar("Restaurante Inexistente", "Carlos")
        );

        assertEquals(
                "Restaurante nao encontrado para os valores: nome=Restaurante Inexistente Dono=Carlos",
                exception.getMessage()
        );
    }
}