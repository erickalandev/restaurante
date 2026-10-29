package com.tech.challenge.fiap.gestao.restaurante.infraestrutura.datasource;

import com.tech.challenge.fiap.gestao.restaurante.domain.entities.ItensCardapio;
import com.tech.challenge.fiap.gestao.restaurante.domain.entities.Restaurante;
import com.tech.challenge.fiap.gestao.restaurante.domain.entities.Usuario;
import com.tech.challenge.fiap.gestao.restaurante.enums.TipoCozinhaEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:h2:mem:restaurante_integration;MODE=MySQL;DB_CLOSE_DELAY=-1;DATABASE_TO_UPPER=false",
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
        "spring.jpa.show-sql=false"
})
class GestaoRestauranteDataSourceIntegrationTest {

    @Autowired
    private GestaoRestauranteDataSource dataSource;

    @Test
    @Transactional
    void deveCadastrarEConsultarUsuarioPorCpf() {
        Usuario usuario = Usuario.criarUsuario(
                null,
                "Maria Silva",
                "12345678909",
                "maria@email.com",
                "11987654321",
                com.tech.challenge.fiap.gestao.restaurante.enums.TipoUsuarioEnum.CLIENTE
        );

        Usuario salvo = dataSource.cadastrarUsuario(usuario);
        Usuario consultado = dataSource.buscarUsuarioPorCpf("12345678909");

        assertAll(
                () -> assertNotNull(salvo),
                () -> assertNotNull(salvo.getUsuarioId()),
                () -> assertNotNull(consultado),
                () -> assertEquals("Maria Silva", consultado.getNome()),
                () -> assertEquals("12345678909", consultado.getCpf()),
                () -> assertEquals("maria@email.com", consultado.getEmail())
        );
    }

    @Test
    @Transactional
    void deveBuscarUsuariosComFiltros() {
        dataSource.cadastrarUsuario(Usuario.criarUsuario(
                null,
                "Maria Silva",
                "12345678909",
                "maria@email.com",
                "11987654321",
                com.tech.challenge.fiap.gestao.restaurante.enums.TipoUsuarioEnum.CLIENTE
        ));
        dataSource.cadastrarUsuario(Usuario.criarUsuario(
                null,
                "Joao Silva",
                "98765432100",
                "joao@email.com",
                "11999887766",
                com.tech.challenge.fiap.gestao.restaurante.enums.TipoUsuarioEnum.CLIENTE
        ));

        Set<Usuario> usuarios = dataSource.buscarUsuarios("Maria", "12345678909", "maria@email.com", "11987654321");

        assertAll(
                () -> assertEquals(1, usuarios.size()),
                () -> assertTrue(usuarios.stream().anyMatch(u -> u.getNome().equals("Maria Silva")))
        );
    }

    @Test
    @Transactional
    void deveAtualizarUsuarioCliente() {
        Usuario salvo = dataSource.cadastrarUsuario(Usuario.criarUsuario(
                null,
                "Maria Silva",
                "12345678909",
                "maria@email.com",
                "11987654321",
                com.tech.challenge.fiap.gestao.restaurante.enums.TipoUsuarioEnum.CLIENTE
        ));

        Usuario atualizado = Usuario.criarUsuario(
                salvo.getUsuarioId(),
                "Maria Souza",
                "12345678909",
                "maria.souza@email.com",
                "11887766554",
                com.tech.challenge.fiap.gestao.restaurante.enums.TipoUsuarioEnum.CLIENTE
        );

        Usuario retorno = dataSource.atualizarUsuario(atualizado);
        Usuario consultado = dataSource.buscarUsuarioPorCpf("12345678909");

        assertAll(
                () -> assertNotNull(retorno),
                () -> assertEquals("Maria Souza", consultado.getNome()),
                () -> assertEquals("maria.souza@email.com", consultado.getEmail()),
                () -> assertEquals("11887766554", consultado.getContato())
        );
    }

    @Test
    @Transactional
    void deveDeletarUsuarioPorId() {
        Usuario salvo = dataSource.cadastrarUsuario(Usuario.criarUsuario(
                null,
                "Maria para deletar",
                "11144477735",
                "deletar@email.com",
                "11990001122",
                com.tech.challenge.fiap.gestao.restaurante.enums.TipoUsuarioEnum.CLIENTE
        ));

        dataSource.deletarUsuarioPorId(salvo.getUsuarioId());

        assertNull(dataSource.buscarUsuarioPorCpf("11144477735"));
    }

