package com.tech.challenge.fiap.gestao.restaurante.usecases.usuario;

import com.tech.challenge.fiap.gestao.restaurante.application.usecases.usuario.DeletarUsuarioClienteUseCase;
import com.tech.challenge.fiap.gestao.restaurante.domain.entities.Usuario;
import com.tech.challenge.fiap.gestao.restaurante.enums.TipoUsuarioEnum;
import com.tech.challenge.fiap.gestao.restaurante.exception.UsuarioNaoEncontradoException;
import com.tech.challenge.fiap.gestao.restaurante.domain.repository.IUsuarioGateway;
import com.tech.challenge.fiap.gestao.restaurante.utils.CpfUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DeletarUsuarioClienteUseCaseTest {

    @Mock
    private IUsuarioGateway usuarioGateway;

    private DeletarUsuarioClienteUseCase useCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        useCase = DeletarUsuarioClienteUseCase.deletar(usuarioGateway);
    }

    @Test
    void deveDeletarUsuarioQuandoExistir() {
        String cpf = "566.947.837-10";
        String cpfFormatado = CpfUtils.formatarCpf(cpf);
        Usuario usuario = Usuario.criarUsuario(null, "Maria Souza", cpf, "maria@email.com", "(11) 98888-7777", TipoUsuarioEnum.CLIENTE);
        usuario.setUsuarioId(10L);

        when(usuarioGateway.buscarUsuarioPorCpf(cpfFormatado)).thenReturn(usuario);

        useCase.executar(cpf);

        verify(usuarioGateway).buscarUsuarioPorCpf(cpfFormatado);
        verify(usuarioGateway).deletarUsuarioPorId(10L);
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoExiste() {
        String cpf = "106.101.230-17";
        String cpfFormatado = CpfUtils.formatarCpf(cpf);

        when(usuarioGateway.buscarUsuarioPorCpf(cpfFormatado)).thenReturn(null);

        assertThrows(
                UsuarioNaoEncontradoException.class,
                () -> useCase.executar(cpf)
        );
    }

    @Test
    void deveLancarExcecaoQuandoCpfForInvalido() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> useCase.executar("123")
        );

        assertEquals("CPF informado invalido.", exception.getMessage());
    }
}