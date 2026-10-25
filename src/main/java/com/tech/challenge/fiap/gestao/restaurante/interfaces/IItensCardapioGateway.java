package com.tech.challenge.fiap.gestao.restaurante.interfaces;

import com.tech.challenge.fiap.gestao.restaurante.entities.ItensCardapio;

import java.util.List;

public interface IItensCardapioGateway {
    List<ItensCardapio> salvarTodosItens(List<ItensCardapio> itensCardapio);
    ItensCardapio buscarPorId(Long codigoItemCardapio);
    void deletarItem(Long codigoItemCardapio);
    ItensCardapio atualizarItemCarpio(ItensCardapio itemCardapioAtualizado);
}
