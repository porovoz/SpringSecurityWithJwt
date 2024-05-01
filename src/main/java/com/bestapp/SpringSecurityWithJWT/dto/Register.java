package com.bestapp.SpringSecurityWithJWT.dto;

import com.bestapp.SpringSecurityWithJWT.model.RoleType;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class Register {

    @Size(min = 2, max = 32)
    private String username;

    @Size(min = 4, max = 32)
    private String email;

    @Size(min = 8, max = 16)
    private String password;

    @Size(min = 4, max = 16)
    private RoleType role;
}