    @Test
    @Transactional
    void deveCadastrarEConsultarRestaurantePorNomeEDono() {
        Usuario dono = dataSource.cadastrarUsuario(Usuario.criarUsuarioDono("Carlos Souza"));

        Restaurante restaurante = Restaurante.criarRestaurante(
                null,
                "La Mesa",
                "Italiana",
                "11h às 23h",
                "1134567890",
                dono.getNome()
        );

        Restaurante salvo = dataSource.cadastrarRestaurante(restaurante);
        Restaurante consultado = dataSource.buscarRestaurantePorNomeEDono("La Mesa", "Carlos Souza");

        assertAll(
                () -> assertNotNull(salvo),
                () -> assertNotNull(salvo.getRestauranteId()),
                () -> assertNotNull(consultado),
                () -> assertEquals("La Mesa", consultado.getNome()),
                () -> assertEquals(TipoCozinhaEnum.ITALIANA, consultado.getTipoCozinha()),
                () -> assertEquals("Carlos Souza", consultado.getDonoRestaurante().getNome())
        );
    }

    @Test
    @Transactional
    void deveBuscarRestauranteComFiltro() {
        dataSource.cadastrarRestaurante(Restaurante.criarRestaurante(
                null,
                "La Mesa",
                "Italiana",
                "11h às 23h",
                "1134567890",
                "Carlos"
        ));
        dataSource.cadastrarRestaurante(Restaurante.criarRestaurante(
                null,
                "Bistro Feliz",
                "Brasileira",
                "12h às 00h",
                "1144556677",
                "Ana"
        ));

        Set<Restaurante> restaurantes = dataSource.buscarRestauranteComFiltro("La Mesa", null, null, null, "Carlos");

        assertAll(
                () -> assertEquals(1, restaurantes.size()),
                () -> assertTrue(restaurantes.stream().anyMatch(r -> r.getNome().equals("La Mesa")))
        );
    }

    @Test
    @Transactional
    void deveBuscarRestaurantePorId() {
        Restaurante salvo = dataSource.cadastrarRestaurante(Restaurante.criarRestaurante(
                null,
                "Ponto do Sabor",
                "Brasileira",
                "Todo dia, 8h às 22h",
                "11998877665",
                "Marcos"
        ));

        Restaurante consultado = dataSource.buscarRestaurantePorId(salvo.getRestauranteId());

        assertAll(
                () -> assertNotNull(consultado),
                () -> assertEquals("Ponto do Sabor", consultado.getNome()),
                () -> assertEquals("Marcos", consultado.getDonoRestaurante().getNome())
        );
    }

    @Test
    @Transactional
    void deveAtualizarRestaurante() {
        Restaurante salvo = dataSource.cadastrarRestaurante(Restaurante.criarRestaurante(
                null,
                "Ponto do Sabor",
                "Brasileira",
                "Todo dia, 8h às 22h",
                "11998877665",
                "Marcos"
        ));

        Restaurante atualizado = Restaurante.criarRestaurante(
                salvo.getRestauranteId(),
                "Ponto do Sabor Atualizado",
                "Italiana",
                "Todos os dias, 10h às 23h",
                "11997766554",
                "Marcos"
        );

        Restaurante retorno = dataSource.atualizarRestaurante(atualizado);
        Restaurante consultado = dataSource.buscarRestaurantePorId(salvo.getRestauranteId());

        assertAll(
                () -> assertNotNull(retorno),
                () -> assertEquals("Ponto do Sabor Atualizado", consultado.getNome()),
                () -> assertEquals(TipoCozinhaEnum.ITALIANA, consultado.getTipoCozinha()),
                () -> assertEquals("11997766554", consultado.getContato())
        );
    }

    @Test
    @Transactional
    void deveDeletarRestaurante() {
        Restaurante salvo = dataSource.cadastrarRestaurante(Restaurante.criarRestaurante(
                null,
                "Restaurante para deletar",
                "Japonesa",
                "10h às 21h",
                "11998877666",
                "Paulo"
        ));

        dataSource.deletarRestaurante(salvo.getRestauranteId());

        assertThrows(NoSuchElementException.class, () -> dataSource.buscarRestaurantePorId(salvo.getRestauranteId()));
    }

