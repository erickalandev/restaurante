package com.tech.challenge.fiap.gestao.restaurante.application.usecases.restaurante;

import com.tech.challenge.fiap.gestao.restaurante.application.dtos.EnderecoDTO;
import com.tech.challenge.fiap.gestao.restaurante.application.dtos.RestauranteDTO;
import com.tech.challenge.fiap.gestao.restaurante.application.usecases.restaurante.CadastrarRestauranteUseCase;
import com.tech.challenge.fiap.gestao.restaurante.domain.entities.Endereco;
import com.tech.challenge.fiap.gestao.restaurante.domain.entities.Restaurante;
import com.tech.challenge.fiap.gestao.restaurante.exception.RestauranteEncontradoException;
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
    void deveCadastrarRestauranteQuandoNaoExiste() {
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
                20L,
                dto.nome(),
                dto.tipoCozinha(),
                dto.horarioFuncionamento(),
                dto.contato(),
                dto.donoRestaurante()
        );
        restauranteCadastrado.adicionarEndereco(Endereco.criarEndereco(
                enderecoDTO.rua(),
                enderecoDTO.cidade(),
                enderecoDTO.estado(),
                enderecoDTO.cep(),
                enderecoDTO.numero(),
                enderecoDTO.complemento()
        ));

        when(restauranteGateway.buscarRestaurantePorNomeEDono("La Mesa", "Carlos")).thenReturn(null);
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
    void deveLancarExcecaoQuandoRestauranteJaExiste() {
        Restaurante restauranteExistente = Restaurante.criarRestaurante(
                10L,
                "La Mesa",
                "Italiana",
                "11h às 23h",
                "(11) 3456-7890",
                "Carlos"
        );

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

        when(restauranteGateway.buscarRestaurantePorNomeEDono("La Mesa", "Carlos")).thenReturn(restauranteExistente);

        RestauranteEncontradoException exception = assertThrows(
                RestauranteEncontradoException.class,
                () -> useCase.executar(dto)
        );

        assertEquals(
                "Restaurante ja existente.",
                exception.getMessage()
        );
    }
}