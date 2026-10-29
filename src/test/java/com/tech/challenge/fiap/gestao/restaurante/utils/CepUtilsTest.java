package com.tech.challenge.fiap.gestao.restaurante.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CepUtilsTest {

    @Test
    void deveFormatarCepComMascaraEComNumero() {
        assertAll(
                () -> assertEquals("12345678", CepUtils.formatarCep("12345-678")),
                () -> assertEquals("12345678", CepUtils.formatarCep("12345678"))
        );
    }

    @Test
    void deveLancarExcecaoQuandoCepForInvalido() {
        assertThrows(IllegalArgumentException.class, () -> CepUtils.formatarCep("1234"));
        assertThrows(IllegalArgumentException.class, () -> CepUtils.formatarCep("1234-567"));
    }
}