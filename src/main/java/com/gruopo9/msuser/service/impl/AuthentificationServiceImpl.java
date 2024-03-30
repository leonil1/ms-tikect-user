package com.gruopo9.msuser.service.impl;

import com.gruopo9.msuser.entity.Rol;
import com.gruopo9.msuser.entity.Usuario;
import com.gruopo9.msuser.repository.RolRepository;
import com.gruopo9.msuser.repository.UsuarioRepository;
import com.gruopo9.msuser.request.SignInRequest;
import com.gruopo9.msuser.request.SignUpRequest;
import com.gruopo9.msuser.response.AuthenticationResponse;
import com.gruopo9.msuser.service.AuthenticationService;
import com.gruopo9.msuser.service.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthentificationServiceImpl  implements AuthenticationService {

    private final RolRepository rolRepository;
    private final AuthenticationManager authenticationManager;
    private final UsuarioRepository usuarioRepository;
    private final JWTService jwtService;

    @Override
    public Usuario getUsuarioAutenticado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            return usuarioRepository.findByUsername(username)
                    .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        }
        throw new IllegalArgumentException("Usuario no autenticado");
    }
    @Override
    public Usuario signUpUser(SignUpRequest signUpRequest) {
        Usuario usuario = new Usuario();
        usuario.setUsername(signUpRequest.getUsername());
        usuario.setEmail(signUpRequest.getEmail());
        usuario.setTelefono(signUpRequest.getTelefono());

        Optional<Rol> userRolOptional = rolRepository.findByNombreRol("USER");
        if (!userRolOptional.isPresent()) {
            throw new RuntimeException("User Role not found.");
        }
        usuario.getRoles().add(userRolOptional.get());
        usuario.setPassword(new BCryptPasswordEncoder().encode(signUpRequest.getPassword()));
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario signUpAdmin(SignUpRequest signUpRequest) {
        Usuario usuario = new Usuario();
        usuario.setUsername(signUpRequest.getUsername());
        usuario.setEmail(signUpRequest.getEmail());
        usuario.setTelefono(signUpRequest.getTelefono());

        Optional<Rol> adminRolOptional = rolRepository.findByNombreRol("ADMIN");
        if (!adminRolOptional.isPresent()) {
            throw new RuntimeException("Admin Role not found.");
        }

        usuario.getRoles().add(adminRolOptional.get());
        usuario.setPassword(new BCryptPasswordEncoder().encode(signUpRequest.getPassword()));
        return usuarioRepository.save(usuario);
    }
    @Override
    public AuthenticationResponse signin(SignInRequest signInRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                signInRequest.getUsername(),signInRequest.getPassword()));
        var user = usuarioRepository.findByUsername(signInRequest.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Useario no valido"));

        var jwt = jwtService.generateToken(user);
        AuthenticationResponse authenticationResponse =  new AuthenticationResponse();
        authenticationResponse.setToken(jwt);
        return authenticationResponse;
    }
}
