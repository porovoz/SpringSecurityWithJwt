package com.bestapp.SpringSecurityWithJWT.service;

import com.bestapp.SpringSecurityWithJWT.security.dto.JWTRequest;
import com.bestapp.SpringSecurityWithJWT.security.dto.JWTResponse;
import com.bestapp.SpringSecurityWithJWT.dto.Register;
import lombok.NonNull;

public interface AuthService {

    /**
     * Registers a new user in the application.
     * @param register an object containing information about user registration.
     * @return <B>true</B> if the registration was successful, otherwise <B>false</B> .
     */
    boolean register(Register register);

    /**
     * Checks the correctness of the entered credentials when trying to log in.
     * @param authenticationRequest an object containing information to proceed successful login.
     */
    JWTResponse login(@NonNull JWTRequest authenticationRequest);

    /**
     * Gets a new access token when it became invalid.
     * @param refreshToken an object containing refresh token to get a new access token.
     */
    JWTResponse getAccessToken(@NonNull String refreshToken);

    /**
     * Gets a new access and refresh tokens when it became invalid.
     * @param refreshToken an object containing refresh token to get a new access and refresh tokens.
     */
    JWTResponse refreshToken(@NonNull String refreshToken);
}
