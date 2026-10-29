package com.tech.challenge.fiap.gestao.restaurante.presenter;

import com.tech.challenge.fiap.gestao.restaurante.application.dtos.ItensCardapioDTO;
import com.tech.challenge.fiap.gestao.restaurante.domain.entities.ItensCardapio;
import com.tech.challenge.fiap.gestao.restaurante.domain.entities.Restaurante;
import com.tech.challenge.fiap.gestao.restaurante.enums.TipoCozinhaEnum;
import com.tech.challenge.fiap.gestao.restaurante.infraestrutura.model.ItensCardapioEntity;
import com.tech.challenge.fiap.gestao.restaurante.infraestrutura.model.RestauranteEntity;
import com.tech.challenge.fiap.gestao.restaurante.infraestrutura.model.UsuarioEntity;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ItensCardapioPresenterTest {

    @Test
    void deveRetornarNullQuandoItemForNulo() {
        assertNull(ItensCardapioPresenter.toDTO(null));
        assertNull(ItensCardapioPresenter.toEntity(null));
        assertNull(ItensCardapioPresenter.toDomain(null));
    }

    @Test
    void deveConverterItemParaDTO() {
        var restaurante = Restaurante.criarRestaurante(10L, "La Mesa", "Italiana", "12h às 23h", "11999999999", "Carlos");
        var item = ItensCardapio.criarItemCardapio("Spaghetti", new BigDecimal("49.90"), "Massa ao molho", true, "foto.jpg");
        item.adicionarRestaurante(restaurante);

        ItensCardapioDTO dto = ItensCardapioPresenter.toDTO(item);

        assertAll(
                () -> assertNotNull(dto),
                () -> assertEquals("Spaghetti", dto.nome()),
                () -> assertEquals(new BigDecimal("49.90"), dto.preco()),
                () -> assertEquals("Massa ao molho", dto.descricao()),
                () -> assertTrue(dto.disponibilidadeComercioLocal()),
                () -> assertEquals("foto.jpg", dto.foto())
        );
    }

    @Test
    void deveConverterItemParaEntity() {
        var restaurante = Restaurante.criarRestaurante(10L, "La Mesa", "Italiana", "12h às 23h", "11999999999", "Carlos");
        var item = ItensCardapio.criarItemCardapio("Spaghetti", new BigDecimal("49.90"), "Massa ao molho", true, "foto.jpg");
        item.setCodigoItemCardapio(25L);
        item.adicionarRestaurante(restaurante);

        var entity = ItensCardapioPresenter.toEntity(item);

        assertAll(
                () -> assertNotNull(entity),
                () -> assertEquals(25L, entity.getId()),
                () -> assertEquals("Spaghetti", entity.getNome()),
                () -> assertEquals("Massa ao molho", entity.getDescricao()),
                () -> assertEquals(new BigDecimal("49.90"), entity.getPreco()),
                () -> assertTrue(entity.getDisponibilidadeComercioLocal()),
                () -> assertEquals("foto.jpg", entity.getFoto()),
                () -> assertNotNull(entity.getRestaurante())
        );
    }

    @Test
    void deveConverterEntityParaDomainComRestaurante() {
        var usuarioEntity = new UsuarioEntity(3L, "Carlos", "12345678901", "carlos@email.com", "11988887777", com.tech.challenge.fiap.gestao.restaurante.enums.TipoUsuarioEnum.DONO_RESTAURANTE);
        var restauranteEntity = new RestauranteEntity(10L, "La Mesa", TipoCozinhaEnum.ITALIANA, "12h às 23h", "11999999999");
        restauranteEntity.vincularUsuario(usuarioEntity);

        var entity = new ItensCardapioEntity(25L, "Spaghetti", "Massa ao molho", new BigDecimal("49.90"), true, "foto.jpg", restauranteEntity);

        var domain = ItensCardapioPresenter.toDomain(entity);

        assertAll(
                () -> assertNotNull(domain),
                () -> assertEquals(25L, domain.getCodigoItemCardapio()),
                () -> assertEquals("Spaghetti", domain.getNome()),
                () -> assertEquals("Massa ao molho", domain.getDescricao()),
                () -> assertEquals(new BigDecimal("49.90"), domain.getPreco()),
                () -> assertTrue(domain.getDisponibilidadeComercioLocal()),
                () -> assertEquals("foto.jpg", domain.getFoto()),
                () -> assertNotNull(domain.getRestaurante()),
                () -> assertEquals("La Mesa", domain.getRestaurante().getNome())
        );
    }

    @Test
    void deveConverterEntityParaDomainSemRestaurante() {
        var entity = new ItensCardapioEntity(15L, "Pizza", "Pizza de pepperoni", new BigDecimal("39.90"), false, "pizza.jpg", null);

        var domain = ItensCardapioPresenter.toDomain(entity);

        assertAll(
                () -> assertNotNull(domain),
                () -> assertEquals(15L, domain.getCodigoItemCardapio()),
                () -> assertNull(domain.getRestaurante())
        );
    }
}
