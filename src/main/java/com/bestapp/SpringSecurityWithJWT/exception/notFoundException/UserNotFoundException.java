package com.bestapp.SpringSecurityWithJWT.exception.notFoundException;

public class UserNotFoundException extends NotFoundException {

    public UserNotFoundException(String message) {
        super("User not found");
    }
}
