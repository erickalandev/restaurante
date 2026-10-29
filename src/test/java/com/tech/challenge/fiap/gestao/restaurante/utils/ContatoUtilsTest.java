package com.tech.challenge.fiap.gestao.restaurante.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ContatoUtilsTest {

    @Test
    void deveFormatarContato() {
        assertAll(
                () -> assertEquals("11987654321", ContatoUtils.formatarContato("(11) 98765-4321")),
                () -> assertEquals("11987654321", ContatoUtils.formatarContato("11987654321"))
        );
    }

    @Test
    void deveLancarExcecaoQuandoContatoForInvalido() {
        assertThrows(IllegalArgumentException.class, () -> ContatoUtils.formatarContato("999"));
        assertThrows(IllegalArgumentException.class, () -> ContatoUtils.formatarContato("abc"));
    }
}