package com.tech.challenge.fiap.gestao.restaurante.infraestrutura.controller;

import com.tech.challenge.fiap.gestao.restaurante.application.dtos.ConsultaUsuarioDTO;
import com.tech.challenge.fiap.gestao.restaurante.application.dtos.EnderecoDTO;
import com.tech.challenge.fiap.gestao.restaurante.application.dtos.NovoUsuarioClienteDTO;
import com.tech.challenge.fiap.gestao.restaurante.application.dtos.UsuarioDTO;
import com.tech.challenge.fiap.gestao.restaurante.domain.entities.Usuario;
import com.tech.challenge.fiap.gestao.restaurante.domain.repository.IDataSource;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UsuarioControllerTest {

    @Test
    void deveConsultarUsuariosComSucesso() {
        IDataSource dataSource = mock(IDataSource.class);
        UsuarioController controller = UsuarioController.create(dataSource);

        Usuario usuario = Usuario.criarUsuario(
                1L,
                "João",
                "12345678909",
                "joao@email.com",
                "11999999999",
                com.tech.challenge.fiap.gestao.restaurante.enums.TipoUsuarioEnum.CLIENTE
        );

        when(dataSource.buscarUsuarios("João", "12345678909", "joao@email.com", "11999999999"))
                .thenReturn(Set.of(usuario));

        ConsultaUsuarioDTO request = new ConsultaUsuarioDTO("João", "12345678909", "joao@email.com", "11999999999");

        ResponseEntity<List<UsuarioDTO>> response = controller.consultarUsuarios(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals("João", response.getBody().get(0).nome());
    }

    @Test
    void deveCadastrarUsuarioClienteComSucesso() {
        IDataSource dataSource = mock(IDataSource.class);
        UsuarioController controller = UsuarioController.create(dataSource);

        EnderecoDTO endereco = new EnderecoDTO("Rua A", "São Paulo", "SP", "01000-000", "123", "Casa");
        NovoUsuarioClienteDTO request = new NovoUsuarioClienteDTO(
                "João",
                "12345678909",
                "joao@email.com",
                "11999999999",
                Set.of(endereco)
        );

        Usuario usuarioSalvo = Usuario.criarUsuario(
                1L,
                "João",
                "12345678909",
                "joao@email.com",
                "11999999999",
                com.tech.challenge.fiap.gestao.restaurante.enums.TipoUsuarioEnum.CLIENTE
        );

        when(dataSource.cadastrarUsuario(any(Usuario.class))).thenReturn(usuarioSalvo);

        ResponseEntity<UsuarioDTO> response = controller.cadastrarUsuarioCliente(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("João", response.getBody().nome());
    }

    @Test
    void deveAtualizarUsuarioClienteComSucesso() {
        IDataSource dataSource = mock(IDataSource.class);
        UsuarioController controller = UsuarioController.create(dataSource);

        EnderecoDTO endereco = new EnderecoDTO("Rua B", "São Paulo", "SP", "01000-000", "456", "Apto 1");
        NovoUsuarioClienteDTO request = new NovoUsuarioClienteDTO(
                "João Silva",
                "12345678909",
                "joao.novo@email.com",
                "11888888888",
                Set.of(endereco)
        );

        Usuario usuarioAtualizado = Usuario.criarUsuario(
                1L,
                "João Silva",
                "12345678909",
                "joao.novo@email.com",
                "11888888888",
                com.tech.challenge.fiap.gestao.restaurante.enums.TipoUsuarioEnum.CLIENTE
        );

        when(dataSource.buscarUsuarioPorCpf("12345678909")).thenReturn(usuarioAtualizado);
        when(dataSource.atualizarUsuario(any(Usuario.class))).thenReturn(usuarioAtualizado);

        ResponseEntity<UsuarioDTO> response = controller.atualizarUsuarioCliente(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("João Silva", response.getBody().nome());
    }
}
