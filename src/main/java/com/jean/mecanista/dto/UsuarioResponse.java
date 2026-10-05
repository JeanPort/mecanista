package com.jean.mecanista.dto;

import com.jean.mecanista.model.Usuario;

public record UsuarioResponse(Long id, String nome, String email, boolean ativo) {
    public static UsuarioResponse de(Usuario usuario) {
        if (usuario == null) return null;
        return new UsuarioResponse(usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.isAtivo());
    }
}
