package com.tech.challenge.fiap.gestao.restaurante.utils;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class LogUtilTest {

    @Test
    void deveRegistrarLogsDeInicioFimErro() {
        Logger logger = mock(Logger.class);
        Object dados = "payload";
        Exception exception = new IllegalStateException("falha");

        LogUtil.inicio(logger, "cliente", dados);
        LogUtil.fim(logger, "cliente", dados);
        LogUtil.erroNegocio(logger, "cliente", dados, exception);
        LogUtil.erroInesperado(logger, "cliente", dados, exception);

        verify(logger).info(">>> Iniciado controller de {}. Dados enviados: [{}]", "cliente", dados);
        verify(logger).info(">>> Finalizado controller de {}. Dados retornados: [{}]", "cliente", dados);
        verify(logger).error(">>> Erro de regra de negocio ao {}. Dados enviados: [{}], Excecao: {}",
                "cliente", dados, exception.getMessage());
        verify(logger).error(">>> Erro inesperado do sistema ao {}. Dados enviados: [{}], Excecao: {}",
                "cliente", dados, exception.getMessage());
    }

}