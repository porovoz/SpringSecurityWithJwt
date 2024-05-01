package com.bestapp.SpringSecurityWithJWT.service.imp;

import com.bestapp.SpringSecurityWithJWT.dto.Register;
import com.bestapp.SpringSecurityWithJWT.dto.User;
import com.bestapp.SpringSecurityWithJWT.exception.notFoundException.UserNotFoundException;
import com.bestapp.SpringSecurityWithJWT.model.UserEntity;
import com.bestapp.SpringSecurityWithJWT.repository.UserRepository;
import com.bestapp.SpringSecurityWithJWT.service.UserMapper;
import com.bestapp.SpringSecurityWithJWT.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Implementation of the service to work with the users
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    /**
     * Creating a new user object
     * - Converting created register data transfer object into user object {@link UserMapper#toUserEntity(Register)}.<br>
     * @param register object containing all necessary information for creation a new user object
     */
    @Override
    @Transactional
    public void createUser(Register register) {
        log.info("Create user method was invoked");
        userRepository.save(userMapper.toUserEntity(register));
        log.info("User was created successfully");
    }

    /** Getting user by email.<br>
     * - Search for a user by email {@link UserRepository#findByEmail(String)}.<br>
     * - Converting found user object into user data transfer object {@link UserMapper#toUser(UserEntity)}.
     * @param email user email in database
     * @throws UserNotFoundException if user object was not found
     * @throws IllegalArgumentException if {@literal email} is {@literal null}.
     * @return {@link Optional<User>} - found optional of user data transfer object
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<User> getByEmail(String email) {
        UserEntity userEntity = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(super.toString()));
        return Optional.of(userMapper.toUser(userEntity));
    }
}
