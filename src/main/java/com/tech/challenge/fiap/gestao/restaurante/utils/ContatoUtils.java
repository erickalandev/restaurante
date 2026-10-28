package com.tech.challenge.fiap.gestao.restaurante.utils;

import org.springframework.util.StringUtils;

public class ContatoUtils {

    private static void validaContato(String contato) {
        if (!StringUtils.hasText(contato)) {
            return;
        }

        String telefone = contato.replaceAll("\\D", "");
        if (telefone.length() != 10 && telefone.length() != 11) {
            throw new IllegalArgumentException("O campo 'contato' deve conter um número de telefone válido.");
        }
    }

    public static String formatarContato(String contato) {
        validaContato(contato);
        return contato.replaceAll("\\D", "");
    }
}
