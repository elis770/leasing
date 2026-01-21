package com.example.leasing.exception;

/**
 * Excepción personalizada para validaciones de negocio
 * Se lanza cuando una operación viola reglas de negocio
 */
public class BusinessValidationException extends RuntimeException {
    public BusinessValidationException(String message) {
        super(message);
    }
}
