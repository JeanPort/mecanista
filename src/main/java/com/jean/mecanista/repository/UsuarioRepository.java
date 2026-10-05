package com.jean.mecanista.repository;

import com.jean.mecanista.exception.RecursoNaoEncontradoException;
import com.jean.mecanista.model.Usuario;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @EntityGraph(attributePaths = {"papeis", "papeis.papel"})
    Optional<Usuario> findByEmail(String email);

    boolean existsByEmail(String email);

    default Usuario buscarOuFalhar(Long id) {
        return findById(id).orElseThrow(() -> RecursoNaoEncontradoException.usuario(id));
    }
}
