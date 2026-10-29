package com.tech.challenge.fiap.gestao.restaurante.domain.entities;

import com.tech.challenge.fiap.gestao.restaurante.domain.entities.Endereco;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class EnderecoTest {

    @Test
    void deveCriarEnderecoComDadosValidos() throws Exception {
        Endereco endereco = Endereco.criarEndereco(
                "Avenida Paulista",
                "São Paulo",
                "SP",
                "01311-100",
                "1500",
                "Apartamento 12"
        );

        assertAll(
                () -> assertNotNull(endereco),
                () -> assertEquals("Avenida Paulista", lerCampo(endereco, "rua")),
                () -> assertEquals("São Paulo", lerCampo(endereco, "cidade")),
                () -> assertEquals("SP", lerCampo(endereco, "estado")),
                () -> assertEquals("01311100", lerCampo(endereco, "cep")),
                () -> assertEquals("1500", lerCampo(endereco, "numero")),
                () -> assertEquals("Apartamento 12", lerCampo(endereco, "complemento"))
        );
    }

    private Object lerCampo(Endereco endereco, String nomeCampo) throws NoSuchFieldException, IllegalAccessException {
        Field field = Endereco.class.getDeclaredField(nomeCampo);
        field.setAccessible(true);
        return field.get(endereco);
    }
}
