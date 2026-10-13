package com.tech.challenge.fiap.gestao.restaurante.dtos;

import com.tech.challenge.fiap.gestao.restaurante.model.Endereco;
import com.tech.challenge.fiap.gestao.restaurante.model.Restaurante;

import java.util.List;

public record ConsultarRestauranteResponse(
        String donoRestaurante,
        String nome,
        String tipoCozinha,
        String horarioFuncionamento,
        String contato,
        List<EnderecoDTO> enderecos
) {

    public static ConsultarRestauranteResponse toResponse(Restaurante restaurante) {
        return new ConsultarRestauranteResponse(
                restaurante.getDonoRestaurante().getNome(),
                restaurante.getNome(),
                restaurante.getTipoCozinha() == null ? null : restaurante.getTipoCozinha().name().toLowerCase(),
                restaurante.getHorarioFuncionamento(),
                restaurante.getContato(),
                Endereco.toEnderecosDTO(restaurante.getEnderecos())
        );
    }
}
