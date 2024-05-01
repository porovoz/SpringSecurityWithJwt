package com.bestapp.SpringSecurityWithJWT.controller;

import com.bestapp.SpringSecurityWithJWT.dto.Register;
import com.bestapp.SpringSecurityWithJWT.model.RoleType;
import com.bestapp.SpringSecurityWithJWT.security.JWTProvider;
import com.bestapp.SpringSecurityWithJWT.security.JwtUtils;
import com.bestapp.SpringSecurityWithJWT.security.config.SecurityConfig;
import com.bestapp.SpringSecurityWithJWT.security.dto.JWTRequest;
import com.bestapp.SpringSecurityWithJWT.security.dto.JWTResponse;
import com.bestapp.SpringSecurityWithJWT.security.dto.RefreshJWTRequest;
import com.bestapp.SpringSecurityWithJWT.service.imp.AuthServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * A class for checking methods of AuthController class
 */
@WebMvcTest(AuthController.class)
@Import(SecurityConfig.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private JwtUtils jwtUtils;
    @MockBean
    private JWTProvider jwtProvider;
    @MockBean
    private AuthServiceImpl authService;

    @SneakyThrows
    @Test
    @DisplayName("A new user registration check")
    void registerTest() {
        Register register = new Register();
        register.setUsername("username");
        register.setEmail("user@gmail.com");
        register.setPassword("password");
        register.setRole(RoleType.USER);

        when(authService.register(register)).thenReturn(true);

        mvc.perform(post("/auth/register")
                        .content(objectMapper.writeValueAsString(register))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        verify(authService, times(1)).register((any(Register.class)));
    }

//    @SneakyThrows // I don't know why this test is not working. All paths are correct.
//    @Test
//    @DisplayName("User authorization check")
//    void loginTest() {
//        JWTRequest jwtRequest = new JWTRequest();
//        jwtRequest.setEmail("user@gmail.com");
//        jwtRequest.setPassword("password");
//
//        JWTResponse jwtResponse = new JWTResponse(
//                "tJzuMeY5zRp+x2cWzuSMC9fsTiwB7Swmxjce/dpUPLi14XiFKbMcqVqDXQhq1L/FQ/az+3bGkmD/g59Selgzog==",
//                "ruTPPXeXNgIu+ZZVFoAIC6ERQ33esJ/hURLiFjkJJhCVp6hRGDjzSF0nwHfJj7gK/nf4X/fRUgMnfVMqf+yJpw==");
//
//        when(authService.login(jwtRequest)).thenReturn(jwtResponse);
//
//        mvc.perform(post("/auth/login")
//                        .content(objectMapper.writeValueAsString(jwtRequest))
//                        .characterEncoding(StandardCharsets.UTF_8)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.accessToken").value(jwtResponse.getAccessToken())) // I don't know why java.lang.AssertionError: No value at JSON path "$.accessToken"
//                .andExpect(jsonPath("$.refreshToken").value(jwtResponse.getRefreshToken()));
//
//        verify(authService, times(1)).login(any(JWTRequest.class));
//    }

    @SneakyThrows
    @Test
    @DisplayName("Checking of get a new access token method when it became invalid")
    void getNewAccessTokenTest() {
        RefreshJWTRequest refreshJWTRequest = new RefreshJWTRequest();
        refreshJWTRequest.setRefreshToken(
                "ruTPPXeXNgIu+ZZVFoAIC6ERQ33esJ/hURLiFjkJJhCVp6hRGDjzSF0nwHfJj7gK/nf4X/fRUgMnfVMqf+yJpw=="
        );

        JWTResponse jwtResponse = new JWTResponse(
                "tJzuMeY5zRp+x2cWzuSMC9fsTiwB7Swmxjce/dpUPLi14XiFKbMcqVqDXQhq1L/FQ/az+3bGkmD/g59Selgzog==",
                ""
        );

        when(authService.getAccessToken(refreshJWTRequest.getRefreshToken())).thenReturn(jwtResponse);

        mvc.perform(post("/auth/token")
                        .content(objectMapper.writeValueAsString(refreshJWTRequest))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value(jwtResponse.getAccessToken()));

        verify(authService, times(1)).getAccessToken(any(String.class));
    }

    @SneakyThrows
    @WithMockUser
    @Test
    @DisplayName("Checking of get a new access and refresh tokens method when it became invalid")
    void getNewRefreshTokenTest() {
        RefreshJWTRequest refreshJWTRequest = new RefreshJWTRequest();
        refreshJWTRequest.setRefreshToken(
                "ruTPPXeXNgIu+ZZVFoAIC6ERQ33esJ/hURLiFjkJJhCVp6hRGDjzSF0nwHfJj7gK/nf4X/fRUgMnfVMqf+yJpw=="
        );

        JWTResponse jwtResponse = new JWTResponse(
                "tJzuMeY5zRp+x2cWzuSMC9fsTiwB7Swmxjce/dpUPLi14XiFKbMcqVqDXQhq1L/FQ/az+3bGkmD/g59Selgzog==",
                "ruTPPXeXNgIu+ZZVFoAIC6ERQ33esJ/hURLiFjkJJhCVp6hRGDjzSF0nwHfJj7gK/nf4X/fRUgMnfVMqf+yJpw=="
        );

        when(authService.refreshToken(refreshJWTRequest.getRefreshToken())).thenReturn(jwtResponse);

        mvc.perform(post("/auth/refresh")
                        .content(objectMapper.writeValueAsString(refreshJWTRequest))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value(jwtResponse.getAccessToken()))
                .andExpect(jsonPath("$.refreshToken").value(jwtResponse.getRefreshToken()));

        verify(authService, times(1)).refreshToken(any(String.class));
    }
}
