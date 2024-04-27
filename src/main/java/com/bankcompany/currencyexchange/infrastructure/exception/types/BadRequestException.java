package com.bankcompany.currencyexchange.infrastructure.exception.types;

public class BadRequestException extends RuntimeException {

    public BadRequestException(String message, Object... args) {
        super(String.format(message, args));
    }
}
