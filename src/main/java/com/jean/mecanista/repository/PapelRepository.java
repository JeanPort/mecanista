package com.jean.mecanista.repository;

import com.jean.mecanista.exception.RecursoNaoEncontradoException;
import com.jean.mecanista.model.Papel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PapelRepository extends JpaRepository<Papel, Long> {

    default Papel buscarOuFalhar(Long id) {
        return findById(id).orElseThrow(() -> RecursoNaoEncontradoException.papel(id));
    }
}
