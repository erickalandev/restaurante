package com.tech.challenge.fiap.gestao.restaurante.application.gateway;

import com.tech.challenge.fiap.gestao.restaurante.domain.entities.ItensCardapio;
import com.tech.challenge.fiap.gestao.restaurante.domain.repository.IDataSource;
import com.tech.challenge.fiap.gestao.restaurante.domain.repository.IItensCardapioGateway;

import java.util.List;

public class ItensCardapioGateway implements IItensCardapioGateway {

    private final IDataSource dataSource;

    public ItensCardapioGateway(IDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static ItensCardapioGateway create(IDataSource dataSource) {
        return new ItensCardapioGateway(dataSource);
    }

    @Override
    public List<ItensCardapio> salvarTodosItens(List<ItensCardapio> itensCardapio) {
        return this.dataSource.salvarTodosItensCardapio(itensCardapio);
    }

    @Override
    public ItensCardapio buscarPorId(Long codigoItemCardapio) {
        return this.dataSource.buscarItensCardapioPorId(codigoItemCardapio);
    }

    @Override
    public void deletarItem(Long codigoItemCardapio) {
        this.dataSource.deletarItemCardapio(codigoItemCardapio);
    }

    @Override
    public ItensCardapio atualizarItemCardapio(ItensCardapio itemCardapioAtualizado) {
        return this.dataSource.atualizarItemCardapio(itemCardapioAtualizado);
    }
}
