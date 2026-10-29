package com.tech.challenge.fiap.gestao.restaurante.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TipoCozinhaEnumTest {

    @Test
    void deveRetornarEnumCorretoQuandoDescricaoForValida() {
        assertEquals(TipoCozinhaEnum.ITALIANA, TipoCozinhaEnum.getValue("Italiana"));
        assertEquals(TipoCozinhaEnum.CHINESA, TipoCozinhaEnum.getValue("Chinesa"));
        assertEquals(TipoCozinhaEnum.JAPONESA, TipoCozinhaEnum.getValue("Japonesa"));
        assertEquals(TipoCozinhaEnum.BRASILEIRA, TipoCozinhaEnum.getValue("brasileira"));
    }

    @Test
    void deveRetornarDescricaoDoEnum() {
        assertEquals("Italiana", TipoCozinhaEnum.ITALIANA.getDescricao());
        assertEquals("Coreana", TipoCozinhaEnum.COREANA.getDescricao());
    }

    @Test
    void deveLancarExcecaoQuandoDescricaoForInvalida() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> TipoCozinhaEnum.getValue("Vegana")
        );

        assertEquals("Valor inválido para tipo de Cozinha: Vegana", exception.getMessage());
    }
}
