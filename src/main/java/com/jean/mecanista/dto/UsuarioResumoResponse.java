package com.jean.mecanista.dto;

import com.jean.mecanista.model.Usuario;

public record UsuarioResumoResponse(Long id, String nome) {

    public static UsuarioResumoResponse de(Usuario usuario){
        if (usuario == null) return null;
        return new UsuarioResumoResponse(usuario.getId(), usuario.getNome());
    }
}
