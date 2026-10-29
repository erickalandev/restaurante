package com.tech.challenge.fiap.gestao.restaurante.presenter;

import com.tech.challenge.fiap.gestao.restaurante.application.dtos.RestauranteDTO;
import com.tech.challenge.fiap.gestao.restaurante.domain.entities.Endereco;
import com.tech.challenge.fiap.gestao.restaurante.domain.entities.ItensCardapio;
import com.tech.challenge.fiap.gestao.restaurante.domain.entities.Restaurante;
import com.tech.challenge.fiap.gestao.restaurante.enums.TipoCozinhaEnum;
import com.tech.challenge.fiap.gestao.restaurante.enums.TipoUsuarioEnum;
import com.tech.challenge.fiap.gestao.restaurante.infraestrutura.model.EnderecoEntity;
import com.tech.challenge.fiap.gestao.restaurante.infraestrutura.model.ItensCardapioEntity;
import com.tech.challenge.fiap.gestao.restaurante.infraestrutura.model.RestauranteEntity;
import com.tech.challenge.fiap.gestao.restaurante.infraestrutura.model.UsuarioEntity;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantePresenterTest {

    @Test
    void deveRetornarNullQuandoRestauranteForaNulo() {
        assertNull(RestaurantePresenter.toDTO(null));
        assertNull(RestaurantePresenter.toDomain(null));
        assertNull(RestaurantePresenter.toDomainWithoutItens(null));
        assertNull(RestaurantePresenter.toEntity(null));
    }

    @Test
    void deveConverterRestauranteParaDTO() {
        var restaurante = Restaurante.criarRestaurante(5L, "La Mesa", "Italiana", "12h às 23h", "11999999999", "Carlos");
        restaurante.adicionarEndereco(Endereco.criarEndereco("Rua B", "São Paulo", "SP", "01311-100", "200", "Bloco B"));

        RestauranteDTO dto = RestaurantePresenter.toDTO(restaurante);

        assertAll(
                () -> assertNotNull(dto),
                () -> assertEquals("La Mesa", dto.nome()),
                () -> assertEquals("Italiana", dto.tipoCozinha()),
                () -> assertEquals("12h às 23h", dto.horarioFuncionamento()),
                () -> assertEquals("11999999999", dto.contato()),
                () -> assertEquals("Carlos", dto.donoRestaurante()),
                () -> assertEquals(1, dto.enderecos().size()),
                () -> assertEquals("Rua B", dto.enderecos().iterator().next().rua())
        );
    }

    @Test
    void deveConverterEntityParaDomainComEnderecoEItens() {
        var usuarioEntity = new UsuarioEntity(99L, "Carlos", null, null, null, TipoUsuarioEnum.DONO_RESTAURANTE);
        var restauranteEntity = new RestauranteEntity(5L, "La Mesa", TipoCozinhaEnum.ITALIANA, "12h às 23h", "11999999999");
        restauranteEntity.vincularUsuario(usuarioEntity);

        var enderecoEntity = new EnderecoEntity(1L, "Rua B", "São Paulo", "SP", "01311100", "200", "Bloco B", restauranteEntity, null);
        restauranteEntity.vincularEndereco(Set.of(enderecoEntity));

        var itemEntity = new ItensCardapioEntity(7L, "Spaghetti", "Massa ao molho", new BigDecimal("49.90"), true, "foto.jpg", restauranteEntity);
        restauranteEntity.getItensCardapioEntity().add(itemEntity);

        var restaurante = RestaurantePresenter.toDomain(restauranteEntity);

        assertAll(
                () -> assertNotNull(restaurante),
                () -> assertEquals(5L, restaurante.getRestauranteId()),
                () -> assertEquals("La Mesa", restaurante.getNome()),
                () -> assertEquals(TipoCozinhaEnum.ITALIANA, restaurante.getTipoCozinha()),
                () -> assertEquals("12h às 23h", restaurante.getHorarioFuncionamento()),
                () -> assertEquals("11999999999", restaurante.getContato()),
                () -> assertEquals(1, restaurante.getEnderecos().size()),
                () -> assertEquals("Rua B", restaurante.getEnderecos().iterator().next().getRua()),
                () -> assertEquals(1, restaurante.getItensCardapio().size()),
                () -> assertEquals("Spaghetti", restaurante.getItensCardapio().iterator().next().getNome())
        );
    }

    @Test
    void deveConverterEntityParaDomainWithoutItens() {
        var usuarioEntity = new UsuarioEntity(44L, "Carlos", null, null, null, TipoUsuarioEnum.DONO_RESTAURANTE);
        var restauranteEntity = new RestauranteEntity(8L, "Sabor do Sul", TipoCozinhaEnum.BRASILEIRA, "Todos os dias", "51999999999");
        restauranteEntity.vincularUsuario(usuarioEntity);

        var restaurante = RestaurantePresenter.toDomainWithoutItens(restauranteEntity);

        assertAll(
                () -> assertNotNull(restaurante),
                () -> assertEquals(8L, restaurante.getRestauranteId()),
                () -> assertEquals("Sabor do Sul", restaurante.getNome()),
                () -> assertEquals(TipoCozinhaEnum.BRASILEIRA, restaurante.getTipoCozinha()),
                () -> assertEquals("Todos os dias", restaurante.getHorarioFuncionamento()),
                () -> assertEquals("51999999999", restaurante.getContato()),
                () -> assertNotNull(restaurante.getDonoRestaurante()),
                () -> assertEquals(44L, restaurante.getDonoRestaurante().getUsuarioId())
        );
    }

    @Test
    void deveConverterEntityParaDomainWithoutItensQuandoDonoForNulo() {
        var restauranteEntity = new RestauranteEntity(9L, "Casa do Chef", TipoCozinhaEnum.FRANCESA, "24h", "11977776666");
        var usuarioDono = com.tech.challenge.fiap.gestao.restaurante.domain.entities.Usuario.criarUsuarioDono("Carlos");
        restauranteEntity.vincularUsuario(new com.tech.challenge.fiap.gestao.restaurante.infraestrutura.model.UsuarioEntity(10L, "Carlos", null, null, null, TipoUsuarioEnum.DONO_RESTAURANTE));

        var restaurante = RestaurantePresenter.toDomainWithoutItens(restauranteEntity);

        assertAll(
                () -> assertNotNull(restaurante),
                () -> assertEquals("Casa do Chef", restaurante.getNome()),
                () -> assertNotNull(restaurante.getDonoRestaurante()),
                () -> assertEquals("Carlos", restaurante.getDonoRestaurante().getNome())
        );
    }

    @Test
    void deveConverterRestauranteParaEntity() {
        var restaurante = Restaurante.criarRestaurante(6L, "Sabor do Sul", "Brasileira", "Segunda a Domingo", "51984612011", "Maria");
        restaurante.adicionarEndereco(Endereco.criarEndereco("Rua C", "Porto Alegre", "RS", "90010-030", "345", "Sala 2"));

        var entity = RestaurantePresenter.toEntity(restaurante);

        assertAll(
                () -> assertNotNull(entity),
                () -> assertEquals(6L, entity.getRestauranteId()),
                () -> assertEquals("Sabor do Sul", entity.getNome()),
                () -> assertEquals(TipoCozinhaEnum.BRASILEIRA, entity.getTipoCozinha()),
                () -> assertEquals("Segunda a Domingo", entity.getHorarioFuncionamento()),
                () -> assertEquals("51984612011", entity.getContato()),
                () -> assertNotNull(entity.getDonoRestaurante()),
                () -> assertEquals("Maria", entity.getDonoRestaurante().getNome()),
                () -> assertEquals(1, entity.getEnderecoEntities().size())
        );
    }
}
