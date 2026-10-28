package com.tech.challenge.fiap.gestao.restaurante.utils;

public class FiltroUtils {

    public static String normalizarTexto(String valor) {
        return valor == null || valor.isBlank() ? null : valor.trim();
    }

    public static String normalizarCpf(String cpf) {
        if (cpf == null || cpf.isBlank()) {
            return null;
        }
        return CpfUtils.formatarCpf(cpf.trim());
    }
}
