package com.tech.challenge.fiap.gestao.restaurante.entities;

import com.tech.challenge.fiap.gestao.restaurante.domain.entities.Usuario;
import com.tech.challenge.fiap.gestao.restaurante.enums.TipoUsuarioEnum;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {

    @Test
    void deveCriarUsuarioComDadosValidos() {
        Usuario usuario = Usuario.criarUsuario(
                null,
                "Guilherme Enrico Aragão",
                "566.947.837-10",
                "guilhermeenricoaragao@lencise.com",
                "(51) 99187-7137",
                TipoUsuarioEnum.CLIENTE
        );

        assertAll(
                () -> assertNotNull(usuario),
                () -> assertEquals("Guilherme Enrico Aragão", usuario.getNome()),
                () -> assertEquals("56694783710", usuario.getCpf()),
                () -> assertEquals("guilhermeenricoaragao@lencise.com", usuario.getEmail()),
                () -> assertEquals("51991877137", usuario.getContato()),
                () -> assertEquals(TipoUsuarioEnum.CLIENTE, usuario.getTipoUsuario())
        );
    }

    @Test
    void deveAtualizarUsuarioComDadosValidos() {
        Usuario usuario = Usuario.criarUsuario(
                10L,
                "Maria Souza",
                "87424740946",
                "maria.souza@email.com",
                "(11) 98877-6655",
                TipoUsuarioEnum.CLIENTE
        );

        assertAll(
                () -> assertEquals(10L, usuario.getUsuarioId()),
                () -> assertEquals("Maria Souza", usuario.getNome()),
                () -> assertEquals("87424740946", usuario.getCpf()),
                () -> assertEquals("maria.souza@email.com", usuario.getEmail()),
                () -> assertEquals("11988776655", usuario.getContato()),
                () -> assertEquals(TipoUsuarioEnum.CLIENTE, usuario.getTipoUsuario())
        );
    }

    @Test
    void deveCriarUsuarioDonoComTipoCorreto() {
        Usuario usuario = Usuario.criarUsuarioDono("João da Silva");

        assertAll(
                () -> assertEquals("João da Silva", usuario.getNome()),
                () -> assertEquals(TipoUsuarioEnum.DONO_RESTAURANTE, usuario.getTipoUsuario())
        );
    }

    @Test
    void deveLancarExcecaoQuandoNomeForNuloOuVazio() {
        assertThrows(IllegalArgumentException.class, () -> Usuario.criarUsuario(null, null, "566.947.837-10", "teste@email.com", "(11) 98888-7777", TipoUsuarioEnum.CLIENTE));
        assertThrows(IllegalArgumentException.class, () -> Usuario.criarUsuario(null, "", "566.947.837-10", "teste@email.com", "(11) 98888-7777", TipoUsuarioEnum.CLIENTE));
    }

    @Test
    void deveLancarExcecaoQuandoCpfForNuloOuVazio() {
        assertThrows(IllegalArgumentException.class, () -> Usuario.criarUsuario(null, "Maria Souza", null, "teste@email.com", "(11) 98888-7777", TipoUsuarioEnum.CLIENTE));
        assertThrows(IllegalArgumentException.class, () -> Usuario.criarUsuario(null, "Maria Souza", "", "teste@email.com", "(11) 98888-7777", TipoUsuarioEnum.CLIENTE));
    }

    @Test
    void deveLancarExcecaoQuandoEmailForNuloOuVazio() {
        assertThrows(IllegalArgumentException.class, () -> Usuario.criarUsuario(null, "Maria Souza", "566.947.837-10", null, "(11) 98888-7777", TipoUsuarioEnum.CLIENTE));
        assertThrows(IllegalArgumentException.class, () -> Usuario.criarUsuario(null, "Maria Souza", "566.947.837-10", "", "(11) 98888-7777", TipoUsuarioEnum.CLIENTE));
    }

    @Test
    void deveLancarExcecaoQuandoCpfForInvalido() {
        Usuario usuario = new Usuario();

        assertThrows(IllegalArgumentException.class, () -> usuario.setCpf("111.111.111-11"));
    }

    @Test
    void deveLancarExcecaoQuandoEmailForInvalido() {
        Usuario usuario = new Usuario();

        assertThrows(IllegalArgumentException.class, () -> usuario.setEmail("email-invalido"));
    }

    @Test
    void deveLancarExcecaoQuandoContatoForInvalido() {
        Usuario usuario = new Usuario();

        assertThrows(IllegalArgumentException.class, () -> usuario.setContato("999"));
    }

    @Test
    void deveIgnorarEnderecoNulo() {
        Usuario usuario = new Usuario();

        usuario.adicionarEndereco(null);

        assertNull(usuario.getEnderecos());
    }
}
