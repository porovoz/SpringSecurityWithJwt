package com.bestapp.SpringSecurityWithJWT.service;

import com.bestapp.SpringSecurityWithJWT.dto.Register;
import com.bestapp.SpringSecurityWithJWT.dto.User;
import com.bestapp.SpringSecurityWithJWT.model.RoleType;
import com.bestapp.SpringSecurityWithJWT.model.UserEntity;
import com.bestapp.SpringSecurityWithJWT.service.imp.UserMapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

/**
 * A class for checking methods of UserMapperImpl class
 * @see com.bestapp.SpringSecurityWithJWT.service.imp.UserMapperImpl
 */
@ExtendWith(MockitoExtension.class)
class UserMapperImplTest {

    @InjectMocks
    private UserMapperImpl userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    private final UserEntity expected = new UserEntity();
    private final Register register = new Register();

    @BeforeEach
    public void setUp() {
        expected.setId(1L);
        expected.setUsername("Serge");
        expected.setEmail("Serge@gmail.com");
        expected.setPassword("password");
        expected.setRole(RoleType.USER);

        register.setUsername("Serge");
        register.setEmail("Serge@gmail.com");
        register.setPassword("password");
        register.setRole(RoleType.USER);
    }

    /**
     * Checking <b>toUserEntity()</b> method of UserMapperImpl class<br>
     */
    @Test
    @DisplayName("Check to user entity method")
    void shouldProperlyMapRegisterToUserEntity() {

        when(passwordEncoder.encode(anyString())).thenReturn("password");
        UserEntity userEntity = userMapper.toUserEntity(register);

        assertNotNull(userEntity);
        assertEquals(register.getUsername(), userEntity.getUsername());
        assertEquals(register.getEmail(), userEntity.getEmail());
        assertEquals(register.getPassword(), userEntity.getPassword());
        assertEquals(register.getRole(), userEntity.getRole());
    }

    /**
     * Checking <b>toUser()</b> method of UserMapperImpl class<br>
     */
    @Test
    @DisplayName("Check to user method")
    void shouldProperlyMapUserEntityToUser() {

        User user = userMapper.toUser(expected);

        assertNotNull(user);
        assertEquals(expected.getUsername(), user.getUsername());
        assertEquals(expected.getEmail(), user.getEmail());
        assertEquals(expected.getPassword(), user.getPassword());
        assertEquals(expected.getRole(), user.getRole());
    }
}
