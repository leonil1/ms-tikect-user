package com.gruopo9.msuser;

import com.gruopo9.msuser.entity.Rol;
import com.gruopo9.msuser.entity.Usuario;
import com.gruopo9.msuser.repository.RolRepository;
import com.gruopo9.msuser.repository.UsuarioRepository;
import com.gruopo9.msuser.request.SignInRequest;
import com.gruopo9.msuser.request.SignUpRequest;
import com.gruopo9.msuser.response.AuthenticationResponse;
import com.gruopo9.msuser.service.JWTService;
import com.gruopo9.msuser.service.impl.AuthentificationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class AuthenticationServiceTest {

    @InjectMocks
    private AuthentificationServiceImpl authenticationService;

    @Mock
    private RolRepository rolRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private JWTService jwtService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void signUpUserCreatesNewUser() {
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setUsername("testUser");
        signUpRequest.setEmail("testEmail");
        signUpRequest.setTelefono("testPhone");
        signUpRequest.setPassword("testPassword");

        Rol userRole = new Rol();
        userRole.setNombreRol("USER");
        when(rolRepository.findByNombreRol("USER")).thenReturn(Optional.of(userRole));

        Usuario expectedUser = new Usuario();
        expectedUser.setUsername("testUser");
        expectedUser.setEmail("testEmail");
        expectedUser.setTelefono("testPhone");
        expectedUser.getRoles().add(userRole);
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(expectedUser);

        Usuario actualUser = authenticationService.signUpUser(signUpRequest);

        assertEquals(expectedUser, actualUser);
    }

    @Test
    public void signUpAdminCreatesNewAdmin() {
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setUsername("testAdmin");
        signUpRequest.setEmail("testEmail");
        signUpRequest.setTelefono("testPhone");
        signUpRequest.setPassword("testPassword");

        Rol adminRole = new Rol();
        adminRole.setNombreRol("ADMIN");
        when(rolRepository.findByNombreRol("ADMIN")).thenReturn(Optional.of(adminRole));

        Usuario expectedUser = new Usuario();
        expectedUser.setUsername("testAdmin");
        expectedUser.setEmail("testEmail");
        expectedUser.setTelefono("testPhone");
        expectedUser.getRoles().add(adminRole);
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(expectedUser);

        Usuario actualUser = authenticationService.signUpAdmin(signUpRequest);

        assertEquals(expectedUser, actualUser);
    }

    @Test
    public void signInAuthenticatesUser() {
        SignInRequest signInRequest = new SignInRequest();
        signInRequest.setUsername("testUser");
        signInRequest.setPassword("testPassword");

        Usuario user = new Usuario();
        user.setUsername("testUser");
        when(usuarioRepository.findByUsername("testUser")).thenReturn(Optional.of(user));

        String expectedToken = "testToken";
        when(jwtService.generateToken(user)).thenReturn(expectedToken);

        AuthenticationResponse expectedResponse = new AuthenticationResponse();
        expectedResponse.setToken(expectedToken);

        AuthenticationResponse actualResponse = authenticationService.signin(signInRequest);

        assertEquals(expectedResponse.getToken(), actualResponse.getToken());
    }
}
