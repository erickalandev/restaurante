package com.tech.challenge.fiap.gestao.restaurante.application.usecases.usuario;

import com.tech.challenge.fiap.gestao.restaurante.application.dtos.NovoUsuarioClienteDTO;
import com.tech.challenge.fiap.gestao.restaurante.application.usecases.usuario.CadastrarUsuarioClienteUseCase;
import com.tech.challenge.fiap.gestao.restaurante.domain.entities.Usuario;
import com.tech.challenge.fiap.gestao.restaurante.enums.TipoUsuarioEnum;
import com.tech.challenge.fiap.gestao.restaurante.exception.UsuarioEncontradoException;
import com.tech.challenge.fiap.gestao.restaurante.domain.repository.IUsuarioGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CadastrarUsuarioClienteUseCaseTest {

    @Mock
    private IUsuarioGateway usuarioGateway;

    private CadastrarUsuarioClienteUseCase useCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        useCase = CadastrarUsuarioClienteUseCase.criar(usuarioGateway);
    }

    @Test
    void deveCadastrarUsuarioQuandoNaoExistir() {
        String cpf = "566.947.837-10";
        String cpfFormatado = "56694783710";
        NovoUsuarioClienteDTO dto = new NovoUsuarioClienteDTO(
                "Maria Souza",
                cpf,
                "maria@email.com",
                "(11) 98888-7777",
                Collections.emptySet()
        );

        Usuario usuarioCadastrado = Usuario.criarUsuario(
                null,
                dto.nome(),
                dto.cpf(),
                dto.email(),
                dto.contato(),
                TipoUsuarioEnum.CLIENTE
        );
        usuarioCadastrado.setUsuarioId(10L);

        when(usuarioGateway.buscarUsuarioPorCpf(cpfFormatado)).thenReturn(null);
        when(usuarioGateway.cadastrarUsuario(any(Usuario.class))).thenReturn(usuarioCadastrado);

        Usuario resultado = useCase.executar(dto);

        assertEquals(10L, resultado.getUsuarioId());
        assertEquals("Maria Souza", resultado.getNome());
        assertEquals(usuarioCadastrado.getCpf(), resultado.getCpf());
        assertEquals("maria@email.com", resultado.getEmail());
        verify(usuarioGateway).buscarUsuarioPorCpf(cpfFormatado);
        verify(usuarioGateway).cadastrarUsuario(any(Usuario.class));
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioJaExiste() {
        String cpf = "566.947.837-10";
        String cpfFormatado = "56694783710";
        NovoUsuarioClienteDTO dto = new NovoUsuarioClienteDTO(
                "Maria Souza",
                cpf,
                "maria@email.com",
                "(11) 98888-7777",
                Collections.emptySet()
        );

        Usuario usuarioExistente = Usuario.criarUsuario(
                null,
                dto.nome(),
                dto.cpf(),
                dto.email(),
                dto.contato(),
                TipoUsuarioEnum.CLIENTE
        );

        when(usuarioGateway.buscarUsuarioPorCpf(cpfFormatado)).thenReturn(usuarioExistente);

        UsuarioEncontradoException exception = assertThrows(
                UsuarioEncontradoException.class,
                () -> useCase.executar(dto)
        );

        assertEquals("CPF ja cadastrado.", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoCpfForInvalido() {
        NovoUsuarioClienteDTO dto = new NovoUsuarioClienteDTO(
                "Maria Souza",
                "123",
                "maria@email.com",
                "(11) 98888-7777",
                Collections.emptySet()
        );

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> useCase.executar(dto)
        );

        assertEquals("CPF informado invalido.", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoEmailForInvalido() {
        NovoUsuarioClienteDTO dto = new NovoUsuarioClienteDTO(
                "Maria Souza",
                "566.947.837-10",
                "email-invalido",
                "(11) 98888-7777",
                Collections.emptySet()
        );

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> useCase.executar(dto)
        );

        assertEquals("E-mail informado invalido.", exception.getMessage());
    }
}