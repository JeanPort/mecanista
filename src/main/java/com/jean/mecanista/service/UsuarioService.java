package com.jean.mecanista.service;

import com.jean.mecanista.dto.CriarUsuarioRequest;
import com.jean.mecanista.dto.UsuarioResponse;
import com.jean.mecanista.model.Usuario;
import com.jean.mecanista.repository.PapelRepository;
import com.jean.mecanista.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {

    private final PasswordEncoder encoder;
    private final UsuarioRepository usuarioRepository;
    private final PapelRepository papelRepository;

    public UsuarioService(PasswordEncoder encoder, UsuarioRepository usuarioRepository, PapelRepository papelRepository) {
        this.encoder = encoder;
        this.usuarioRepository = usuarioRepository;
        this.papelRepository = papelRepository;
    }

    @Transactional
    public UsuarioResponse criar(CriarUsuarioRequest request){
        var papel = papelRepository.buscarOuFalhar(request.papelId());
        var existeEmail = usuarioRepository.existsByEmail(request.email());
        if (existeEmail){
            throw new IllegalArgumentException("Email em uso");
        }
        var senhaCriptografada = encoder.encode(request.senha());
        var usuario = Usuario.criar(request.nome(), senhaCriptografada, request.email(), papel);

        usuarioRepository.save(usuario);
        return UsuarioResponse.de(usuario);
    }
}
