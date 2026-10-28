package com.tech.challenge.fiap.gestao.restaurante.entities;

import com.tech.challenge.fiap.gestao.restaurante.domain.entities.Endereco;
import com.tech.challenge.fiap.gestao.restaurante.domain.entities.Restaurante;
import com.tech.challenge.fiap.gestao.restaurante.enums.TipoCozinhaEnum;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RestauranteTest {

    @Test
    void deveCriarRestauranteComDadosValidos() {
        Restaurante restaurante = Restaurante.criarRestaurante(
                "La Mesa",
                "Italiana",
                "Segunda a Domingo, 11h às 23h",
                "(11) 3456-7890",
                "Carlos"
        );

        assertAll(
                () -> assertNotNull(restaurante),
                () -> assertEquals("La Mesa", restaurante.getNome()),
                () -> assertEquals(TipoCozinhaEnum.ITALIANA, restaurante.getTipoCozinha()),
                () -> assertEquals("Segunda a Domingo, 11h às 23h", restaurante.getHorarioFuncionamento()),
                () -> assertEquals("1134567890", restaurante.getContato()),
                () -> assertNotNull(restaurante.getDonoRestaurante()),
                () -> assertEquals("Carlos", restaurante.getDonoRestaurante().getNome()),
                () -> assertEquals("DONO_RESTAURANTE", restaurante.getDonoRestaurante().getTipoUsuario().name())
        );
    }

    @Test
    void deveCriarRestauranteComDadosEndereco() {
        Endereco endereco = Endereco.criarEndereco(
                "Rua Doze",
                "Barra Mansa",
                "RJ",
                "27338-220",
                "822",
                null
        );
        Restaurante restaurante = new Restaurante();
        restaurante.adicionarEndereco(endereco);

        Endereco enderecoRestaurante = restaurante.getEnderecos().iterator().next();
        assertAll(
                () -> assertEquals("Rua Doze", enderecoRestaurante.getRua()),
                () -> assertEquals("Barra Mansa", enderecoRestaurante.getCidade()),
                () -> assertEquals("RJ", enderecoRestaurante.getEstado()),
                () -> assertEquals("27338220", enderecoRestaurante.getCep()),
                () -> assertEquals("822", enderecoRestaurante.getNumero()),
                () -> assertNull(enderecoRestaurante.getComplemento())
        );

    }

    @Test
    void deveAtualizarRestauranteComDadosValidos() {
        Restaurante restaurante = Restaurante.atualizarRestaurante(
                7L,
                "Sabor do Sul",
                "Brasileira",
                "Todos os dias, 8h às 22h",
                "(51) 99876-5432",
                "Ana"
        );

        assertAll(
                () -> assertEquals(7L, restaurante.getRestauranteId()),
                () -> assertEquals("Sabor do Sul", restaurante.getNome()),
                () -> assertEquals(TipoCozinhaEnum.BRASILEIRA, restaurante.getTipoCozinha()),
                () -> assertEquals("Todos os dias, 8h às 22h", restaurante.getHorarioFuncionamento()),
                () -> assertEquals("51998765432", restaurante.getContato()),
                () -> assertEquals("Ana", restaurante.getDonoRestaurante().getNome())
        );
    }

    @Test
    void deveLancarExcecaoQuandoNomeForVazio() {
        assertThrows(IllegalArgumentException.class, () -> Restaurante.criarRestaurante(null, "Italiana", "11h às 23h", "(11) 3456-7890", "Carlos"));
        assertThrows(IllegalArgumentException.class, () -> Restaurante.criarRestaurante("", "Italiana", "11h às 23h", "(11) 3456-7890", "Carlos"));
    }

    @Test
    void deveLancarExcecaoQuandoTipoCozinhaForVazio() {
        assertThrows(IllegalArgumentException.class, () -> Restaurante.criarRestaurante("La Mesa", null, "11h às 23h", "(11) 3456-7890", "Carlos"));
        assertThrows(IllegalArgumentException.class, () -> Restaurante.criarRestaurante("La Mesa", "", "11h às 23h", "(11) 3456-7890", "Carlos"));
    }

    @Test
    void deveLancarExcecaoQuandoHorarioForVazio() {
        assertThrows(IllegalArgumentException.class, () -> Restaurante.criarRestaurante("La Mesa", "Italiana", null, "(11) 3456-7890", "Carlos"));
        assertThrows(IllegalArgumentException.class, () -> Restaurante.criarRestaurante("La Mesa", "Italiana", "", "(11) 3456-7890", "Carlos"));
    }

    @Test
    void deveLancarExcecaoQuandoContatoForVazio() {
        assertThrows(IllegalArgumentException.class, () -> Restaurante.criarRestaurante("La Mesa", "Italiana", "11h às 23h", null, "Carlos"));
        assertThrows(IllegalArgumentException.class, () -> Restaurante.criarRestaurante("La Mesa", "Italiana", "11h às 23h", "", "Carlos"));
    }

    @Test
    void deveLancarExcecaoQuandoDonoForVazio() {
        assertThrows(IllegalArgumentException.class, () -> Restaurante.criarRestaurante("La Mesa", "Italiana", "11h às 23h", "(11) 3456-7890", null));
        assertThrows(IllegalArgumentException.class, () -> Restaurante.criarRestaurante("La Mesa", "Italiana", "11h às 23h", "(11) 3456-7890", ""));
    }

    @Test
    void deveLancarExcecaoQuandoTipoCozinhaForInvalido() {
        Restaurante restaurante = new Restaurante();

        assertThrows(IllegalArgumentException.class, () -> restaurante.setTipoCozinha("Tipo inexistente"));
    }

    @Test
    void deveLancarExcecaoQuandoContatoForInvalido() {
        Restaurante restaurante = new Restaurante();

        assertThrows(IllegalArgumentException.class, () -> restaurante.setContato("abc"));
    }

    @Test
    void deveMesclarRestauranteComSucesso() {
        Restaurante restaurante = Restaurante.criarRestaurante(
                "La Mesa",
                "Italiana",
                "11h às 23h",
                "(11) 3456-7890",
                "Carlos"
        );

        Restaurante merge = Restaurante.merge(restaurante);

        assertAll(
                () -> assertNotNull(merge),
                () -> assertNotSame(restaurante, merge),
                () -> assertEquals(restaurante.getNome(), merge.getNome()),
                () -> assertEquals(restaurante.getTipoCozinha(), merge.getTipoCozinha()),
                () -> assertEquals(restaurante.getHorarioFuncionamento(), merge.getHorarioFuncionamento()),
                () -> assertEquals(restaurante.getContato(), merge.getContato()),
                () -> assertEquals(restaurante.getDonoRestaurante().getNome(), merge.getDonoRestaurante().getNome())
        );
    }
}
