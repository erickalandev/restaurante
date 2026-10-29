package com.tech.challenge.fiap.gestao.restaurante.application.usecases.restaurante;

import com.tech.challenge.fiap.gestao.restaurante.application.usecases.restaurante.DeletarRestauranteClienteUseCase;
import com.tech.challenge.fiap.gestao.restaurante.domain.entities.Restaurante;
import com.tech.challenge.fiap.gestao.restaurante.exception.RestauranteNaoEncontradoException;
import com.tech.challenge.fiap.gestao.restaurante.domain.repository.IRestauranteGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DeletarRestauranteClienteUseCaseTest {

    @Mock
    private IRestauranteGateway restauranteGateway;

    private DeletarRestauranteClienteUseCase useCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        useCase = DeletarRestauranteClienteUseCase.deletar(restauranteGateway);
    }

    @Test
    void deveDeletarRestauranteQuandoExistir() {
        Long restauranteId = 10L;
        Restaurante restaurante = Restaurante.criarRestaurante(
                null,
                "La Mesa",
                "Italiana",
                "11h às 23h",
                "(11) 3456-7890",
                "Carlos"
        );
        restaurante.setRestauranteId(restauranteId);

        when(restauranteGateway.buscarRestaurantePorNomeEDono(restaurante.getNome(), restaurante.getDonoRestaurante().getNome())).thenReturn(restaurante);

        useCase.executar(restaurante.getNome(), restaurante.getDonoRestaurante().getNome());

        verify(restauranteGateway).buscarRestaurantePorNomeEDono(restaurante.getNome(), restaurante.getDonoRestaurante().getNome());
        verify(restauranteGateway).deletarRestaurante(restauranteId);
    }

    @Test
    void deveLancarExcecaoQuandoRestauranteNaoExiste() {
        String nome = "La Mesa";
        String dono = "Carlos";

        when(restauranteGateway.buscarRestaurantePorNomeEDono(nome, dono)).thenReturn(null);

        assertThrows(
                RestauranteNaoEncontradoException.class,
                () -> useCase.executar(nome, dono)
        );
    }
}