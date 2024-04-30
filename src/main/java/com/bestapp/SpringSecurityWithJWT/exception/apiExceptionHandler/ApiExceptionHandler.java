package com.bestapp.SpringSecurityWithJWT.exception.apiExceptionHandler;

import com.bestapp.SpringSecurityWithJWT.exception.InvalidTokenException;
import com.bestapp.SpringSecurityWithJWT.exception.invalidRegistrationParameterException.InvalidRegistrationParameterException;
import com.bestapp.SpringSecurityWithJWT.exception.notFoundException.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * An auxiliary class for handling exceptions that occur when controllers are running.
 */
@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String notFoundExceptionHandler(NotFoundException e) {
        return e.getMessage();
    }

    @ExceptionHandler(InvalidRegistrationParameterException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String invalidParameterExceptionHandler(InvalidRegistrationParameterException e) {
        return e.getMessage();
    }

    @ExceptionHandler(InvalidTokenException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String invalidTokenExceptionHandler(InvalidTokenException e) {
        return e.getMessage();
    }
}