    @Test
    @Transactional
    void deveSalvarItensEmLoteEConsultarPorId() {
        Restaurante restaurante = dataSource.cadastrarRestaurante(
                Restaurante.criarRestaurante(
                        null,
                        "Bistro Central",
                        "Brasileira",
                        "12h às 00h",
                        "11998877666",
                        "Ana Paula"
                )
        );

        ItensCardapio pizza = ItensCardapio.criarItemCardapio(
                "Pizza Margherita",
                new BigDecimal("39.90"),
                "Pizza com mussarela",
                true,
                "pizza.jpg"
        );
        pizza.adicionarRestaurante(restaurante);

        ItensCardapio pasta = ItensCardapio.criarItemCardapio(
                "Lasanha",
                new BigDecimal("45.50"),
                "Lasanha bolonhesa",
                true,
                "lasanha.jpg"
        );
        pasta.adicionarRestaurante(restaurante);

        List<ItensCardapio> salvos = dataSource.salvarTodosItensCardapio(List.of(pizza, pasta));
        ItensCardapio consultado = dataSource.buscarItensCardapioPorId(salvos.get(0).getCodigoItemCardapio());

        assertAll(
                () -> assertNotNull(salvos),
                () -> assertEquals(2, salvos.size()),
                () -> assertNotNull(consultado),
                () -> assertEquals("Pizza Margherita", consultado.getNome()),
                () -> assertEquals(new BigDecimal("39.90"), consultado.getPreco()),
                () -> assertEquals("Bistro Central", consultado.getRestaurante().getNome())
        );
    }

    @Test
    @Transactional
    void deveAtualizarItemCardapio() {
        Restaurante restaurante = dataSource.cadastrarRestaurante(Restaurante.criarRestaurante(
                null,
                "Bistro Atualizar",
                "Brasileira",
                "12h às 00h",
                "11998877666",
                "Ana Paula"
        ));

        ItensCardapio item = ItensCardapio.criarItemCardapio(
                "Pizza Margherita",
                new BigDecimal("39.90"),
                "Pizza com mussarela",
                true,
                "pizza.jpg"
        );
        item.adicionarRestaurante(restaurante);
        ItensCardapio salvo = dataSource.salvarTodosItensCardapio(List.of(item)).getFirst();

        ItensCardapio atualizado = ItensCardapio.atualizarItemCardapio(
                salvo.getCodigoItemCardapio(),
                "Pizza Quatro Queijos",
                new BigDecimal("49.90"),
                "Pizza com quatro queijos",
                false,
                "pizza_4_queijos.jpg"
        );
        atualizado.adicionarRestaurante(restaurante);

        ItensCardapio retorno = dataSource.atualizarItemCardapio(atualizado);
        ItensCardapio consultado = dataSource.buscarItensCardapioPorId(salvo.getCodigoItemCardapio());

        assertAll(
                () -> assertNotNull(retorno),
                () -> assertEquals("Pizza Quatro Queijos", consultado.getNome()),
                () -> assertEquals(new BigDecimal("49.90"), consultado.getPreco()),
                () -> assertFalse(consultado.getDisponibilidadeComercioLocal())
        );
    }

    @Test
    @Transactional
    void deveDeletarItemCardapio() {
        Restaurante restaurante = dataSource.cadastrarRestaurante(Restaurante.criarRestaurante(
                null,
                "Bistro Deletar",
                "Brasileira",
                "12h às 00h",
                "11998877666",
                "Ana Paula"
        ));

        ItensCardapio item = ItensCardapio.criarItemCardapio(
                "Pastel",
                new BigDecimal("18.00"),
                "Pastel de forno",
                true,
                "pastel.jpg"
        );
        item.adicionarRestaurante(restaurante);
        ItensCardapio salvo = dataSource.salvarTodosItensCardapio(List.of(item)).getFirst();

        dataSource.deletarItemCardapio(salvo.getCodigoItemCardapio());

        assertThrows(NoSuchElementException.class, () -> dataSource.buscarItensCardapioPorId(salvo.getCodigoItemCardapio()));
    }
}
