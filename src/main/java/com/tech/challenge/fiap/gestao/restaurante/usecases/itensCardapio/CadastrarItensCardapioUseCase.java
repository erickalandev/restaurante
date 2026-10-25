package com.tech.challenge.fiap.gestao.restaurante.usecases.itensCardapio;

import com.tech.challenge.fiap.gestao.restaurante.dtos.ItensCardapioDTO;
import com.tech.challenge.fiap.gestao.restaurante.entities.ItensCardapio;
import com.tech.challenge.fiap.gestao.restaurante.entities.Restaurante;
import com.tech.challenge.fiap.gestao.restaurante.interfaces.IItensCardapioGateway;
import com.tech.challenge.fiap.gestao.restaurante.interfaces.IRestauranteGateway;
import com.tech.challenge.fiap.gestao.restaurante.usecases.restaurante.ConsultarRestauranteUseCase;

import java.util.List;

public class CadastrarItensCardapioUseCase {

    private final IItensCardapioGateway itensCardapioGateway;
    private final IRestauranteGateway restauranteGateway;

    public CadastrarItensCardapioUseCase(IItensCardapioGateway itensCardapioGateway, IRestauranteGateway restauranteGateway) {
        this.itensCardapioGateway = itensCardapioGateway;
        this.restauranteGateway = restauranteGateway;
    }

    public static CadastrarItensCardapioUseCase criar(IItensCardapioGateway itensCardapioGateway, IRestauranteGateway restauranteGateway) {
        return new CadastrarItensCardapioUseCase(itensCardapioGateway, restauranteGateway);
    }

    public List<ItensCardapio> executar(String nomeRestaurante, String donoRestaurante, List<ItensCardapioDTO> itensCardapioDTO) {
        Restaurante restaurante = ConsultarRestauranteUseCase.consultar(restauranteGateway)
                                                             .restaurantePorNomeEDono(nomeRestaurante,donoRestaurante);
        List<ItensCardapio> itensCardapio = itensCardapioDTO.stream()
                .map(dto -> ItensCardapio.criarItemCardapio(
                        dto.nome(),
                        dto.preco(),
                        dto.descricao(),
                        dto.disponibilidadeComercioLocal(),
                        dto.foto()
                ).adicionarRestaurante(restaurante))
                .toList();
        return itensCardapioGateway.salvarTodosItens(itensCardapio);
    }
}
