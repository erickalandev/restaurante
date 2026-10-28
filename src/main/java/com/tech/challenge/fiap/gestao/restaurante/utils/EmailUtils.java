package com.tech.challenge.fiap.gestao.restaurante.utils;

import org.springframework.util.StringUtils;

public class EmailUtils {

    public static void validaEmail(String email) {
        if (!isEmailValido(email)) {
            throw new IllegalArgumentException("E-mail informado invalido.");
        }
    }

    private static boolean isEmailValido(String email) {
        if (!StringUtils.hasText(email)) {
            return false;
        }

        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(regex);
    }
}
