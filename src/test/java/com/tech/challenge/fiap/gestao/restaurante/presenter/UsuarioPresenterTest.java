package com.tech.challenge.fiap.gestao.restaurante.presenter;

import com.tech.challenge.fiap.gestao.restaurante.application.dtos.UsuarioDTO;
import com.tech.challenge.fiap.gestao.restaurante.domain.entities.Endereco;
import com.tech.challenge.fiap.gestao.restaurante.domain.entities.Usuario;
import com.tech.challenge.fiap.gestao.restaurante.enums.TipoUsuarioEnum;
import com.tech.challenge.fiap.gestao.restaurante.infraestrutura.model.EnderecoEntity;
import com.tech.challenge.fiap.gestao.restaurante.infraestrutura.model.UsuarioEntity;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioPresenterTest {

    @Test
    void deveRetornarNullQuandoUsuarioForNulo() {
        assertNull(UsuarioPresenter.toDTO(null));
        assertNull(UsuarioPresenter.toDomain(null));
        assertNull(UsuarioPresenter.toEntity(null));
    }

    @Test
    void deveConverterUsuarioParaDTO() {
        var usuario = Usuario.criarUsuario(1L, "Ana", "52998224725", "ana@email.com", "11999999999", TipoUsuarioEnum.CLIENTE);
        usuario.adicionarEndereco(Endereco.criarEndereco("Rua A", "São Paulo", "SP", "01311-100", "10", "Casa"));

        UsuarioDTO dto = UsuarioPresenter.toDTO(usuario);

        assertAll(
                () -> assertNotNull(dto),
                () -> assertEquals("Ana", dto.nome()),
                () -> assertEquals("52998224725", dto.cpf()),
                () -> assertEquals("ana@email.com", dto.email()),
                () -> assertEquals("11999999999", dto.contato()),
                () -> assertEquals(TipoUsuarioEnum.CLIENTE, dto.tipoUsuario()),
                () -> assertEquals(1, dto.enderecos().size()),
                () -> assertEquals("Rua A", dto.enderecos().iterator().next().rua())
        );
    }

    @Test
    void deveConverterUsuarioParaEntityComEnderecos() {
        var usuario = Usuario.criarUsuario(1L, "Ana", "52998224725", "ana@email.com", "11999999999", TipoUsuarioEnum.CLIENTE);
        usuario.adicionarEndereco(Endereco.criarEndereco("Rua A", "São Paulo", "SP", "01311-100", "10", "Casa"));

        var entity = UsuarioPresenter.toEntity(usuario);

        assertAll(
                () -> assertNotNull(entity),
                () -> assertEquals(1L, entity.getUsuarioId()),
                () -> assertEquals("Ana", entity.getNome()),
                () -> assertEquals("52998224725", entity.getCpf()),
                () -> assertEquals("ana@email.com", entity.getEmail()),
                () -> assertEquals("11999999999", entity.getContato()),
                () -> assertEquals(TipoUsuarioEnum.CLIENTE, entity.getTipoUsuario()),
                () -> assertEquals(1, entity.getEnderecoEntities().size())
        );
    }

    @Test
    void deveConverterUsuarioParaEntitySemEnderecos() {
        var usuario = Usuario.criarUsuario(2L, "Pedro", "52998224725", "pedro@email.com", "11988887777", TipoUsuarioEnum.CLIENTE);
        usuario.setEnderecos(Set.of());

        var entity = UsuarioPresenter.toEntity(usuario);

        assertAll(
                () -> assertNotNull(entity),
                () -> assertEquals("Pedro", entity.getNome()),
                () -> assertTrue(entity.getEnderecoEntities().isEmpty())
        );
    }

    @Test
    void deveConverterEntityParaDomainComEnderecos() {
        var usuarioEntity = new UsuarioEntity(1L, "Ana", "52998224725", "ana@email.com", "11999999999", TipoUsuarioEnum.CLIENTE);
        var enderecoEntity = new EnderecoEntity(10L, "Rua A", "São Paulo", "SP", "01311100", "10", "Casa", null, usuarioEntity);
        usuarioEntity.vincularEndereco(Set.of(enderecoEntity));

        var usuario = UsuarioPresenter.toDomain(usuarioEntity);

        assertAll(
                () -> assertNotNull(usuario),
                () -> assertEquals(1L, usuario.getUsuarioId()),
                () -> assertEquals("Ana", usuario.getNome()),
                () -> assertEquals("52998224725", usuario.getCpf()),
                () -> assertEquals("ana@email.com", usuario.getEmail()),
                () -> assertEquals("11999999999", usuario.getContato()),
                () -> assertEquals(1, usuario.getEnderecos().size()),
                () -> assertEquals("Rua A", usuario.getEnderecos().iterator().next().getRua())
        );
    }
}
