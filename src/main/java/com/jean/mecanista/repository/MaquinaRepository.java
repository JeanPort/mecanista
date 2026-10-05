package com.jean.mecanista.repository;

import com.jean.mecanista.model.Maquina;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaquinaRepository extends JpaRepository<Maquina, Long> {
}
