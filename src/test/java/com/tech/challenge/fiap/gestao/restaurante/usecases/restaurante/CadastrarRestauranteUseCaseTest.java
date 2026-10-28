package com.tech.challenge.fiap.gestao.restaurante.usecases.restaurante;

import com.tech.challenge.fiap.gestao.restaurante.application.dtos.EnderecoDTO;
import com.tech.challenge.fiap.gestao.restaurante.application.dtos.RestauranteDTO;
import com.tech.challenge.fiap.gestao.restaurante.application.usecases.restaurante.CadastrarRestauranteUseCase;
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

class CadastrarRestauranteUseCaseTest {

    @Mock
    private IRestauranteGateway restauranteGateway;

    private CadastrarRestauranteUseCase useCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        useCase = CadastrarRestauranteUseCase.cadastrar(restauranteGateway);
    }

    @Test
    void deveCadastrarRestauranteQuandoConsultaRetornaRestauranteExistente() {
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

        RestauranteDTO dto = new RestauranteDTO(
                "La Mesa",
                "Italiana",
                "11h às 23h",
                "(11) 3456-7890",
                Set.of(enderecoDTO),
                "Carlos"
        );

        Restaurante restauranteCadastrado = Restaurante.criarRestaurante(
                dto.nome(),
                dto.tipoCozinha(),
                dto.horarioFuncionamento(),
                dto.contato(),
                dto.donoRestaurante()
        );
        restauranteCadastrado.setRestauranteId(20L);
        restauranteCadastrado.adicionarEndereco(Endereco.criarEndereco("Rua das Flores",
                "São Paulo",
                "SP",
                "01000-000",
                "123",
                "Apto 2"));

        when(restauranteGateway.buscarRestaurantePorNomeEDono("La Mesa", "Carlos")).thenReturn(restauranteExistente);
        when(restauranteGateway.cadastrarRestaurante(any(Restaurante.class))).thenReturn(restauranteCadastrado);

        Restaurante resultado = useCase.executar(dto);

        assertEquals(20L, resultado.getRestauranteId());
        assertEquals("La Mesa", resultado.getNome());
        assertEquals("1134567890", resultado.getContato());
        assertEquals(1, resultado.getEnderecos().size());
        verify(restauranteGateway).buscarRestaurantePorNomeEDono("La Mesa", "Carlos");
        verify(restauranteGateway).cadastrarRestaurante(any(Restaurante.class));
    }

    @Test
    void deveLancarExcecaoQuandoRestauranteNaoExisteNaConsulta() {
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
                "11h às 23h",
                "(11) 3456-7890",
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
}