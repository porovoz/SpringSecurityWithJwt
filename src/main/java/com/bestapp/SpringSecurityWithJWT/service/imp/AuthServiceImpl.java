package com.bestapp.SpringSecurityWithJWT.service.imp;

import com.bestapp.SpringSecurityWithJWT.dto.Register;
import com.bestapp.SpringSecurityWithJWT.dto.User;
import com.bestapp.SpringSecurityWithJWT.exception.InvalidTokenException;
import com.bestapp.SpringSecurityWithJWT.exception.invalidRegistrationParameterException.InvalidLoginPasswordException;
import com.bestapp.SpringSecurityWithJWT.exception.notFoundException.UserNotFoundException;
import com.bestapp.SpringSecurityWithJWT.model.RoleType;
import com.bestapp.SpringSecurityWithJWT.repository.UserRepository;
import com.bestapp.SpringSecurityWithJWT.security.JWTProvider;
import com.bestapp.SpringSecurityWithJWT.security.dto.JWTRequest;
import com.bestapp.SpringSecurityWithJWT.security.dto.JWTResponse;
import com.bestapp.SpringSecurityWithJWT.service.AuthService;
import com.bestapp.SpringSecurityWithJWT.service.UserMapper;
import com.bestapp.SpringSecurityWithJWT.service.UserService;
import io.jsonwebtoken.Claims;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.bestapp.SpringSecurityWithJWT.model.RoleType.USER;

/**
 * User registration and authorization service
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final JWTProvider jwtProvider;
    private final Map<String, String> refreshStorage = new HashMap<>();
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    /**
     * Registers a new user in the application and saves it to a database.<br>
     * - {@link UserService#createUser(Register)} method used to create a new user.<br>
     * - Converting register data transfer object into user object {@link UserMapper#toUserEntity(Register)}.
     * @param register an object containing information about user registration.
     * @return <B>true</B> if the registration was successful, otherwise <B>false</B>.
     */
    @Override
    public boolean register(Register register) {
        if (userRepository.existsByEmail(register.getEmail())) {
            log.info("User already exists.");
            return false;
        }
        RoleType role = (register.getRole() == null) ? USER : register.getRole();
        register.setRole(role);
        userService.createUser(register);
        log.info("User was successfully registered.");
        return true;
    }

    /**
     * Checks the correctness of the entered credentials when trying to log in.
     * - {@link UserService#getByEmail(String)} method used to get user by email from DB.<br>
     * - {@link JWTProvider#generateAccessToken(User)} method used to generate access token.<br>
     * - {@link JWTProvider#generateRefreshToken(User)} method used to generate refresh token.<br>
     * - {@link Map#put(Object, Object)} method used to save refresh token.<br>
     * @exception UserNotFoundException may throw if the user not found in DB.<br>
     * @exception InvalidLoginPasswordException may throw if entered password is incorrect.<br>
     * @param authenticationRequest an object containing information to proceed successful login.
     * @return {@link JWTResponse} - JWT response data transfer object with access and refresh tokens.
     */
    @Override
    public JWTResponse login(@NonNull JWTRequest authenticationRequest) {
        final User user = userService.getByEmail(authenticationRequest.getEmail())
                .orElseThrow(() -> new UserNotFoundException(super.toString()));
        if (passwordEncoder.matches(authenticationRequest.getPassword(), user.getPassword())) {
            final String accessToken = jwtProvider.generateAccessToken(user);
            final String refreshToken = jwtProvider.generateRefreshToken(user);
            refreshStorage.put(user.getEmail(), refreshToken);
            log.info("User was successfully logged in");
            return new JWTResponse(accessToken, refreshToken);
        } else {
            throw new InvalidLoginPasswordException("Incorrect password");
        }
    }

    /**
     * Gets a new access token when it became invalid.
     * - {@link JWTProvider#validateRefreshToken(String)} method used to validate entered refresh token.<br>
     * - {@link JWTProvider#getRefreshClaims(String)} method used to get refresh claims.<br>
     * - {@link Map#get(Object)} method used to get refresh token.<br>
     * - {@link UserService#getByEmail(String)} method used to get user by email from DB.<br>
     * - {@link JWTProvider#generateAccessToken(User)} method used to generate access token.<br>
     * @exception UserNotFoundException may throw if the user not found in DB.<br>
     * @exception InvalidTokenException may throw if entered refresh token is invalid.<br>
     * @param refreshToken an object containing refresh token to get a new access token.<br>
     * @return {@link JWTResponse} - JWT response data transfer object with a new access token.
     */
    @Override
    public JWTResponse getAccessToken(@NonNull String refreshToken) {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String email = claims.getSubject();
            final String saveRefreshToken = refreshStorage.get(email);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final User user = userService.getByEmail(email)
                        .orElseThrow(() -> new UserNotFoundException(super.toString()));
                final String accessToken = jwtProvider.generateAccessToken(user);
                log.info("New access token was successfully got");
                return new JWTResponse(accessToken, "");
            }
        }
        throw new InvalidTokenException("Invalid refresh token");
    }


    /**
     * Gets a new access and refresh tokens when it became invalid.
     * - {@link JWTProvider#validateRefreshToken(String)} method used to validate entered refresh token.<br>
     * - {@link JWTProvider#getRefreshClaims(String)} method used to get refresh claims.<br>
     * - {@link Map#get(Object)} method used to get refresh token.<br>
     * - {@link UserService#getByEmail(String)} method used to get user by email from DB.<br>
     * - {@link JWTProvider#generateAccessToken(User)} method used to generate access token.<br>
     * - {@link JWTProvider#generateRefreshToken(User)} method used to generate refresh token.<br>
     * - {@link Map#put(Object, Object)} method used to save refresh token.<br>
     * @exception UserNotFoundException may throw if the user not found in DB.<br>
     * @exception InvalidTokenException may throw if entered refresh token is invalid.<br>
     * @param refreshToken an object containing refresh token to get a new access and refresh tokens.
     * @return {@link JWTResponse} - JWT response data transfer object with a new access and refresh tokens.
     */
    @Override
    public JWTResponse refreshToken(@NonNull String refreshToken) {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String email = claims.getSubject();
            final String saveRefreshToken = refreshStorage.get(email);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final User user = userService.getByEmail(email)
                        .orElseThrow(() -> new UserNotFoundException(super.toString()));
                final String accessToken = jwtProvider.generateAccessToken(user);
                final String newRefreshToken = jwtProvider.generateRefreshToken(user);
                refreshStorage.put(user.getEmail(), newRefreshToken);
                log.info("New access and refresh tokens was successfully got");
                return new JWTResponse(accessToken, newRefreshToken);
            }
            log.info("Tokens are not equal.");
        }
        throw new InvalidTokenException("Invalid refresh token");
    }
}
