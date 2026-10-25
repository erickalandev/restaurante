package com.tech.challenge.fiap.gestao.restaurante.utils;

import org.springframework.util.StringUtils;

public abstract class CpfUtils {

    private static void validaCpf(String cpf) {
        if (!isCpfValido(cpf)) {
            throw new IllegalArgumentException("CPF informado invalido.");
        }
    }

    public static String formatarCpf(String cpf) {
        validaCpf(cpf);
        return cpf.replaceAll("\\D", "");
    }

    private static boolean isCpfValido(String cpf) {
        if (StringUtils.hasText(cpf)) {
            return false;
        }

        cpf = cpf.replaceAll("\\D", "");

        if (cpf.length() != 11) {
            return false;
        }

        String finalCpf = cpf;
        boolean todosNumerosIguais = cpf.chars().allMatch(ch -> ch == finalCpf.charAt(0));
        if (todosNumerosIguais) {
            return false;
        }

        int soma = 0;
        int peso = 10;

        for (int i = 0; i < 9; i++) {
            soma += (cpf.charAt(i) - '0') * peso;
            peso--;
        }

        int digito1 = 11 - (soma % 11);
        if (digito1 >= 10) {
            digito1 = 0;
        }

        soma = 0;
        peso = 11;

        for (int i = 0; i < 10; i++) {
            soma += (cpf.charAt(i) - '0') * peso;
            peso--;
        }

        int digito2 = 11 - (soma % 11);
        if (digito2 >= 10) {
            digito2 = 0;
        }

        return (cpf.charAt(9) - '0') == digito1 && (cpf.charAt(10) - '0') == digito2;
    }
}