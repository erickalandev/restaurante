package com.tech.challenge.fiap.gestao.restaurante.infraestrutura.controller;

import com.tech.challenge.fiap.gestao.restaurante.application.dtos.CadastrarItensCardapioDTO;
import com.tech.challenge.fiap.gestao.restaurante.application.dtos.ItensCardapioDTO;
import com.tech.challenge.fiap.gestao.restaurante.domain.entities.ItensCardapio;
import com.tech.challenge.fiap.gestao.restaurante.domain.entities.Restaurante;
import com.tech.challenge.fiap.gestao.restaurante.domain.repository.IDataSource;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ItensCardapioControllerTest {

    @Test
    void deveCadastrarItensCardapioComSucesso() {
        IDataSource dataSource = mock(IDataSource.class);
        ItensCardapioController controller = ItensCardapioController.create(dataSource);

        Restaurante restaurante = Restaurante.criarRestaurante(
                1L,
                "La Mesa",
                "Italiana",
                "11h Ã s 23h",
                "(11) 3456-7890",
                "Carlos"
        );

        ItensCardapio item1 = ItensCardapio.criarItemCardapio(
                "Pizza Margherita",
                new BigDecimal("35.00"),
                "Pizza clÃ¡ssica",
                true,
                "pizza.jpg"
        );
        item1.setCodigoItemCardapio(10L);
        item1.adicionarRestaurante(restaurante);

        ItensCardapio item2 = ItensCardapio.criarItemCardapio(
                "Pizza Pepperoni",
                new BigDecimal("45.50"),
                "Pizza com pepperoni",
                false,
                "pepperoni.jpg"
        );
        item2.setCodigoItemCardapio(11L);
        item2.adicionarRestaurante(restaurante);

        when(dataSource.buscarRestaurantePorNomeEDono("La Mesa", "Carlos")).thenReturn(restaurante);
        when(dataSource.salvarTodosItensCardapio(anyList())).thenReturn(List.of(item1, item2));

        CadastrarItensCardapioDTO request = new CadastrarItensCardapioDTO(List.of(
                new ItensCardapioDTO(null, "Pizza Margherita", new BigDecimal("35.00"), "Pizza clÃ¡ssica", true, "pizza.jpg"),
                new ItensCardapioDTO(null, "Pizza Pepperoni", new BigDecimal("45.50"), "Pizza com pepperoni", false, "pepperoni.jpg")
        ));

        ResponseEntity<List<ItensCardapioDTO>> response = controller.cadastrarItensCardapio("La Mesa", "Carlos", request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void deveConsultarItensCardapioComSucesso() {
        IDataSource dataSource = mock(IDataSource.class);
        ItensCardapioController controller = ItensCardapioController.create(dataSource);

        Restaurante restaurante = Restaurante.criarRestaurante(
                1L,
                "La Mesa",
                "Italiana",
                "11h Ã s 23h",
                "(11) 3456-7890",
                "Carlos"
        );

        ItensCardapio item = ItensCardapio.criarItemCardapio(
                "Pizza Margherita",
                new BigDecimal("35.00"),
                "Pizza clÃ¡ssica",
                true,
                "pizza.jpg"
        );
        item.setCodigoItemCardapio(10L);
        item.adicionarRestaurante(restaurante);

        restaurante.setItensCardapio(Set.of(item));
        when(dataSource.buscarRestaurantePorNomeEDono("La Mesa", "Carlos")).thenReturn(restaurante);

        ResponseEntity<List<ItensCardapioDTO>> response = controller.consultarItensCardapio("La Mesa", "Carlos");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals("Pizza Margherita", response.getBody().get(0).nome());
    }

    @Test
    void deveAtualizarItemCardapioComSucesso() {
        IDataSource dataSource = mock(IDataSource.class);
        ItensCardapioController controller = ItensCardapioController.create(dataSource);

        Restaurante restaurante = Restaurante.criarRestaurante(
                1L,
                "La Mesa",
                "Italiana",
                "11h Ã s 23h",
                "(11) 3456-7890",
                "Carlos"
        );

        ItensCardapio itemExistente = ItensCardapio.criarItemCardapio(
                "Pizza Margherita",
                new BigDecimal("35.00"),
                "Pizza clÃ¡ssica",
                true,
                "pizza.jpg"
        );
        itemExistente.setCodigoItemCardapio(10L);
        itemExistente.adicionarRestaurante(restaurante);

        ItensCardapio itemAtualizado = ItensCardapio.criarItemCardapio(
                "Pizza Pepperoni",
                new BigDecimal("45.50"),
                "Pizza com pepperoni",
                false,
                "pepperoni.jpg"
        );
        itemAtualizado.setCodigoItemCardapio(10L);
        itemAtualizado.adicionarRestaurante(restaurante);

        when(dataSource.buscarItensCardapioPorId(10L)).thenReturn(itemExistente);
        when(dataSource.atualizarItemCardapio(any(ItensCardapio.class))).thenReturn(itemAtualizado);

        ItensCardapioDTO dto = new ItensCardapioDTO(
                10L,
                "Pizza Pepperoni",
                new BigDecimal("45.50"),
                "Pizza com pepperoni",
                false,
                "pepperoni.jpg"
        );

        ResponseEntity<ItensCardapioDTO> response = controller.atualizarItensCardapio(dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Pizza Pepperoni", response.getBody().nome());
    }
}


