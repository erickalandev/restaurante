package com.tech.challenge.fiap.gestao.restaurante.application.usecases.itensCardapio;

import com.tech.challenge.fiap.gestao.restaurante.application.dtos.ItensCardapioDTO;
import com.tech.challenge.fiap.gestao.restaurante.domain.entities.ItensCardapio;
import com.tech.challenge.fiap.gestao.restaurante.exception.ItemCardapioNaoEncontradoException;
import com.tech.challenge.fiap.gestao.restaurante.domain.repository.IItensCardapioGateway;

public class AtualizarItensCardapioUseCase {

    private final IItensCardapioGateway itensCardapioGateway;

    public AtualizarItensCardapioUseCase(IItensCardapioGateway itensCardapioGateway) {
        this.itensCardapioGateway = itensCardapioGateway;
    }

    public static AtualizarItensCardapioUseCase atualizar(IItensCardapioGateway itensCardapioGateway) {
        return new AtualizarItensCardapioUseCase(itensCardapioGateway);
    }

    public ItensCardapio executar(ItensCardapioDTO itensCardapioDTO) {
        Long codigoItemCardapio = itensCardapioDTO.codigoItemCardapio();
        if (codigoItemCardapio == null) {
            throw new IllegalArgumentException("Código do item do cardápio é obrigatório");
        }

        ItensCardapio itensCardapioExistente = itensCardapioGateway.buscarPorId(codigoItemCardapio);
        if (itensCardapioExistente == null) {
            throw new ItemCardapioNaoEncontradoException("Nao foi encontrado item do cardapio para o codigo=" + codigoItemCardapio);
        }
        ItensCardapio itemCardapioAtualizado = ItensCardapio.atualizarItemCardapio(
                codigoItemCardapio,
                itensCardapioDTO.nome(),
                itensCardapioDTO.preco(),
                itensCardapioDTO.descricao(),
                itensCardapioDTO.disponibilidadeComercioLocal(),
                itensCardapioDTO.foto()
        );
        itemCardapioAtualizado.adicionarRestaurante(itensCardapioExistente.getRestaurante());
        return itensCardapioGateway.atualizarItemCardapio(itemCardapioAtualizado);
    }
}
