package com.gruopo9.msuser.controller;

import com.gruopo9.msuser.entity.Usuario;
import com.gruopo9.msuser.request.SignInRequest;
import com.gruopo9.msuser.request.SignUpRequest;
import com.gruopo9.msuser.response.AuthenticationResponse;
import com.gruopo9.msuser.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/autenticacion")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

@GetMapping("/usuarioautenticado")
public ResponseEntity<Usuario> getUsuarioAutenticado() {
    Usuario usuario = authenticationService.getUsuarioAutenticado();

    // Verificar si el usuario tiene el rol USER o ADMIN
    boolean hasUserRole = usuario.getRoles().stream().anyMatch(rol -> rol.getNombreRol().equals("USER"));
    boolean hasAdminRole = usuario.getRoles().stream().anyMatch(rol -> rol.getNombreRol().equals("ADMIN"));

    // Permitir acceso solo a usuarios con rol USER o ADMIN
    if (hasUserRole || hasAdminRole) {
        return ResponseEntity.ok(usuario);
    } else {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}

    @PostMapping("/signupuser")
    public ResponseEntity<Usuario> signUpUser(@RequestBody SignUpRequest signUpRequest){
        return ResponseEntity.ok(authenticationService.signUpUser(signUpRequest));
    }
    @PostMapping("/signupadmin")
    public ResponseEntity<Usuario> signUpAdmin(@RequestBody SignUpRequest signUpRequest){
        return ResponseEntity.ok(authenticationService.signUpAdmin(signUpRequest));
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthenticationResponse> signin(@RequestBody SignInRequest signInRequest){
        return ResponseEntity.ok(authenticationService.signin(signInRequest));
    }


}
