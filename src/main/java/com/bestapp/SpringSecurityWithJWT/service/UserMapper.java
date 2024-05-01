package com.bestapp.SpringSecurityWithJWT.service;

import com.bestapp.SpringSecurityWithJWT.dto.Register;
import com.bestapp.SpringSecurityWithJWT.dto.User;
import com.bestapp.SpringSecurityWithJWT.model.UserEntity;

/**
 * The {@code UserMapper} is an interface responsible for converting user entities (UserEntity) and DTO
 * (Data Transfer Object) to various formats and vice versa.
 */
public interface UserMapper {

    UserEntity toUserEntity(Register register);
    User toUser(UserEntity userEntity);
}
