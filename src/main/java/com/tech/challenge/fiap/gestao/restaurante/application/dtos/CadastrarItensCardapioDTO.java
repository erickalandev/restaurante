package com.tech.challenge.fiap.gestao.restaurante.application.dtos;

import java.util.List;

public record CadastrarItensCardapioDTO(
        List<ItensCardapioDTO> itensCardapio
) {
}
