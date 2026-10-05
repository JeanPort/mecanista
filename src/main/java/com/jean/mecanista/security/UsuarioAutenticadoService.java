package com.jean.mecanista.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.jean.mecanista.repository.UsuarioRepository;

@Service
public class UsuarioAutenticadoService implements UserDetailsService {

    private final UsuarioRepository usuarioRepo;

    public UsuarioAutenticadoService(UsuarioRepository usuarioRepo) {
        this.usuarioRepo = usuarioRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var usuario = usuarioRepo.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario não encontrado"));
        return new UsuarioAutenticado(usuario);
    }
}
