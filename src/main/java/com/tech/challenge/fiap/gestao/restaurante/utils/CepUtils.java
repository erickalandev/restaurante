package com.tech.challenge.fiap.gestao.restaurante.utils;

import org.springframework.util.StringUtils;

public class CepUtils {

    public static String formatarCep(String cep) {
        validaCep(cep);
        return cep.replaceAll("\\D", "");
    }

    private static void validaCep(String cep) {
        if (!isCepValido(cep)) {
            throw new IllegalArgumentException("CEP informado invalido.");
        }
    }

    private static boolean isCepValido(String cep) {
        if (!StringUtils.hasText(cep)) {
            return true;
        }
        return cep.matches("^\\d{5}-\\d{3}$") || cep.matches("^\\d{8}$");
    }
}
