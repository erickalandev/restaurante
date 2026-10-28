package com.tech.challenge.fiap.gestao.restaurante.application.usecases.restaurante;

import com.tech.challenge.fiap.gestao.restaurante.application.dtos.EnderecoDTO;
import com.tech.challenge.fiap.gestao.restaurante.application.dtos.RestauranteDTO;
import com.tech.challenge.fiap.gestao.restaurante.domain.entities.Endereco;
import com.tech.challenge.fiap.gestao.restaurante.domain.entities.Restaurante;
import com.tech.challenge.fiap.gestao.restaurante.domain.repository.IRestauranteGateway;
import com.tech.challenge.fiap.gestao.restaurante.exception.RestauranteEncontradoException;

public class CadastrarRestauranteUseCase {

    private final IRestauranteGateway restauranteGateway;

    public CadastrarRestauranteUseCase(IRestauranteGateway restauranteGateway) {
        this.restauranteGateway = restauranteGateway;
    }

    public static CadastrarRestauranteUseCase cadastrar(IRestauranteGateway restauranteGateway) {
        return new CadastrarRestauranteUseCase(restauranteGateway);
    }

    public Restaurante executar(RestauranteDTO restauranteDTO) {

        Restaurante restaurante = Restaurante.criarRestaurante(
                restauranteDTO.nome(),
                restauranteDTO.tipoCozinha(),
                restauranteDTO.horarioFuncionamento(),
                restauranteDTO.contato(),
                restauranteDTO.donoRestaurante()
        );

        Restaurante restauranteExistente = restauranteGateway.buscarRestaurantePorNomeEDono(restaurante.getNome(), restaurante.getDonoRestaurante().getNome());
        if (restauranteExistente != null) {
            throw new RestauranteEncontradoException("Restaurante ja existente.");
        }

        for (EnderecoDTO dto: restauranteDTO.enderecos()) {
            final Endereco endereco = Endereco.criarEndereco(
                    dto.rua(),
                    dto.cidade(),
                    dto.estado(),
                    dto.cep(),
                    dto.numero(),
                    dto.complemento()
            );
            restaurante.adicionarEndereco(endereco);
        }

        return restauranteGateway.cadastrarRestaurante(restaurante);
    }
}
