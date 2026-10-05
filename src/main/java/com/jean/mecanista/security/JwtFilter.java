package com.jean.mecanista.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;
import com.jean.mecanista.security.jwt.JwtService;
import com.jean.mecanista.security.jwt.JwtServiceException;

import java.io.IOException;

@Service
public class JwtFilter extends OncePerRequestFilter {

    public JwtFilter(JwtService jwtService, UsuarioAutenticadoService usuarioAutenticadoService) {
        this.jwtService = jwtService;
        this.usuarioAutenticadoService = usuarioAutenticadoService;
    }

    private final JwtService jwtService;
    private final UsuarioAutenticadoService usuarioAutenticadoService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var prefix = "Bearer ";
        var header = request.getHeader("Authorization");
        if (header != null && header.startsWith(prefix)){
            var token = header.substring(prefix.length()).trim();
            try {
                var username = jwtService.extrairSubject(token);
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null){
                    var usuarioAutenticado = ((UsuarioAutenticado) usuarioAutenticadoService.loadUserByUsername(username));
                    var autenticacao = new UsernamePasswordAuthenticationToken(usuarioAutenticado, null, usuarioAutenticado.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(autenticacao);
                }
            }catch (JwtServiceException e){
                response.setStatus(e.getStatus().value());
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                response.getWriter().write(e.getMessage());
                SecurityContextHolder.clearContext();
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}
