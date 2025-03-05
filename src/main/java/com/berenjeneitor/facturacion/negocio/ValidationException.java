package com.berenjeneitor.facturacion.negocio;

// Custom exception for validation errors
public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
}