package com.gruopo9.msuser.service.impl;

import com.gruopo9.msuser.entity.Rol;
import com.gruopo9.msuser.repository.RolRepository;
import com.gruopo9.msuser.service.RolService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RolServiceImpl implements RolService {

    private final RolRepository rolRepository;


    @Override
    public Optional<Rol> findByNombreRol(String nombreRol) {
        return rolRepository.findByNombreRol(nombreRol);
    }
}

