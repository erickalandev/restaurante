package com.tech.challenge.fiap.gestao.restaurante.application.usecases.itensCardapio;

import com.tech.challenge.fiap.gestao.restaurante.application.usecases.itensCardapio.DeletarItensCardapioUseCase;
import com.tech.challenge.fiap.gestao.restaurante.domain.entities.ItensCardapio;
import com.tech.challenge.fiap.gestao.restaurante.exception.ItemCardapioNaoEncontradoException;
import com.tech.challenge.fiap.gestao.restaurante.domain.repository.IItensCardapioGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class DeletarItensCardapioUseCaseTest {

    @Mock
    private IItensCardapioGateway itensCardapioGateway;

    private DeletarItensCardapioUseCase useCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        useCase = DeletarItensCardapioUseCase.deletar(itensCardapioGateway);
    }

    @Test
    void deveDeletarItemQuandoCodigoExiste() {
        Long codigoItemCardapio = 10L;

        ItensCardapio item = ItensCardapio.criarItemCardapio(
                "Pizza",
                new BigDecimal("45.90"),
                "Pizza de calabresa",
                true,
                "pizza.jpg"
        );
        item.setCodigoItemCardapio(codigoItemCardapio);

        when(itensCardapioGateway.buscarPorId(codigoItemCardapio)).thenReturn(item);

        useCase.executar(codigoItemCardapio);

        verify(itensCardapioGateway).buscarPorId(codigoItemCardapio);
        verify(itensCardapioGateway).deletarItem(codigoItemCardapio);
    }

    @Test
    void deveLancarExcecaoQuandoItemNaoExiste() {
        Long codigoItemCardapio = 99L;

        when(itensCardapioGateway.buscarPorId(codigoItemCardapio)).thenReturn(null);

        ItemCardapioNaoEncontradoException exception = assertThrows(
                ItemCardapioNaoEncontradoException.class,
                () -> useCase.executar(codigoItemCardapio)
        );

        assertEquals(
                "Nao foi encontrado item do cardapio para o codigo=" + codigoItemCardapio,
                exception.getMessage()
        );

        verify(itensCardapioGateway).buscarPorId(codigoItemCardapio);
        verify(itensCardapioGateway, never()).deletarItem(anyLong());
    }
}