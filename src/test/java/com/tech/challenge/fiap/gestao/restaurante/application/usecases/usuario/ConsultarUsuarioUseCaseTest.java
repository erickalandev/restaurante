package com.tech.challenge.fiap.gestao.restaurante.application.usecases.usuario;

import com.tech.challenge.fiap.gestao.restaurante.application.dtos.ConsultaUsuarioDTO;
import com.tech.challenge.fiap.gestao.restaurante.application.usecases.usuario.ConsultarUsuarioUseCase;
import com.tech.challenge.fiap.gestao.restaurante.domain.entities.Usuario;
import com.tech.challenge.fiap.gestao.restaurante.enums.TipoUsuarioEnum;
import com.tech.challenge.fiap.gestao.restaurante.domain.repository.IUsuarioGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ConsultarUsuarioUseCaseTest {

    @Mock
    private IUsuarioGateway usuarioGateway;

    private ConsultarUsuarioUseCase useCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        useCase = ConsultarUsuarioUseCase.buscar(usuarioGateway);
    }

    @Test
    void deveListarUsuariosComFiltro() {
        String cpfFormato = "56694783710";
        ConsultaUsuarioDTO dto = new ConsultaUsuarioDTO("Maria", "566.947.837-10", "maria@email.com", "(11) 98888-7777");

        Usuario usuario = Usuario.criarUsuario(null, "Maria Souza", dto.cpf(), dto.email(), dto.contato(), TipoUsuarioEnum.CLIENTE);
        usuario.setUsuarioId(10L);

        when(usuarioGateway.buscarUsuarios(dto.nome(), cpfFormato, dto.email(), dto.contato()))
                .thenReturn(List.of(usuario));

        List<Usuario> resultado = useCase.executar(dto);

        assertEquals(1, resultado.size());
        assertEquals("Maria Souza", resultado.getFirst().getNome());
        verify(usuarioGateway).buscarUsuarios(dto.nome(), cpfFormato, dto.email(), dto.contato());
    }

    @Test
    void deveBuscarUsuarioPorCpf() {
        String cpf = "566.947.837-10";
        String cpfFormatado = "56694783710";
        Usuario usuario = Usuario.criarUsuario(null, "Maria Souza", cpf, "maria@email.com", "(11) 98888-7777", TipoUsuarioEnum.CLIENTE);
        usuario.setUsuarioId(10L);

        when(usuarioGateway.buscarUsuarioPorCpf(cpfFormatado)).thenReturn(usuario);

        Usuario resultado = useCase.buscarUsuarioPorCpf(cpf);

        assertEquals(10L, resultado.getUsuarioId());
        assertEquals("Maria Souza", resultado.getNome());
        verify(usuarioGateway).buscarUsuarioPorCpf(cpfFormatado);
    }

    @Test
    void deveLancarExcecaoQuandoCpfForInvalido() {
        ConsultaUsuarioDTO dto = new ConsultaUsuarioDTO("Maria", "123", "maria@email.com", "(11) 98888-7777");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> useCase.executar(dto)
        );

        assertEquals("CPF informado invalido.", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoEmailForInvalido() {
        ConsultaUsuarioDTO dto = new ConsultaUsuarioDTO("Maria", "566.947.837-10", "email-invalido", "(11) 98888-7777");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> useCase.executar(dto)
        );

        assertEquals("E-mail informado invalido.", exception.getMessage());
    }
}