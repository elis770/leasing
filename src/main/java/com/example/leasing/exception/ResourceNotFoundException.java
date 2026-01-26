package com.example.leasing.exception;

/**
 * Excepción personalizada para recursos no encontrados
 * Se lanza cuando se busca una entidad por ID y no existe
 */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
    
    public ResourceNotFoundException(String resource, Long id) {
        super(String.format("%s con ID %d no encontrado", resource, id));
    }
}