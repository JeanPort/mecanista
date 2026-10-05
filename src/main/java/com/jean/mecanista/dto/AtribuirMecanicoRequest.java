package com.jean.mecanista.dto;

import jakarta.validation.constraints.NotNull;

public record AtribuirMecanicoRequest(@NotNull Long mecanicoId) {
}
