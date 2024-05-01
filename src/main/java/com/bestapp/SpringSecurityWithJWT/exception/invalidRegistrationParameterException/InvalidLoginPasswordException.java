package com.bestapp.SpringSecurityWithJWT.exception.invalidRegistrationParameterException;

public class InvalidLoginPasswordException extends InvalidRegistrationParameterException {

    public InvalidLoginPasswordException(String message) {
        super("Check if login and password are correct");
    }
}
