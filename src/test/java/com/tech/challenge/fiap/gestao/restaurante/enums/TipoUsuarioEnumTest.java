package com.tech.challenge.fiap.gestao.restaurante.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TipoUsuarioEnumTest {

    @Test
    void deveExporOsTiposDeUsuarioEsperados() {
        assertEquals(TipoUsuarioEnum.CLIENTE, TipoUsuarioEnum.valueOf("CLIENTE"));
        assertEquals(TipoUsuarioEnum.DONO_RESTAURANTE, TipoUsuarioEnum.valueOf("DONO_RESTAURANTE"));
    }
}
