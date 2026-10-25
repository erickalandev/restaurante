package com.tech.challenge.fiap.gestao.restaurante.usecases.itensCardapio;

import com.tech.challenge.fiap.gestao.restaurante.dtos.ItensCardapioDTO;
import com.tech.challenge.fiap.gestao.restaurante.entities.ItensCardapio;
import com.tech.challenge.fiap.gestao.restaurante.exception.ItemCardapioNaoEncontradoException;
import com.tech.challenge.fiap.gestao.restaurante.interfaces.IItensCardapioGateway;
import org.springframework.util.ObjectUtils;

import java.util.List;

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
        if (ObjectUtils.isEmpty(itensCardapioExistente)) {
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
        return itensCardapioGateway.atualizarItemCarpio(itemCardapioAtualizado);
    }
}
