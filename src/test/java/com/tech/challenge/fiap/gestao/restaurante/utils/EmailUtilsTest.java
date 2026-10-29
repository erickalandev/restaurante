package com.tech.challenge.fiap.gestao.restaurante.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailUtilsTest {

    @Test
    void deveValidarEmailValido() {
        assertDoesNotThrow(() -> EmailUtils.validaEmail("usuario@email.com"));
    }

    @Test
    void deveLancarExcecaoQuandoEmailForInvalido() {
        assertThrows(IllegalArgumentException.class, () -> EmailUtils.validaEmail("email-invalido"));
        assertThrows(IllegalArgumentException.class, () -> EmailUtils.validaEmail(""));
    }

}