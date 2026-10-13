package com.tech.challenge.fiap.gestao.restaurante.exception;

public class FieldErrorDetail {
    private String field;
    private String message;

    public FieldErrorDetail(String field, String message) {
        this.field = field;
        this.message = message;
    }

    // Getters and setters
}
