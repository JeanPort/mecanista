package com.jean.mecanista.dto;

import com.jean.mecanista.model.Prioridade;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AbrirChamadoRequest(@NotNull Long maquinaId,Prioridade prioridade,@NotBlank String descricao) {
}

