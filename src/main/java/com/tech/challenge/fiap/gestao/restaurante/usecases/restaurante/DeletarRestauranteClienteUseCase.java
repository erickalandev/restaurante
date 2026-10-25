package com.tech.challenge.fiap.gestao.restaurante.usecases.restaurante;

import com.tech.challenge.fiap.gestao.restaurante.entities.Restaurante;
import com.tech.challenge.fiap.gestao.restaurante.entities.Usuario;
import com.tech.challenge.fiap.gestao.restaurante.exception.RestauranteNaoEncontradoException;
import com.tech.challenge.fiap.gestao.restaurante.exception.UsuarioNaoEncontradoException;
import com.tech.challenge.fiap.gestao.restaurante.interfaces.IRestauranteGateway;
import com.tech.challenge.fiap.gestao.restaurante.interfaces.IUsuarioGateway;
import org.springframework.util.ObjectUtils;

public class DeletarRestauranteClienteUseCase {

    private final IRestauranteGateway restauranteGateway;

    public DeletarRestauranteClienteUseCase(IRestauranteGateway restauranteGateway) {
        this.restauranteGateway = restauranteGateway;
    }

    public static DeletarRestauranteClienteUseCase deletar(IRestauranteGateway restauranteGateway) {
        return new DeletarRestauranteClienteUseCase(restauranteGateway);
    }

    public void executar(Long restauranteId) {
        final Restaurante restauranteExistente = restauranteGateway.buscarPorId(restauranteId);
        if (ObjectUtils.isEmpty(restauranteExistente)) {
            throw new RestauranteNaoEncontradoException("Restaurante nao cadastrado.");
        }
        restauranteGateway.deletarRestaurante(restauranteId);
    }
}
