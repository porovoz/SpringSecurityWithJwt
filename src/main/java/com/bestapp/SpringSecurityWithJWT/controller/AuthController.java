package com.bestapp.SpringSecurityWithJWT.controller;

import com.bestapp.SpringSecurityWithJWT.security.dto.RefreshJWTRequest;
import com.bestapp.SpringSecurityWithJWT.security.dto.JWTRequest;
import com.bestapp.SpringSecurityWithJWT.security.dto.JWTResponse;
import com.bestapp.SpringSecurityWithJWT.dto.Register;
import com.bestapp.SpringSecurityWithJWT.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for user registration and authorization
 * @see JWTRequest
 * @see RefreshJWTRequest
 * @see Register
 * @see AuthService
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * Register a new user.
     * @return the HTTP 201 status code (Created).<br>
     */
    @PostMapping("/register")
    @Operation(
            summary = "User registration",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Created", content = @Content(schema = @Schema(hidden = true)))
            },
            tags = "Registration"
    )
    public ResponseEntity<?> register(@RequestBody Register register) {
        if (authService.register(register)) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Authorise user.
     * @return the HTTP 200 status code (OK).<br>
     */
    @Operation(
            summary = "User authorization",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(hidden = true)))
            },
            tags = "Authorization"
    )
    @PostMapping("/login")
    public ResponseEntity<JWTResponse> login(@RequestBody JWTRequest authRequest) {
        final JWTResponse tokenResponse = authService.login(authRequest);
        return ResponseEntity.ok(tokenResponse);
    }

    /**
     * Get a new access token when it became invalid.
     * @return the HTTP 200 status code (OK).<br>
     */
    @Operation(
            summary = "Gets a new access token when it became invalid",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(hidden = true)))
            },
            tags = "Getting a new access token"
    )
    @PostMapping("/token")
    public ResponseEntity<JWTResponse> getNewAccessToken(@RequestBody RefreshJWTRequest request) {
        final JWTResponse tokenResponse = authService.getAccessToken(request.getRefreshToken());
        return ResponseEntity.ok(tokenResponse);
    }

    /**
     * Get a new access and refresh tokens when it became invalid.
     * @return the HTTP 200 status code (OK).<br>
     */
    @Operation(
            summary = "Gets a new access and refresh tokens when it became invalid",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(hidden = true)))
            },
            tags = "Getting a new access and refresh tokens"
    )
    @PostMapping("/refresh")
    public ResponseEntity<JWTResponse> getNewRefreshToken(@RequestBody RefreshJWTRequest request) {
        final JWTResponse tokenResponse = authService.refreshToken(request.getRefreshToken());
        return ResponseEntity.ok(tokenResponse);
    }
}
