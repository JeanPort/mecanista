package com.jean.mecanista.controller;

import com.jean.mecanista.model.Maquina;
import com.jean.mecanista.service.MaquinaService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/maquinas")
public class MaquinaController {

    private final MaquinaService maquinaService;

    public MaquinaController(MaquinaService maquinaService) {
        this.maquinaService = maquinaService;
    }

    @GetMapping
    public ResponseEntity<List<Maquina>> listar(){
        var maquinas = maquinaService.listar();
        return ResponseEntity.ok(maquinas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Maquina> buscarPorId(@PathVariable Long id){
        var maquina = maquinaService.buscarPorId(id);
        if (maquina == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(maquina);
    }
}
