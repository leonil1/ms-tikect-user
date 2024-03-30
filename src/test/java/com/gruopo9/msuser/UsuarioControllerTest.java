package com.gruopo9.msuser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gruopo9.msuser.controller.UsuarioController;
import com.gruopo9.msuser.entity.Usuario;
import com.gruopo9.msuser.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UsuarioControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private UsuarioController usuarioController;

    @BeforeEach
    void setup(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(usuarioController).build();

    }

    @Test
    void ReturnOkWhenGetUsuarioById() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(1L);
        when(usuarioService.getUsuarioById(anyLong())).thenReturn(new ResponseEntity<>(usuario, HttpStatus.OK));

        mockMvc.perform(get("/ms-examen/v1/usuarios/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idUsuario", is(1)));
    }

    @Test
    void ReturnNotFoundWhenGetUsuarioById() throws Exception {
        when(usuarioService.getUsuarioById(anyLong())).thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));

        mockMvc.perform(get("/ms-examen/v1/usuarios/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    void ReturnOkWhenCreateUsuario() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setUsername("newUser");
        when(usuarioService.createUsuario(any(Usuario.class))).thenReturn(new ResponseEntity<>(usuario, HttpStatus.OK));

        mockMvc.perform(post("/ms-examen/v1/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(usuario)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is("newUser")));
    }

    @Test
    void ReturnBadRequestWhenCreateUsuario() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setUsername("existingUser");
        when(usuarioService.createUsuario(any(Usuario.class))).thenReturn(new ResponseEntity<>(HttpStatus.BAD_REQUEST));

        mockMvc.perform(post("/ms-examen/v1/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(usuario)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void ReturnOkWhenUpdateUsuario() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(1L);
        usuario.setUsername("updatedUser");
        when(usuarioService.updateUsuario(anyLong(), any(Usuario.class))).thenReturn(new ResponseEntity<>(usuario, HttpStatus.OK));

        mockMvc.perform(put("/ms-examen/v1/usuarios/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(usuario)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is("updatedUser")));
    }


    @Test
    void ReturnNotFoundWhenUpdateUsuario() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(1L);
        when(usuarioService.updateUsuario(anyLong(), any(Usuario.class))).thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));

        mockMvc.perform(put("/ms-examen/v1/usuarios/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(usuario)))
                .andExpect(status().isNotFound());
    }

    @Test
    void ReturnNoContentWhenDeleteUsuario() throws Exception {
        when(usuarioService.deleteUsuario(anyLong())).thenReturn(new ResponseEntity<>(HttpStatus.NO_CONTENT));

        mockMvc.perform(delete("/ms-examen/v1/usuarios/{id}", 1L))
                .andExpect(status().isNoContent());
    }

    @Test
    void ReturnNotFoundWhenDeleteUsuario() throws Exception {
        when(usuarioService.deleteUsuario(anyLong())).thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        mockMvc.perform(delete("/ms-examen/v1/usuarios/{id}", 1L))
                .andExpect(status().isNotFound());
    }
    @Test
    void saludoAdminShouldReturnExpectedMessage() throws Exception {
        mockMvc.perform(get("/ms-examen/v1/usuarios/rol"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hola User"));
    }

}