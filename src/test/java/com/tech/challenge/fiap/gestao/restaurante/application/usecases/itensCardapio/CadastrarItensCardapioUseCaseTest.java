package com.tech.challenge.fiap.gestao.restaurante.application.usecases.itensCardapio;

import com.tech.challenge.fiap.gestao.restaurante.application.dtos.ItensCardapioDTO;
import com.tech.challenge.fiap.gestao.restaurante.application.usecases.itensCardapio.CadastrarItensCardapioUseCase;
import com.tech.challenge.fiap.gestao.restaurante.domain.entities.ItensCardapio;
import com.tech.challenge.fiap.gestao.restaurante.domain.entities.Restaurante;
import com.tech.challenge.fiap.gestao.restaurante.exception.RestauranteNaoEncontradoException;
import com.tech.challenge.fiap.gestao.restaurante.domain.repository.IItensCardapioGateway;
import com.tech.challenge.fiap.gestao.restaurante.domain.repository.IRestauranteGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CadastrarItensCardapioUseCaseTest {

    @Mock
    private IItensCardapioGateway itensCardapioGateway;

    @Mock
    private IRestauranteGateway restauranteGateway;

    private CadastrarItensCardapioUseCase useCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        useCase = CadastrarItensCardapioUseCase.criar(itensCardapioGateway, restauranteGateway);
    }

    @Test
    void deveCadastrarItensQuandoRestauranteExiste() {
        Restaurante restaurante = Restaurante.criarRestaurante(
                null,
                "La Mesa",
                "Italiana",
                "11h às 23h",
                "(11) 3456-7890",
                "Carlos"
        );

        ItensCardapioDTO pizza1 = new ItensCardapioDTO(
                null,
                "Pizza Margherita",
                new BigDecimal("35.00"),
                "Pizza clássica",
                true,
                "pizza.jpg"
        );

        ItensCardapioDTO pizza2 = new ItensCardapioDTO(
                null,
                "Pizza Pepperoni",
                new BigDecimal("45.50"),
                "Pizza com pepperoni",
                false,
                "pepperoni.jpg"
        );

        List<ItensCardapio> itensEsperados = List.of(
                ItensCardapio.criarItemCardapio("Pizza Margherita", new BigDecimal("35.00"), "Pizza clássica", true, "pizza.jpg")
                        .adicionarRestaurante(restaurante),
                ItensCardapio.criarItemCardapio("Pizza Pepperoni", new BigDecimal("45.50"), "Pizza com pepperoni", false, "pepperoni.jpg")
                        .adicionarRestaurante(restaurante)
        );

        when(restauranteGateway.buscarRestaurantePorNomeEDono("La Mesa", "Carlos")).thenReturn(restaurante);
        when(itensCardapioGateway.salvarTodosItens(anyList())).thenReturn(itensEsperados);

        List<ItensCardapio> resultado = useCase.executar("La Mesa", "Carlos", List.of(pizza1, pizza2));

        assertEquals(2, resultado.size());
        assertEquals("La Mesa", resultado.get(0).getRestaurante().getNome());
        assertEquals("Pizza Margherita", resultado.get(0).getNome());
        assertEquals("Pizza Pepperoni", resultado.get(1).getNome());
        verify(restauranteGateway).buscarRestaurantePorNomeEDono("La Mesa", "Carlos");
        verify(itensCardapioGateway).salvarTodosItens(anyList());
    }

    @Test
    void deveLancarExcecaoQuandoRestauranteNaoExiste() {
        when(restauranteGateway.buscarRestaurantePorNomeEDono("Restaurante Inexistente", "Carlos"))
                .thenReturn(null);

        ItensCardapioDTO pizza = new ItensCardapioDTO(
                null,
                "Pizza Margherita",
                new BigDecimal("35.00"),
                "Pizza clássica",
                true,
                "pizza.jpg"
        );

        assertThrows(
                RestauranteNaoEncontradoException.class,
                () -> useCase.executar("Restaurante Inexistente", "Carlos", List.of(pizza))
        );
    }
}