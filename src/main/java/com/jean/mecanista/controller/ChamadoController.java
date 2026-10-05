package com.jean.mecanista.controller;

import com.jean.mecanista.dto.AbrirChamadoRequest;
import com.jean.mecanista.dto.AtribuirMecanicoRequest;
import com.jean.mecanista.dto.ChamadoResponse;
import com.jean.mecanista.dto.filtro.ChamadaFiltro;
import com.jean.mecanista.security.UsuarioAutenticado;
import com.jean.mecanista.service.ChamadoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/chamados")
public class ChamadoController {

    private final ChamadoService chamadoService;

    public ChamadoController(ChamadoService chamadoService) {
        this.chamadoService = chamadoService;
    }

    @PostMapping
    public ResponseEntity<ChamadoResponse> abrir(@RequestBody @Valid AbrirChamadoRequest request, @AuthenticationPrincipal UsuarioAutenticado usuarioAutenticado){
        var chamadoResponse = chamadoService.abrirChamado(usuarioAutenticado.getId(), request);
        var uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(chamadoResponse.id())
                .toUri();
        return ResponseEntity.created(uri).body(chamadoResponse);
    }

    @PreAuthorize("hasRole('GERENTE')")
    @PostMapping("/{id}/analisar")
    public ResponseEntity<ChamadoResponse> analisar(@PathVariable Long id){
        var chamadoResponse = chamadoService.analisar(id);
        return ResponseEntity.ok(chamadoResponse);
    }

    @PreAuthorize("hasRole('GERENTE')")
    @PostMapping("/{id}/atribuir")
    public ResponseEntity<ChamadoResponse> atribuirMecanico(@PathVariable Long id, @RequestBody @Valid AtribuirMecanicoRequest request){
        var chamadoResponse = chamadoService.atribuirMecanico(id, request.mecanicoId());
        return ResponseEntity.ok(chamadoResponse);
    }

    @PreAuthorize("hasRole('MECANICO')")
    @PostMapping("/{id}/iniciar")
    public ResponseEntity<ChamadoResponse> iniciar(@PathVariable Long id, @AuthenticationPrincipal UsuarioAutenticado usuarioAutenticado){
        var chamadoResponse = chamadoService.iniciar(id, usuarioAutenticado.getId());
        return ResponseEntity.ok(chamadoResponse);
    }

    @PreAuthorize("hasRole('MECANICO')")
    @PostMapping("/{id}/concluir")
    public ResponseEntity<ChamadoResponse> concluir(@PathVariable Long id, @AuthenticationPrincipal UsuarioAutenticado usuarioAutenticado){
        var chamadoResponse = chamadoService.concluir(id, usuarioAutenticado.getId());
        return ResponseEntity.ok(chamadoResponse);
    }

    @GetMapping
    public ResponseEntity<List<ChamadoResponse>> listar(ChamadaFiltro filtro, @AuthenticationPrincipal UsuarioAutenticado usuarioAutenticado){
        var chamadas = chamadoService.listar(filtro, usuarioAutenticado.getId());
        return ResponseEntity.ok(chamadas);
    }


}
