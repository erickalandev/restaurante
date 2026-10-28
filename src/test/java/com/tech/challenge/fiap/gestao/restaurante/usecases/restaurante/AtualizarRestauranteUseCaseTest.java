package com.tech.challenge.fiap.gestao.restaurante.usecases.restaurante;

import com.tech.challenge.fiap.gestao.restaurante.application.dtos.EnderecoDTO;
import com.tech.challenge.fiap.gestao.restaurante.application.dtos.RestauranteDTO;
import com.tech.challenge.fiap.gestao.restaurante.application.usecases.restaurante.AtualizarRestauranteUseCase;
import com.tech.challenge.fiap.gestao.restaurante.domain.entities.Endereco;
import com.tech.challenge.fiap.gestao.restaurante.domain.entities.Restaurante;
import com.tech.challenge.fiap.gestao.restaurante.exception.RestauranteNaoEncontradoException;
import com.tech.challenge.fiap.gestao.restaurante.domain.repository.IRestauranteGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AtualizarRestauranteUseCaseTest {

    @Mock
    private IRestauranteGateway restauranteGateway;

    private AtualizarRestauranteUseCase useCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        useCase = AtualizarRestauranteUseCase.atualizar(restauranteGateway);
    }

    @Test
    void deveAtualizarRestauranteQuandoExistir() {
        Restaurante restauranteExistente = Restaurante.criarRestaurante(
                "La Mesa",
                "Italiana",
                "11h às 23h",
                "(11) 3456-7890",
                "Carlos"
        );
        restauranteExistente.setRestauranteId(10L);

        EnderecoDTO enderecoDTO = new EnderecoDTO(
                "Rua das Flores",
                "São Paulo",
                "SP",
                "01000-000",
                "123",
                "Apto 2"
        );

        EnderecoDTO endereco2DTO = new EnderecoDTO(
                "Rua das Cachoeira",
                "São Paulo",
                "SP",
                "14545-000",
                "546",
                "Apto 97"
        );

        RestauranteDTO dto = new RestauranteDTO(
                "La Mesa",
                "Italiana",
                "12h às 23h",
                "(11) 99999-8888",
                Set.of(enderecoDTO, endereco2DTO),
                "Carlos"
        );

        Restaurante restauranteAtualizado = Restaurante.atualizarRestaurante(
                10L,
                dto.nome(),
                dto.tipoCozinha(),
                dto.horarioFuncionamento(),
                dto.contato(),
                dto.donoRestaurante()
        );
        restauranteAtualizado.adicionarEndereco(
                Endereco.criarEndereco(
                        enderecoDTO.rua(),
                        enderecoDTO.cidade(),
                        enderecoDTO.estado(),
                        enderecoDTO.cep(),
                        enderecoDTO.numero(),
                        enderecoDTO.complemento()
                )
        );
        restauranteAtualizado.adicionarEndereco(
                Endereco.criarEndereco(
                        enderecoDTO.rua(),
                        enderecoDTO.cidade(),
                        enderecoDTO.estado(),
                        enderecoDTO.cep(),
                        enderecoDTO.numero(),
                        enderecoDTO.complemento()
                )
        );

        when(restauranteGateway.buscarRestaurantePorNomeEDono("La Mesa", "Carlos")).thenReturn(restauranteExistente);
        when(restauranteGateway.atualizarRestaurante(any(Restaurante.class))).thenReturn(restauranteAtualizado);

        Restaurante resultado = useCase.executar(dto);

        assertEquals(10L, resultado.getRestauranteId());
        assertEquals("La Mesa", resultado.getNome());
        assertEquals("12h às 23h", resultado.getHorarioFuncionamento());
        assertEquals("11999998888", resultado.getContato());
        assertEquals(2, resultado.getEnderecos().size());
        verify(restauranteGateway).buscarRestaurantePorNomeEDono("La Mesa", "Carlos");
        verify(restauranteGateway).atualizarRestaurante(any(Restaurante.class));
    }

    @Test
    void deveLancarExcecaoQuandoRestauranteNaoExiste() {
        EnderecoDTO enderecoDTO = new EnderecoDTO(
                "Rua das Flores",
                "São Paulo",
                "SP",
                "01000-000",
                "123",
                "Apto 2"
        );

        RestauranteDTO dto = new RestauranteDTO(
                "Restaurante Inexistente",
                "Italiana",
                "12h às 23h",
                "(11) 99999-8888",
                Set.of(enderecoDTO),
                "Carlos"
        );

        when(restauranteGateway.buscarRestaurantePorNomeEDono("Restaurante Inexistente", "Carlos")).thenReturn(null);

        RestauranteNaoEncontradoException exception = assertThrows(
                RestauranteNaoEncontradoException.class,
                () -> useCase.executar(dto)
        );

        assertEquals(
                "Restaurante nao encontrado para os valores: nome=Restaurante Inexistente Dono=Carlos",
                exception.getMessage()
        );
    }

    @Test
    void deveLancarExcecaoQuandoRestauranteNaoTemNome() {
        RestauranteDTO dto = new RestauranteDTO(
                null,
                "Italiana",
                "12h às 23h",
                "(11) 99999-8888",
                null,
                "Carlos"
        );

        when(restauranteGateway.buscarRestaurantePorNomeEDono(null, "Carlos")).thenReturn(null);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> useCase.executar(dto)
        );

        assertEquals(
                "O nome do Restaurante e o nome do dono sao obrigatorios.",
                exception.getMessage()
        );
    }
}