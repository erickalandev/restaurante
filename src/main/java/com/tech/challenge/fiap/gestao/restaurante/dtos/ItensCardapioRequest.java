package com.tech.challenge.fiap.gestao.restaurante.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.Set;

public record ItensCardapioRequest(

        @NotBlank(message = "O nome do restaurante é obrigatório.")
        String nomeRestaurante,

        @NotBlank(message = "O dono do restaurante é obrigatório.")
        String donoRestaurante,

        @NotEmpty(message = "A lista de itens do cardápio não pode estar vazia.")
        Set<@Valid ItensCardapioDTO> itensCardapio

) {
}
