package com.jean.mecanista.repository;

import com.jean.mecanista.exception.RecursoNaoEncontradoException;
import com.jean.mecanista.model.Chamado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ChamadoRepository extends JpaRepository<Chamado, Long>, JpaSpecificationExecutor<Chamado> {

    default Chamado buscarOuFalhar(Long id) {
        return findById(id).orElseThrow(() -> RecursoNaoEncontradoException.usuario(id));
    }
}
