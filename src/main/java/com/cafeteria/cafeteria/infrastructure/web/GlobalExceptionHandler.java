package com.cafeteria.cafeteria.infrastructure.web;

import com.cafeteria.cafeteria.domain.exception.DomainException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DomainException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorResponse handleDomainException(DomainException e) {
        return new ErrorResponse(422,e.getMessage(),LocalDateTime.now());
    }
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleGenericException(Exception e) {
        return new ErrorResponse(500,"Error Inteno do servidor",LocalDateTime.now());
    }


    record ErrorResponse(int status, String message, LocalDateTime timestamp) {}
}
