package com.tech.challenge.fiap.gestao.restaurante.usecases.restaurante;

import com.tech.challenge.fiap.gestao.restaurante.dtos.EnderecoDTO;
import com.tech.challenge.fiap.gestao.restaurante.dtos.RestauranteDTO;
import com.tech.challenge.fiap.gestao.restaurante.entities.Endereco;
import com.tech.challenge.fiap.gestao.restaurante.entities.Restaurante;
import com.tech.challenge.fiap.gestao.restaurante.interfaces.IRestauranteGateway;

public class AtualizarRestauranteUseCase {

    private final IRestauranteGateway restauranteGateway;

    public AtualizarRestauranteUseCase(IRestauranteGateway restauranteGateway) {
        this.restauranteGateway = restauranteGateway;
    }

    public static AtualizarRestauranteUseCase atualizar(IRestauranteGateway restauranteGateway) {
        return new AtualizarRestauranteUseCase(restauranteGateway);
    }

    public Restaurante executar(RestauranteDTO restauranteDTO) {
        Restaurante restauranteExistente = ConsultarRestauranteUseCase.consultar(restauranteGateway)
                                   .restaurantePorNomeEDono(restauranteDTO.nome(), restauranteDTO.donoRestaurante());

        Restaurante restaurante = Restaurante.atualizarRestaurante(
                restauranteExistente.getRestauranteId(),
                restauranteDTO.nome(),
                restauranteDTO.tipoCozinha(),
                restauranteDTO.horarioFuncionamento(),
                restauranteDTO.contato(),
                restauranteDTO.donoRestaurante()
        );

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
        return restauranteGateway.atualizarRestaurante(restaurante);
    }
}
