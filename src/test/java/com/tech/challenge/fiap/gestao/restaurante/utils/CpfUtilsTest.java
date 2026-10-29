package com.tech.challenge.fiap.gestao.restaurante.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CpfUtilsTest {

    @Test
    void deveFormatarCpfValido() {
        assertEquals("12345678909", CpfUtils.formatarCpf("123.456.789-09"));
    }

    @Test
    void deveLancarExcecaoQuandoCpfForInvalido() {
        assertThrows(IllegalArgumentException.class, () -> CpfUtils.formatarCpf("111.111.111-11"));
        assertThrows(IllegalArgumentException.class, () -> CpfUtils.formatarCpf("12345678900"));
    }

}