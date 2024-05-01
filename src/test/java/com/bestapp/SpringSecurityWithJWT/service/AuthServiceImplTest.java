package com.bestapp.SpringSecurityWithJWT.service;

import com.bestapp.SpringSecurityWithJWT.dto.Register;
import com.bestapp.SpringSecurityWithJWT.dto.User;
import com.bestapp.SpringSecurityWithJWT.model.RoleType;
import com.bestapp.SpringSecurityWithJWT.repository.UserRepository;
import com.bestapp.SpringSecurityWithJWT.security.JWTProvider;
import com.bestapp.SpringSecurityWithJWT.security.dto.JWTRequest;
import com.bestapp.SpringSecurityWithJWT.security.dto.JWTResponse;
import com.bestapp.SpringSecurityWithJWT.service.imp.AuthServiceImpl;
import com.bestapp.SpringSecurityWithJWT.service.imp.UserServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static com.bestapp.SpringSecurityWithJWT.model.RoleType.USER;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepositoryMock;

    @Mock
    private JWTProvider jwtProvider;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthServiceImpl authService;

    @Test
    @DisplayName("User registration")
    void registerTest() {

        Register register = new Register();
        register.setUsername("username");
        register.setEmail("username@gmail.com");
        register.setPassword("password");
        register.setRole(RoleType.USER);

        boolean result = authService.register(register);
        assertTrue(result);

        verify(userRepositoryMock, times(1)).existsByEmail(any(String.class));
        verify(userService, times(1)).createUser(any(Register.class));
    }

    @Test
    @DisplayName("Check of successful user login")
    void loginTest() {
        JWTRequest jwtRequest = new JWTRequest();
        jwtRequest.setEmail("user@gmail.com");
        jwtRequest.setPassword("password");

        User user = new User();
        user.setUsername("username");
        user.setEmail("user@gmail.com");
        user.setPassword("password");
        user.setRole(USER);

        JWTResponse jwtResponse = new JWTResponse(
                "tJzuMeY5zRp+x2cWzuSMC9fsTiwB7Swmxjce/dpUPLi14XiFKbMcqVqDXQhq1L/FQ/az+3bGkmD/g59Selgzog==",
                "ruTPPXeXNgIu+ZZVFoAIC6ERQ33esJ/hURLiFjkJJhCVp6hRGDjzSF0nwHfJj7gK/nf4X/fRUgMnfVMqf+yJpw=="
        );

        when(userService.getByEmail(jwtRequest.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(jwtRequest.getPassword(), user.getPassword())).thenReturn(true);
        when(jwtProvider.generateAccessToken(user)).thenReturn(jwtResponse.getAccessToken());
        when(jwtProvider.generateRefreshToken(user)).thenReturn(jwtResponse.getRefreshToken());

        JWTResponse actual = authService.login(jwtRequest);

        assertEquals(jwtResponse.getAccessToken(), actual.getAccessToken());
        assertEquals(jwtResponse.getRefreshToken(), actual.getRefreshToken());

        verify(jwtProvider, times(1)).generateAccessToken(user);
        verify(jwtProvider, times(1)).generateRefreshToken(user);
    }
}
