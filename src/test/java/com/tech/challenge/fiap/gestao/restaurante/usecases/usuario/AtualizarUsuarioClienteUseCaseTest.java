package com.tech.challenge.fiap.gestao.restaurante.usecases.usuario;

import com.tech.challenge.fiap.gestao.restaurante.application.dtos.NovoUsuarioClienteDTO;
import com.tech.challenge.fiap.gestao.restaurante.application.usecases.usuario.AtualizarUsuarioClienteUseCase;
import com.tech.challenge.fiap.gestao.restaurante.domain.entities.Usuario;
import com.tech.challenge.fiap.gestao.restaurante.enums.TipoUsuarioEnum;
import com.tech.challenge.fiap.gestao.restaurante.exception.UsuarioNaoEncontradoException;
import com.tech.challenge.fiap.gestao.restaurante.domain.repository.IUsuarioGateway;
import com.tech.challenge.fiap.gestao.restaurante.utils.CpfUtils;
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

class AtualizarUsuarioClienteUseCaseTest {

    @Mock
    private IUsuarioGateway usuarioGateway;

    private AtualizarUsuarioClienteUseCase useCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        useCase = AtualizarUsuarioClienteUseCase.atualizar(usuarioGateway);
    }

    @Test
    void deveAtualizarUsuarioQuandoExistir() {
        String cpf = "566.947.837-10";
        String cpfFormatado = CpfUtils.formatarCpf(cpf);
        Usuario usuarioExistente = Usuario.criarUsuario(null, "Maria Souza", cpf, "maria@email.com", "(11) 98888-7777", TipoUsuarioEnum.CLIENTE);
        usuarioExistente.setUsuarioId(10L);

        NovoUsuarioClienteDTO dto = new NovoUsuarioClienteDTO(
                "Maria Souza Atualizada",
                cpf,
                "maria.nova@email.com",
                "(11) 99999-8888",
                Collections.emptySet()
        );

        Usuario usuarioAtualizado = Usuario.criarUsuario(
                10L,
                dto.nome(),
                dto.cpf(),
                dto.email(),
                dto.contato(),
                TipoUsuarioEnum.CLIENTE
        );

        when(usuarioGateway.buscarUsuarioPorCpf(cpfFormatado)).thenReturn(usuarioExistente);
        when(usuarioGateway.atualizarUsuario(any(Usuario.class))).thenReturn(usuarioAtualizado);

        Usuario resultado = useCase.executar(dto);

        assertEquals(10L, resultado.getUsuarioId());
        assertEquals("Maria Souza Atualizada", resultado.getNome());
        assertEquals("maria.nova@email.com", resultado.getEmail());
        assertEquals("11999998888", resultado.getContato());
        verify(usuarioGateway).buscarUsuarioPorCpf(cpfFormatado);
        verify(usuarioGateway).atualizarUsuario(any(Usuario.class));
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoExiste() {
        String cpf = "106.101.230-17";
        String cpfFormatado = CpfUtils.formatarCpf(cpf);
        NovoUsuarioClienteDTO dto = new NovoUsuarioClienteDTO(
                "Pedro",
                cpf,
                "pedro@email.com",
                "(11) 77777-6666",
                Collections.emptySet()
        );

        when(usuarioGateway.buscarUsuarioPorCpf(cpfFormatado)).thenReturn(null);

        UsuarioNaoEncontradoException exception = assertThrows(
                UsuarioNaoEncontradoException.class,
                () -> useCase.executar(dto)
        );

        assertEquals("Usuario nao encontrado.", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoCpfForInvalido() {
        String cpf = "123";
        NovoUsuarioClienteDTO dto = new NovoUsuarioClienteDTO(
                "Pedro",
                cpf,
                "pedro@email.com",
                "(11) 77777-6666",
                Collections.emptySet()
        );

        when(usuarioGateway.buscarUsuarioPorCpf(cpf)).thenReturn(null);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> useCase.executar(dto)
        );

        assertEquals("CPF informado invalido.", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoEmailForInvalido() {
        String cpf = "566.947.837-10";
        String cpfFormatado = CpfUtils.formatarCpf(cpf);
        Usuario usuarioExistente = Usuario.criarUsuario(null, "Maria Souza", cpf, "maria@email.com", "(11) 98888-7777", TipoUsuarioEnum.CLIENTE);
        usuarioExistente.setUsuarioId(10L);

        NovoUsuarioClienteDTO dto = new NovoUsuarioClienteDTO(
                "Maria Souza",
                cpf,
                "email-invalido",
                "(11) 98888-7777",
                Collections.emptySet()
        );

        when(usuarioGateway.buscarUsuarioPorCpf(cpfFormatado)).thenReturn(usuarioExistente);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> useCase.executar(dto)
        );

        assertEquals("E-mail informado invalido.", exception.getMessage());
    }
}