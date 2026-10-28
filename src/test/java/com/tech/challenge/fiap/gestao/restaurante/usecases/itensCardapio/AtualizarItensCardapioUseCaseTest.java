package com.tech.challenge.fiap.gestao.restaurante.usecases.itensCardapio;

import com.tech.challenge.fiap.gestao.restaurante.application.dtos.ItensCardapioDTO;
import com.tech.challenge.fiap.gestao.restaurante.application.usecases.itensCardapio.AtualizarItensCardapioUseCase;
import com.tech.challenge.fiap.gestao.restaurante.domain.entities.ItensCardapio;
import com.tech.challenge.fiap.gestao.restaurante.domain.entities.Restaurante;
import com.tech.challenge.fiap.gestao.restaurante.exception.ItemCardapioNaoEncontradoException;
import com.tech.challenge.fiap.gestao.restaurante.domain.repository.IItensCardapioGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

class AtualizarItensCardapioUseCaseTest {

    @Mock
    private IItensCardapioGateway gateway;

    private AtualizarItensCardapioUseCase useCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        useCase = AtualizarItensCardapioUseCase.atualizar(gateway);
    }

    @Test
    void deveAtualizarItemQuandoCodigoExiste() {
        Long codigoItemCardapio = 10L;
        Restaurante restaurante = Restaurante.criarRestaurante(
                "La Mesa",
                "Italiana",
                "11h às 23h",
                "(11) 3456-7890",
                "Carlos"
        );

        ItensCardapio itemExistente = ItensCardapio.criarItemCardapio(
                "Pizza Margherita",
                new BigDecimal("35.00"),
                "Pizza clássica",
                true,
                "pizza.jpg"
        );
        itemExistente.setCodigoItemCardapio(codigoItemCardapio);
        itemExistente.setRestaurante(restaurante);

        ItensCardapioDTO dto = new ItensCardapioDTO(
                codigoItemCardapio,
                "Pizza Pepperoni",
                new BigDecimal("45.50"),
                "Pizza com pepperoni",
                false,
                "pepperoni.jpg"
        );

        ItensCardapio itemAtualizado = ItensCardapio.atualizarItemCardapio(
                codigoItemCardapio,
                dto.nome(),
                dto.preco(),
                dto.descricao(),
                dto.disponibilidadeComercioLocal(),
                dto.foto()
        );
        itemAtualizado.setRestaurante(restaurante);

        when(gateway.buscarPorId(codigoItemCardapio)).thenReturn(itemExistente);
        when(gateway.atualizarItemCardapio(any(ItensCardapio.class))).thenReturn(itemAtualizado);

        ItensCardapio resultado = useCase.executar(dto);

        assertEquals(codigoItemCardapio, resultado.getCodigoItemCardapio());
        assertEquals("Pizza Pepperoni", resultado.getNome());
        assertEquals(new BigDecimal("45.50"), resultado.getPreco());
        assertEquals("Pizza com pepperoni", resultado.getDescricao());
        assertEquals(Boolean.FALSE, resultado.getDisponibilidadeComercioLocal());
        assertEquals("pepperoni.jpg", resultado.getFoto());
        verify(gateway).buscarPorId(codigoItemCardapio);
        verify(gateway).atualizarItemCardapio(any(ItensCardapio.class));
    }

    @Test
    void deveLancarExcecaoQuandoItemNaoExiste() {
        Long codigoItemCardapio = 99L;
        ItensCardapioDTO dto = new ItensCardapioDTO(
                codigoItemCardapio,
                "Pizza Frango",
                new BigDecimal("40.00"),
                "Pizza de frango",
                true,
                "frango.jpg"
        );

        when(gateway.buscarPorId(codigoItemCardapio)).thenReturn(null);

        ItemCardapioNaoEncontradoException exception = assertThrows(
                ItemCardapioNaoEncontradoException.class,
                () -> useCase.executar(dto)
        );

        assertEquals(
                "Nao foi encontrado item do cardapio para o codigo=" + codigoItemCardapio,
                exception.getMessage()
        );
    }

    @Test
    void deveLancarExcecaoQuandoCodigoItemNaoExiste() {
        ItensCardapioDTO dto = new ItensCardapioDTO(
                null,
                "Pizza Frango",
                new BigDecimal("40.00"),
                "Pizza de frango",
                true,
                "frango.jpg"
        );

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> useCase.executar(dto)
        );

        assertEquals(
                "Código do item do cardápio é obrigatório",
                exception.getMessage()
        );
    }
}