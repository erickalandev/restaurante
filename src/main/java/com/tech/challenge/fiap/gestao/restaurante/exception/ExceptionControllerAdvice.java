package com.tech.challenge.fiap.gestao.restaurante.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.*;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(buildError(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "Internal Server Error",
                        "Ocorreu um erro interno no servidor.",
                        request.getRequestURI(),
                        ex.getMessage() != null ? List.of(new FieldErrorDetail("exception", ex.getMessage())) : List.of()
                ));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex, HttpServletRequest request) {
        FieldErrorDetail errorDetail = new FieldErrorDetail("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT)
                .body(buildError(
                        HttpStatus.UNPROCESSABLE_CONTENT,
                        "Regra de negocio",
                        "Dados incorretos.",
                        request.getRequestURI(),
                        List.of(errorDetail)
                ));
    }

    @ExceptionHandler({ItemCardapioNaoEncontradoException.class, UsuarioNaoEncontradoException.class, RestauranteNaoEncontradoException.class})
    public ResponseEntity<ErrorResponse> handleDadosNaoEncontradoException(RuntimeException ex, HttpServletRequest request) {
        FieldErrorDetail errorDetail = new FieldErrorDetail("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(buildError(
                        HttpStatus.NOT_FOUND,
                        "Regra de negocio",
                        "Dados nao encontrados.",
                        request.getRequestURI(),
                        List.of(errorDetail)
                ));
    }

    @ExceptionHandler({UsuarioEncontradoException.class, RestauranteEncontradoException.class})
    public ResponseEntity<ErrorResponse> handleDadosEncontradoException(RuntimeException ex, HttpServletRequest request) {
        FieldErrorDetail errorDetail = new FieldErrorDetail("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(buildError(
                        HttpStatus.CONFLICT,
                        "Regra de negocio",
                        "Dados encontrados.",
                        request.getRequestURI(),
                        List.of(errorDetail)
                ));
    }

    private ErrorResponse buildError(HttpStatus status, String title, String detail, String instance, List<FieldErrorDetail> errors) {
        return new ErrorResponse(
                resolveTraceId(),
                title,
                status.value(),
                detail,
                instance,
                errors
        );
    }

    private String resolveTraceId() {
        String traceId = MDC.get("traceId");
        return traceId != null ? traceId : UUID.randomUUID().toString();
    }

    public record ErrorResponse(
            String traceId,
            String title,
            int status,
            String detail,
            String instance,
            List<FieldErrorDetail> errors
    ) {
    }

    public record FieldErrorDetail(
            String field,
            String message
    ) {
    }
}