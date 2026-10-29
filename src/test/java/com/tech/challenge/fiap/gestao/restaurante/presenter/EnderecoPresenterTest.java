package com.tech.challenge.fiap.gestao.restaurante.presenter;

import com.tech.challenge.fiap.gestao.restaurante.application.dtos.EnderecoDTO;
import com.tech.challenge.fiap.gestao.restaurante.domain.entities.Endereco;
import com.tech.challenge.fiap.gestao.restaurante.enums.TipoUsuarioEnum;
import com.tech.challenge.fiap.gestao.restaurante.infraestrutura.model.EnderecoEntity;
import com.tech.challenge.fiap.gestao.restaurante.infraestrutura.model.RestauranteEntity;
import com.tech.challenge.fiap.gestao.restaurante.infraestrutura.model.UsuarioEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EnderecoPresenterTest {

    @Test
    void deveRetornarNullQuandoEnderecoForNulo() {
        assertNull(EnderecoPresenter.toDTO(null));
        assertNull(EnderecoPresenter.toEntity(null, null, null));
    }

    @Test
    void deveConverterEnderecoParaDTO() {
        var endereco = Endereco.criarEndereco("Rua das Flores", "São Paulo", "SP", "01311-100", "1500", "Apto 12");

        EnderecoDTO dto = EnderecoPresenter.toDTO(endereco);

        assertAll(
                () -> assertNotNull(dto),
                () -> assertEquals("Rua das Flores", dto.rua()),
                () -> assertEquals("São Paulo", dto.cidade()),
                () -> assertEquals("SP", dto.estado()),
                () -> assertEquals("01311100", endereco.getCep()),
                () -> assertEquals("1500", dto.numero()),
                () -> assertEquals("Apto 12", dto.complemento())
        );
    }

    @Test
    void deveConverterEnderecoParaEntity() {
        var endereco = Endereco.criarEndereco("Rua das Flores", "São Paulo", "SP", "01311-100", "1500", "Apto 12");
        endereco.setEnderecoId(99L);

        var usuarioEntity = new UsuarioEntity(7L, "Maria", "12345678901", "maria@email.com", "11999999999", TipoUsuarioEnum.CLIENTE);
        var restauranteEntity = new RestauranteEntity(8L, "Sabor do Sul", com.tech.challenge.fiap.gestao.restaurante.enums.TipoCozinhaEnum.BRASILEIRA, "12h-23h", "11988887777");

        EnderecoEntity entity = EnderecoPresenter.toEntity(endereco, usuarioEntity, restauranteEntity);

        assertAll(
                () -> assertNotNull(entity),
                () -> assertEquals(99L, entity.getEnderecoId()),
                () -> assertEquals("Rua das Flores", entity.getRua()),
                () -> assertEquals("São Paulo", entity.getCidade()),
                () -> assertEquals("SP", entity.getEstado()),
                () -> assertEquals("01311100", entity.getCep()),
                () -> assertEquals("1500", entity.getNumero()),
                () -> assertEquals("Apto 12", entity.getComplemento()),
                () -> assertSame(restauranteEntity, entity.getRestauranteEntity()),
                () -> assertSame(usuarioEntity, entity.getUsuarioEntity())
        );
    }
}
