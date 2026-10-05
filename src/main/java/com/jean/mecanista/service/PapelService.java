package com.jean.mecanista.service;

import com.jean.mecanista.model.Papel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jean.mecanista.repository.PapelRepository;

import java.util.List;

@Service
public class PapelService {

    private final PapelRepository papelRepo;

    public PapelService(PapelRepository papelRepo) {
        this.papelRepo = papelRepo;
    }

    @Transactional(readOnly = true)
    public Papel buscarPorId(Long id){
        return papelRepo.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Papel> listar(){
        return papelRepo.findAll();
    }
}
