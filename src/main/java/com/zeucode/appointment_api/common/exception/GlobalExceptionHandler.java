package com.zeucode.appointment_api.common.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1. Atrapa nuestros errores de "No Encontrado" (Código 404)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleResourceNotFoundException(
        ResourceNotFoundException ex, HttpServletRequest request) {

        ApiErrorResponse response = ApiErrorResponse.builder()
            .timestamp(Instant.now())
            .status(HttpStatus.NOT_FOUND.value())
            .error(HttpStatus.NOT_FOUND.getReasonPhrase())
            .message(ex.getMessage())
            .path(request.getRequestURI())
            .build();

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    // 3. Atrapa errores de Reglas de Negocio (Código 409 - Conflicto)
    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ApiErrorResponse> handleConflictException(
        ConflictException ex, HttpServletRequest request) {

        ApiErrorResponse response = ApiErrorResponse.builder()
            .timestamp(Instant.now())
            .status(HttpStatus.CONFLICT.value())
            .error(HttpStatus.CONFLICT.getReasonPhrase())
            .message(ex.getMessage())
            .path(request.getRequestURI())
            .build();

        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    // 2. Atrapa CUALQUIER otro error no controlado (Código 500 - El comodín salvavidas)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGlobalException(
        Exception ex, HttpServletRequest request) {

        // NOTA SENIOR: En producción, aquí agregarías un log.error(ex.getMessage(), ex); para guardar el error real en tu servidor.

        ApiErrorResponse response = ApiErrorResponse.builder()
            .timestamp(Instant.now())
            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
            .message("An unexpected error occurred. Please contact support.") // Ocultamos el error real al cliente
            .path(request.getRequestURI())
            .build();

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
