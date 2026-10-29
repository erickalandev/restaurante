package com.tech.challenge.fiap.gestao.restaurante.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ExceptionControllerAdviceTest {

    private final ExceptionControllerAdvice advice = new ExceptionControllerAdvice();

    @Test
    void deveResponderComUnprocessableEntityQuandoIllegalArgumentExceptionOcorrer() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn("/api/v1/teste");

        IllegalArgumentException ex = new IllegalArgumentException("Dados incorretos");

        ResponseEntity<ExceptionControllerAdvice.ErrorResponse> response =
                advice.handleIllegalArgumentException(ex, request);

        assertEquals(HttpStatus.UNPROCESSABLE_CONTENT, response.getStatusCode());
        assertEquals("Regra de negocio", response.getBody().title());
        assertEquals("Dados incorretos.", response.getBody().detail());
        assertEquals("/api/v1/teste", response.getBody().instance());
        assertEquals("message", response.getBody().errors().get(0).field());
        assertEquals("Dados incorretos", response.getBody().errors().get(0).message());
    }

    @Test
    void deveResponderComNotFoundQuandoRegistroNaoForEncontrado() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn("/api/v1/restaurante");

        RestauranteNaoEncontradoException ex = new RestauranteNaoEncontradoException("Restaurante nao encontrado");

        ResponseEntity<ExceptionControllerAdvice.ErrorResponse> response =
                advice.handleDadosNaoEncontradoException(ex, request);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Dados nao encontrados.", response.getBody().detail());
        assertEquals("Restaurante nao encontrado", response.getBody().errors().get(0).message());
    }

    @Test
    void deveResponderComConflictQuandoRegistroJaExistir() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn("/api/v1/restaurante");

        RestauranteEncontradoException ex = new RestauranteEncontradoException("Restaurante ja existente.");

        ResponseEntity<ExceptionControllerAdvice.ErrorResponse> response =
                advice.handleDadosEncontradoException(ex, request);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Dados encontrados.", response.getBody().detail());
        assertEquals("Restaurante ja existente.", response.getBody().errors().get(0).message());
    }

    @Test
    void deveResponderComErroInternoQuandoOcorrerExcecaoGenerica() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn("/api/v1/erro");

        RuntimeException ex = new RuntimeException("Erro inesperado");

        ResponseEntity<ExceptionControllerAdvice.ErrorResponse> response =
                advice.handleGenericException(ex, request);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Internal Server Error", response.getBody().title());
        assertEquals("Ocorreu um erro interno no servidor.", response.getBody().detail());
        assertEquals("Erro inesperado", response.getBody().errors().get(0).message());
    }
}
