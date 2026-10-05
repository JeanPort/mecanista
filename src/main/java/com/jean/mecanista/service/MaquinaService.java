package com.jean.mecanista.service;

import com.jean.mecanista.model.Maquina;
import com.jean.mecanista.repository.MaquinaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MaquinaService {

    private final MaquinaRepository maquinaRepo;

    public MaquinaService(MaquinaRepository maquinaRepo) {
        this.maquinaRepo = maquinaRepo;
    }

    @Transactional(readOnly = true)
    public List<Maquina> listar(){
        return maquinaRepo.findAll();
    }

    @Transactional(readOnly = true)
    public Maquina buscarPorId(Long id){
        return maquinaRepo.findById(id).orElse(null);
    }
}
