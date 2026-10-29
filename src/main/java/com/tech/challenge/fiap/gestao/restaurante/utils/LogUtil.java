package com.tech.challenge.fiap.gestao.restaurante.utils;

import org.slf4j.Logger;

public final class LogUtil {

    private LogUtil() {}

    public static void inicio(Logger log, String operacao, Object dados) {
        log.info(">>> Iniciado controller de {}. Dados enviados: [{}]", operacao, dados);
    }

    public static void fim(Logger log, String operacao, Object dadosRetornados) {
        log.info(">>> Finalizado controller de {}. Dados retornados: [{}]", operacao, dadosRetornados);
    }

    public static void erroNegocio(Logger log, String operacao, Object dados, Exception e) {
        log.error(">>> Erro de regra de negocio ao {}. Dados enviados: [{}], Excecao: {}",
                operacao, dados, e.getMessage());
    }

    public static void erroInesperado(Logger log, String operacao, Object dados, Exception e) {
        log.error(">>> Erro inesperado do sistema ao {}. Dados enviados: [{}], Excecao: {}",
                operacao, dados, e.getMessage());
    }
}