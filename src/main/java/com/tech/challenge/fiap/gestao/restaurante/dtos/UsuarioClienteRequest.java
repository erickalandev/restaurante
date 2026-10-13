package com.tech.challenge.fiap.gestao.restaurante.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

import java.util.Set;

public record UsuarioClienteRequest(
        @NotBlank(message = "O campo 'nome' é obrigatório.")
        String nome,
        @NotBlank(message = "O campo 'cpf' é obrigatório.")
        @CPF(message = "O campo 'cpf' deve conter um CPF válido.")
        String cpf,
        @NotBlank(message = "O campo 'email' é obrigatório.")
        @Email(message = "O campo 'email' deve conter um e-mail válido.")
        String email,
        String contato,
        Set<EnderecoDTO> enderecos
) {
}
