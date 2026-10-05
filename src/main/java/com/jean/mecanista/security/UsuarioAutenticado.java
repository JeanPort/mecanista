package com.jean.mecanista.security;

import com.jean.mecanista.model.Usuario;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UsuarioAutenticado implements UserDetails {

    private final Usuario usuario;
    private final List<GrantedAuthority> authorities;

    public UsuarioAutenticado(Usuario usuario) {
        this.usuario = usuario;
        this.authorities = AuthorityUtils.createAuthorityList(usuario.getPapeis()
                .stream()
                .map(usuarioPapel -> "ROLE_" + usuarioPapel.getPapel().getCodigo().toUpperCase())
                .toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public @Nullable String getPassword() {
        return usuario.getSenha();
    }

    @Override
    public String getUsername() {
        return usuario.getEmail();
    }

    public Long getId() {
        return usuario.getId();
    }

    @Override
    public boolean isEnabled() {
        return usuario.isAtivo();
    }
}
