package com.gruopo9.msuser;

import com.gruopo9.msuser.controller.AuthenticationController;
import com.gruopo9.msuser.entity.Usuario;
import com.gruopo9.msuser.request.SignInRequest;
import com.gruopo9.msuser.request.SignUpRequest;
import com.gruopo9.msuser.response.AuthenticationResponse;
import com.gruopo9.msuser.service.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class AuthenticationControllerTest {


    @InjectMocks
    private AuthenticationController authenticationController;

    @Mock
    private AuthenticationService authenticationService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void signUpUserReturnsUsuario() {
        SignUpRequest signUpRequest = new SignUpRequest();
        Usuario usuario = new Usuario();
        when(authenticationService.signUpUser(signUpRequest)).thenReturn(usuario);

        ResponseEntity<Usuario> response = authenticationController.signUpUser(signUpRequest);

        assertEquals(usuario, response.getBody());
    }

    @Test
    public void signUpAdminReturnsUsuario() {
        SignUpRequest signUpRequest = new SignUpRequest();
        Usuario usuario = new Usuario();
        when(authenticationService.signUpAdmin(signUpRequest)).thenReturn(usuario);

        ResponseEntity<Usuario> response = authenticationController.signUpAdmin(signUpRequest);

        assertEquals(usuario, response.getBody());
    }

    @Test
    public void signInReturnsAuthenticationResponse() {
        SignInRequest signInRequest = new SignInRequest();
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        when(authenticationService.signin(signInRequest)).thenReturn(authenticationResponse);

        ResponseEntity<AuthenticationResponse> response = authenticationController.signin(signInRequest);

        assertEquals(authenticationResponse, response.getBody());
    }
}
