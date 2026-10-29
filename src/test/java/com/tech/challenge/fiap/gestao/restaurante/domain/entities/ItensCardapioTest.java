package com.tech.challenge.fiap.gestao.restaurante.domain.entities;

import com.tech.challenge.fiap.gestao.restaurante.domain.entities.ItensCardapio;
import com.tech.challenge.fiap.gestao.restaurante.domain.entities.Restaurante;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ItensCardapioTest {

    @Test
    void deveCriarItemCardapioComDadosValidos() {
        ItensCardapio item = ItensCardapio.criarItemCardapio(
                "Pizza Margherita",
                new BigDecimal("39.90"),
                "Pizza com molho de tomate, mussarela e manjericão",
                true,
                "pizza.jpg"
        );

        assertAll(
                () -> assertNotNull(item),
                () -> assertEquals("Pizza Margherita", item.getNome()),
                () -> assertEquals(new BigDecimal("39.90"), item.getPreco()),
                () -> assertEquals("Pizza com molho de tomate, mussarela e manjericão", item.getDescricao()),
                () -> assertTrue(item.getDisponibilidadeComercioLocal()),
                () -> assertEquals("pizza.jpg", item.getFoto())
        );
    }

    @Test
    void deveAtualizarItemCardapioComDadosValidos() {
        ItensCardapio item = ItensCardapio.atualizarItemCardapio(
                12L,
                "Lasanha",
                new BigDecimal("52.50"),
                "Lasanha ao molho bolonhesa",
                false,
                "lasanha.jpg"
        );

        assertAll(
                () -> assertEquals(12L, item.getCodigoItemCardapio()),
                () -> assertEquals("Lasanha", item.getNome()),
                () -> assertEquals(new BigDecimal("52.50"), item.getPreco()),
                () -> assertEquals("Lasanha ao molho bolonhesa", item.getDescricao()),
                () -> assertFalse(item.getDisponibilidadeComercioLocal()),
                () -> assertEquals("lasanha.jpg", item.getFoto())
        );
    }

    @Test
    void deveLancarExcecaoQuandoNomeForVazio() {
        assertThrows(IllegalArgumentException.class, () -> ItensCardapio.criarItemCardapio(null, new BigDecimal("20.00"), "desc", true, "foto.jpg"));
        assertThrows(IllegalArgumentException.class, () -> ItensCardapio.criarItemCardapio("   ", new BigDecimal("20.00"), "desc", true, "foto.jpg"));
    }

    @Test
    void deveLancarExcecaoQuandoPrecoForNulo() {
        assertThrows(IllegalArgumentException.class, () -> ItensCardapio.criarItemCardapio("Pizza", null, "desc", true, "foto.jpg"));
    }

    @Test
    void deveConverterDisponibilidadeNulaParaFalse() {
        ItensCardapio item = new ItensCardapio();

        item.setDisponibilidadeComercioLocal(null);

        assertFalse(item.getDisponibilidadeComercioLocal());
    }

    @Test
    void deveAdicionarRestauranteAoItem() {
        Restaurante restaurante = Restaurante.criarRestaurante(
                null,
                "La Mesa",
                "Italiana",
                "11h às 23h",
                "(11) 3456-7890",
                "Carlos"
        );
        ItensCardapio item = ItensCardapio.criarItemCardapio(
                "Pizza",
                new BigDecimal("25.00"),
                "Pizza de calabresa",
                true,
                "pizza.jpg"
        );

        ItensCardapio itemComRestaurante = item.adicionarRestaurante(restaurante);

        assertAll(
                () -> assertSame(item, itemComRestaurante),
                () -> assertNotNull(item.getRestaurante()),
                () -> assertEquals("La Mesa", item.getRestaurante().getNome()),
                () -> assertEquals("Carlos", item.getRestaurante().getDonoRestaurante().getNome())
        );
    }
}
