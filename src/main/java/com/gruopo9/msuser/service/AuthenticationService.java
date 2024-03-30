package com.gruopo9.msuser.service;

import com.gruopo9.msuser.entity.Usuario;
import com.gruopo9.msuser.request.SignInRequest;
import com.gruopo9.msuser.request.SignUpRequest;
import com.gruopo9.msuser.response.AuthenticationResponse;

public interface AuthenticationService {
    Usuario getUsuarioAutenticado();

    Usuario signUpUser(SignUpRequest signUpRequest);
    Usuario signUpAdmin(SignUpRequest signUpRequest);
    AuthenticationResponse signin(SignInRequest signInRequest);
}
