package com.tech.challenge.fiap.gestao.restaurante.usecases.restaurante;

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
                "La Mesa",
                "Italiana",
                "11h às 23h",
                "(11) 3456-7890",
                "Carlos"
        );
        restaurante.setRestauranteId(restauranteId);

        when(restauranteGateway.buscarPorId(restauranteId)).thenReturn(restaurante);

        useCase.executar(restauranteId);

        verify(restauranteGateway).buscarPorId(restauranteId);
        verify(restauranteGateway).deletarRestaurante(restauranteId);
    }

    @Test
    void deveLancarExcecaoQuandoRestauranteNaoExiste() {
        Long restauranteId = 99L;

        when(restauranteGateway.buscarPorId(restauranteId)).thenReturn(null);

        assertThrows(
                RestauranteNaoEncontradoException.class,
                () -> useCase.executar(restauranteId)
        );
    }
}