package com.tech.challenge.fiap.gestao.restaurante.exception;

import java.util.List;

public class ErrorResponse {
    private String traceId;
    private String title;
    private int status;
    private String detail;
    private String instance;
    private List<FieldErrorDetail> errors;

    public ErrorResponse(String traceId, String title, int status, String detail, String instance, List<FieldErrorDetail> errors) {
        this.traceId = traceId;
        this.title = title;
        this.status = status;
        this.detail = detail;
        this.instance = instance;
        this.errors = errors;
    }

}
