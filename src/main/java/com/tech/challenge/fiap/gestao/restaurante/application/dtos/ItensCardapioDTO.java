package com.tech.challenge.fiap.gestao.restaurante.application.dtos;

import java.math.BigDecimal;

public record ItensCardapioDTO(

        Long codigoItemCardapio,
        String nome,
        BigDecimal preco,
        String descricao,
        Boolean disponibilidadeComercioLocal,
        String foto
) {
}
