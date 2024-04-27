package com.bankcompany.currencyexchange.infrastructure.exception;

import com.bankcompany.currencyexchange.infrastructure.exception.types.BadRequestException;
import com.bankcompany.currencyexchange.infrastructure.exception.types.NotAuthorizedException;
import com.bankcompany.currencyexchange.infrastructure.exception.types.NotFoundException;
import com.bankcompany.currencyexchange.infrastructure.exception.types.ServiceUnavailableException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorMessage> handleNotFoundException(NotFoundException ex) {
        var errorMessage = new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                new Date(),
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorMessage> handleNotFoundException(BadRequestException ex) {
        var errorMessage = new ErrorMessage(
                HttpStatus.BAD_REQUEST.value(),
                new Date(),
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(NotAuthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ErrorMessage> handleNotFoundException(NotAuthorizedException ex) {
        var errorMessage = new ErrorMessage(
                HttpStatus.UNAUTHORIZED.value(),
                new Date(),
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMessage);
    }

    @ExceptionHandler(ServiceUnavailableException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public ResponseEntity<ErrorMessage> handleServiceUnavailableException(ServiceUnavailableException ex) {
        var errorMessage = new ErrorMessage(
                HttpStatus.SERVICE_UNAVAILABLE.value(),
                new Date(),
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(errorMessage);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorMessage> handleException(Exception ex, WebRequest request) {
        var errorMessage = new ErrorMessage(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                new Date(),
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
    }
}
