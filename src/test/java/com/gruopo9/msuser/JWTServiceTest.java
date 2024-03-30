package com.gruopo9.msuser;

import com.gruopo9.msuser.service.impl.JWTServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class JWTServiceTest {

    private JWTServiceImpl jwtService;
    private UserDetails userDetails;

    @BeforeEach
    void setUp() {
        jwtService = new JWTServiceImpl();
        userDetails = new User("testUser", "testPassword", Collections.emptyList());
    }

    @Test
    void generateToken() {
        String token = jwtService.generateToken(userDetails);
        assertNotNull(token);
    }

    @Test
    void validateToken() {
        String token = jwtService.generateToken(userDetails);
        assertTrue(jwtService.validateToken(token, userDetails));
    }

    @Test
    void extractUserName() {
        String token = jwtService.generateToken(userDetails);
        assertEquals(userDetails.getUsername(), jwtService.extractUserName(token));
    }

    @Test
    void extractUserNameWithInvalidToken() {
        String token = jwtService.generateToken(userDetails);
        assertNotEquals("invalidUser", jwtService.extractUserName(token));
    }
}