package com.jean.mecanista.service;

import com.jean.mecanista.security.UsuarioAutenticado;
import com.jean.mecanista.security.jwt.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public String login(String username, String senha) {
        var autenticacao = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, senha));
        var usuarioAutenticado = (UsuarioAutenticado) autenticacao.getPrincipal();
        return jwtService.gerarToken(usuarioAutenticado);
    }
}
