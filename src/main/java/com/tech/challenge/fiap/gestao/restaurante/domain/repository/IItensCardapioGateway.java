package com.tech.challenge.fiap.gestao.restaurante.domain.repository;

import com.tech.challenge.fiap.gestao.restaurante.domain.entities.ItensCardapio;

import java.util.List;

public interface IItensCardapioGateway {
    List<ItensCardapio> salvarTodosItens(List<ItensCardapio> itensCardapio);
    ItensCardapio buscarPorId(Long codigoItemCardapio);
    void deletarItem(Long codigoItemCardapio);
    ItensCardapio atualizarItemCardapio(ItensCardapio itemCardapioAtualizado);
}
