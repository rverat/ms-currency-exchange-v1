package com.bankcompany.currencyexchange.infrastructure.exception.types;

public class ServiceUnavailableException extends RuntimeException {

    public ServiceUnavailableException(String message, Object... args) {
        super(String.format(message, args));
    }
}
