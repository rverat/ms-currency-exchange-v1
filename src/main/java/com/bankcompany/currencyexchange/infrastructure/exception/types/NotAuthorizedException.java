package com.bankcompany.currencyexchange.infrastructure.exception.types;

public class NotAuthorizedException extends RuntimeException {

    public NotAuthorizedException(String message, Object... args) {
        super(String.format(message, args));
    }
}
