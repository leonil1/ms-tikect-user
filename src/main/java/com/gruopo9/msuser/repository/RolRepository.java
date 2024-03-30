package com.gruopo9.msuser.repository;

import com.gruopo9.msuser.entity.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolRepository extends JpaRepository<Rol,Long> {
    Optional<Rol> findByNombreRol(String nombreRol);

}
