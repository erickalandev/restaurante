package com.tech.challenge.fiap.gestao.restaurante.application.usecases.restaurante;

import com.tech.challenge.fiap.gestao.restaurante.application.dtos.EnderecoDTO;
import com.tech.challenge.fiap.gestao.restaurante.application.dtos.RestauranteDTO;
import com.tech.challenge.fiap.gestao.restaurante.domain.entities.Endereco;
import com.tech.challenge.fiap.gestao.restaurante.domain.entities.Restaurante;
import com.tech.challenge.fiap.gestao.restaurante.domain.repository.IRestauranteGateway;

public class AtualizarRestauranteUseCase {

    private final IRestauranteGateway restauranteGateway;

    public AtualizarRestauranteUseCase(IRestauranteGateway restauranteGateway) {
        this.restauranteGateway = restauranteGateway;
    }

    public static AtualizarRestauranteUseCase atualizar(IRestauranteGateway restauranteGateway) {
        return new AtualizarRestauranteUseCase(restauranteGateway);
    }

    public Restaurante executar(RestauranteDTO restauranteDTO) {
        final Restaurante restauranteExistente = ConsultarRestauranteUseCase.consultar(restauranteGateway)
                                   .restaurantePorNomeEDono(restauranteDTO.nome(), restauranteDTO.donoRestaurante());

        Restaurante restaurante = Restaurante.criarRestaurante(
                restauranteExistente.getRestauranteId(),
                restauranteDTO.nome(),
                restauranteDTO.tipoCozinha(),
                restauranteDTO.horarioFuncionamento(),
                restauranteDTO.contato(),
                restauranteDTO.donoRestaurante()
        );

        restaurante.getDonoRestaurante().setUsuarioId(restauranteExistente.getDonoRestaurante().getUsuarioId());

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
