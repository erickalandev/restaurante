package com.tech.challenge.fiap.gestao.restaurante.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.MDC;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.*;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        List<FieldErrorDetail> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .sorted(Comparator.comparing(FieldError::getField))
                .map(this::toFieldErrorDetail)
                .toList();

        return ResponseEntity.badRequest()
                .body(buildError(
                        HttpStatus.BAD_REQUEST,
                        "Validation failed",
                        "Um ou mais campos da requisição são inválidos.",
                        request.getRequestURI(),
                        errors
                ));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex, HttpServletRequest request) {
        FieldErrorDetail errorDetail = new FieldErrorDetail(ex.getParameterName(), "Parâmetro de requisição ausente.");

        return ResponseEntity.badRequest()
                .body(buildError(
                        HttpStatus.BAD_REQUEST,
                        "Missing request parameter",
                        "Um ou mais parâmetros de requisição estão ausentes.",
                        request.getRequestURI(),
                        List.of(errorDetail)
                ));
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ErrorResponse> handleHandlerMethodValidationException(HandlerMethodValidationException ex, HttpServletRequest request) {
        List<FieldErrorDetail> errors = ex.getParameterValidationResults().stream()
                .flatMap(result -> result.getResolvableErrors().stream())
                .map(violation -> new FieldErrorDetail(
                        Objects.requireNonNull(Arrays.stream(Objects.requireNonNull(violation.getArguments()))
                                .filter(arg -> arg instanceof org.springframework.context.support.DefaultMessageSourceResolvable)
                                .map(arg -> ((org.springframework.context.support.DefaultMessageSourceResolvable) arg).getDefaultMessage())
                                .findFirst()
                                .orElse("unknown")),
                        violation.getDefaultMessage()
                ))
                .toList();


        return ResponseEntity.badRequest()
                .body(buildError(
                        HttpStatus.BAD_REQUEST,
                        "Validation failed",
                        "Um ou mais campos da requisição são inválidos.",
                        request.getRequestURI(),
                        errors
                ));
    }

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

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<ErrorResponse> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex, HttpServletRequest request) {
        FieldErrorDetail errorDetail = new FieldErrorDetail("result", ex.getMessage() != null ? ex.getMessage() : "Dado não encontrado.");
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(buildError(
                        HttpStatus.NOT_FOUND,
                        "Resource Not Found",
                        "O Dado solicitado não foi encontrado.",
                        "",
                        List.of(errorDetail)
                ));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(buildError(
                        HttpStatus.CONFLICT,
                        "Data Integrity Violation",
                        "Não é possível realizar a operação devido a violação de integridade dos dados.",
                        "",
                        List.of(new FieldErrorDetail("exception", "Violação de integridade dos dados."))
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

    private FieldErrorDetail toFieldErrorDetail(FieldError fieldError) {
        return new FieldErrorDetail(
                fieldError.getField(),
                fieldError.getDefaultMessage()
        );
    }

    private String resolveTraceId() {
        String traceId = MDC.get("traceId");
        return traceId != null ? traceId : UUID.randomUUID().toString();
    }
}
