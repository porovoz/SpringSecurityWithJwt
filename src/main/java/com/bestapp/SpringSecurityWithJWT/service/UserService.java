package com.bestapp.SpringSecurityWithJWT.service;

import com.bestapp.SpringSecurityWithJWT.dto.Register;
import com.bestapp.SpringSecurityWithJWT.dto.User;

import java.util.Optional;

public interface UserService {

    /**
     * Creating a new user object
     * @param register object containing all necessary information for creation a user object
     */
    void createUser(Register register);

    /**
     * Getting user by email
     * @param email user email in database
     */
    Optional<User> getByEmail(String email);
}
