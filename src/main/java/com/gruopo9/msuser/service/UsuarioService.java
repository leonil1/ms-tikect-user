package com.gruopo9.msuser.service;

import com.gruopo9.msuser.entity.Usuario;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UsuarioService {
    ResponseEntity<Usuario> createUsuario(Usuario usuario);
    ResponseEntity<Usuario> getUsuarioById(Long id);
    ResponseEntity<Usuario> updateUsuario(Long id, Usuario usuario);
    ResponseEntity<Usuario> deleteUsuario(Long id);
    UserDetailsService userDetailsService();
}
