package com.tech.challenge.fiap.gestao.restaurante.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FiltroUtilsTest {

    @Test
    void deveNormalizarTextoEcpf() {
        assertAll(
                () -> assertEquals("Restaurante", FiltroUtils.normalizarTexto("  Restaurante  ")),
                () -> assertNull(FiltroUtils.normalizarTexto("   ")),
                () -> assertEquals("12345678909", FiltroUtils.normalizarCpf(" 123.456.789-09 ")),
                () -> assertNull(FiltroUtils.normalizarCpf("   "))
        );
    }

}