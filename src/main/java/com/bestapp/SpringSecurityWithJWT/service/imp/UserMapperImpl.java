package com.bestapp.SpringSecurityWithJWT.service.imp;

import com.bestapp.SpringSecurityWithJWT.dto.Register;
import com.bestapp.SpringSecurityWithJWT.dto.User;
import com.bestapp.SpringSecurityWithJWT.model.UserEntity;
import com.bestapp.SpringSecurityWithJWT.service.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * The {@code UserMapperImpl} class is a component responsible for converting user entities (UserEntity) and DTO
 * (Data Transfer Object) to various formats and vice versa.
 */
@Component
@RequiredArgsConstructor
public class UserMapperImpl implements UserMapper {

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserEntity toUserEntity(Register register) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(register.getUsername());
        userEntity.setEmail(register.getEmail());
        userEntity.setPassword(passwordEncoder.encode(register.getPassword()));
        userEntity.setRole(register.getRole());
        return userEntity;
    }

    @Override
    public User toUser(UserEntity userEntity) {
        User user = new User();
        user.setUsername(userEntity.getUsername());
        user.setEmail(userEntity.getEmail());
        user.setPassword(userEntity.getPassword());
        user.setRole(userEntity.getRole());
        return user;
    }
}
