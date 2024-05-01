package com.bestapp.SpringSecurityWithJWT.security.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JWTRequest {

    @Size(min = 4, max = 32)
    private String email;

    @Size(min = 8, max = 16)
    private String password;
}
