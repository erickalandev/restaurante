package com.tech.challenge.fiap.gestao.restaurante.usecases.restaurante;

import com.tech.challenge.fiap.gestao.restaurante.dtos.EnderecoDTO;
import com.tech.challenge.fiap.gestao.restaurante.dtos.RestauranteDTO;
import com.tech.challenge.fiap.gestao.restaurante.entities.Endereco;
import com.tech.challenge.fiap.gestao.restaurante.entities.Restaurante;
import com.tech.challenge.fiap.gestao.restaurante.interfaces.IRestauranteGateway;

public class CadastrarRestauranteUseCase {

    private final IRestauranteGateway restauranteGateway;

    public CadastrarRestauranteUseCase(IRestauranteGateway restauranteGateway) {
        this.restauranteGateway = restauranteGateway;
    }

    public static CadastrarRestauranteUseCase cadastrar(IRestauranteGateway restauranteGateway) {
        return new CadastrarRestauranteUseCase(restauranteGateway);
    }

    public Restaurante executar(RestauranteDTO restauranteDTO) {
        ConsultarRestauranteUseCase.consultar(restauranteGateway)
                                   .restaurantePorNomeEDono(restauranteDTO.nome(), restauranteDTO.donoRestaurante());

        Restaurante restaurante = Restaurante.criarRestaurante(
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

        return restauranteGateway.cadastrarRestaurante(restaurante);
    }
}
