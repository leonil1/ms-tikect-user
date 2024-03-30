package com.gruopo9.msuser;

import com.gruopo9.msuser.entity.Usuario;
import com.gruopo9.msuser.repository.RolRepository;
import com.gruopo9.msuser.repository.UsuarioRepository;
import com.gruopo9.msuser.service.impl.UsuarioServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class UsuarioServiceImplTest {

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private RolRepository rolRepository;

    @BeforeEach
    void setup(){
        MockitoAnnotations.initMocks(this);
    }
    @Test
    void CreateUsuarioWhenNonExistingUsername() {
        Usuario usuario = new Usuario();
        usuario.setUsername("newUser");
        when(usuarioRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        ResponseEntity<Usuario> result = usuarioService.createUsuario(usuario);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(usuario, result.getBody());
    }

    @Test
    void NotCreateUsuarioWhenExistingUsername() {
        Usuario usuario = new Usuario();
        usuario.setUsername("existingUser");
        when(usuarioRepository.findByUsername(anyString())).thenReturn(Optional.of(usuario));

        ResponseEntity<Usuario> result = usuarioService.createUsuario(usuario);

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    @Test
    void GetUsuarioByIdWhenUserExists() {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(1L);
        when(usuarioRepository.findById(anyLong())).thenReturn(Optional.of(usuario));

        ResponseEntity<Usuario> result = usuarioService.getUsuarioById(1L);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(usuario, result.getBody());
    }

    @Test
    void NotGetUsuarioByIdWhenUserDoesNotExist() {
        when(usuarioRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<Usuario> result = usuarioService.getUsuarioById(1L);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    void UpdateUsuarioWhenUserExistsAndUsernameIsUnique() {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(1L);
        usuario.setUsername("newUsername");
        when(usuarioRepository.findById(anyLong())).thenReturn(Optional.of(usuario));
        when(usuarioRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        ResponseEntity<Usuario> result = usuarioService.updateUsuario(1L, usuario);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(usuario, result.getBody());
    }

    @Test
    void NotUpdateUsuarioWhenUserDoesNotExist() {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(1L);
        when(usuarioRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<Usuario> result = usuarioService.updateUsuario(1L, usuario);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    void DeleteUsuarioWhenUserExists() {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(1L);
        when(usuarioRepository.findById(anyLong())).thenReturn(Optional.of(usuario));

        ResponseEntity<Usuario> result = usuarioService.deleteUsuario(1L);

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
    }

    @Test
    void NotDeleteUsuarioWhenUserDoesNotExist() {
        when(usuarioRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<Usuario> result = usuarioService.deleteUsuario(1L);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    void NotUpdateUsuarioWhenUserExistsAndUsernameIsNotUnique() {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(1L);
        usuario.setUsername("existingUsername");
        when(usuarioRepository.findById(anyLong())).thenReturn(Optional.of(usuario));
        when(usuarioRepository.findByUsername(anyString())).thenReturn(Optional.of(new Usuario()));

        ResponseEntity<Usuario> result = usuarioService.updateUsuario(1L, usuario);

        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void userDetailsServiceShouldReturnUserDetailsWhenUserExists() {
        String username = "existingUser";
        Usuario usuario = new Usuario();
        usuario.setUsername(username);
        when(usuarioRepository.findByUsername(anyString())).thenReturn(Optional.of(usuario));

        UserDetails result = usuarioService.userDetailsService().loadUserByUsername(username);

        assertEquals(username, result.getUsername());
    }

    @Test
    void userDetailsServiceShouldThrowExceptionWhenUserDoesNotExist() {
        String username = "nonExistingUser";
        when(usuarioRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> {
            usuarioService.userDetailsService().loadUserByUsername(username);
        });
    }

}
