package com.jean.mecanista.controller;

import com.jean.mecanista.model.Papel;
import com.jean.mecanista.service.PapelService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/papeis")
public class PapelController {

    private final PapelService papelService;

    public PapelController(PapelService papelService) {
        this.papelService = papelService;
    }

    @GetMapping
    public ResponseEntity<List<Papel>> listar(){
        var papeis = papelService.listar();
        return ResponseEntity.ok(papeis);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Papel> buscarPorId(@PathVariable Long id){
        var papel = papelService.buscarPorId(id);
        if (papel == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(papel);
    }
}
