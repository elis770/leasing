package com.example.leasing.exception;

/**
 * Excepción personalizada para conflictos de disponibilidad
 * Se lanza cuando se intenta reservar un auto que no está disponible
 */
public class UnavailableResourceException extends RuntimeException {
    public UnavailableResourceException(String message) {
        super(message);
    }
}
