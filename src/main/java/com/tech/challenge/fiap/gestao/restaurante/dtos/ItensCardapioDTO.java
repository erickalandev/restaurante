package com.tech.challenge.fiap.gestao.restaurante.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ItensCardapioDTO(

        Long codigoItemCardapio,

        @NotBlank(message = "O nome do item do cardápio é obrigatório.")
        String nome,

        @NotNull(message = "O preço do item do cardápio é obrigatório.")
        BigDecimal preco,

        String descricao,
        Boolean disponibilidadeComercioLocal,
        String foto
) {
}
