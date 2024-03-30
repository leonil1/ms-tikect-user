package com.gruopo9.msuser.service;

import com.gruopo9.msuser.entity.Rol;

import java.util.Optional;

public interface RolService {
    Optional<Rol> findByNombreRol(String nombreRol);

}
