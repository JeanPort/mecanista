package com.jean.mecanista.controller;

import com.jean.mecanista.dto.CriarUsuarioRequest;
import com.jean.mecanista.dto.UsuarioResponse;
import com.jean.mecanista.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PreAuthorize("hasRole('GERENTE')")
    @PostMapping
    public ResponseEntity<UsuarioResponse> criar(@RequestBody @Valid CriarUsuarioRequest request){
        var usuarioResponse = usuarioService.criar(request);
        var uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(usuarioResponse.id())
                .toUri();
        return ResponseEntity.created(uri).body(usuarioResponse);
    }
}
