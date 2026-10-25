package com.tech.challenge.fiap.gestao.restaurante.usecases.itensCardapio;

import com.tech.challenge.fiap.gestao.restaurante.entities.ItensCardapio;
import com.tech.challenge.fiap.gestao.restaurante.exception.ItemCardapioNaoEncontradoException;
import com.tech.challenge.fiap.gestao.restaurante.interfaces.IItensCardapioGateway;
import org.springframework.util.ObjectUtils;

public class DeletarItensCardapioUseCase {

    private final IItensCardapioGateway itensCardapioGateway;

    public DeletarItensCardapioUseCase(IItensCardapioGateway itensCardapioGateway) {
        this.itensCardapioGateway = itensCardapioGateway;
    }

    public static DeletarItensCardapioUseCase deletar(IItensCardapioGateway itensCardapioGateway) {
        return new DeletarItensCardapioUseCase(itensCardapioGateway);
    }

    public void executar(Long codigoItemCardapio) {
        ItensCardapio itensCardapio = itensCardapioGateway.buscarPorId(codigoItemCardapio);
        if (ObjectUtils.isEmpty(itensCardapio)) {
            throw new ItemCardapioNaoEncontradoException("Nao foi encontrado item do cardapio para o codigo=" + codigoItemCardapio);
        }
        itensCardapioGateway.deletarItem(codigoItemCardapio);
    }
}
