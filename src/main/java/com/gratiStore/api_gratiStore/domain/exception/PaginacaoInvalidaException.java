package com.gratiStore.api_gratiStore.domain.exception;

public class PaginacaoInvalidaException extends RuntimeException {
    public PaginacaoInvalidaException(String message) {
        super(message);
    }
}
