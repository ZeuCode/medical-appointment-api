package com.zeucode.appointment_api.common.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Map;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL) // Oculta los campos que estén vacíos en el JSON final
public class ApiErrorResponse {
    private Instant timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;
    private Map<String, String> validationErrors; // Para cuando validemos formularios (ej. email inválido)
}
