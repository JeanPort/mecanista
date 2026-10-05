package com.jean.mecanista.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CriarUsuarioRequest(
        @NotBlank String nome,
        @NotBlank String email,
        @NotBlank String senha,
        @NotNull Long papelId) {
}
