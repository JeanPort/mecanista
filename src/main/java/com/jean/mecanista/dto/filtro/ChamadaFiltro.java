package com.jean.mecanista.dto.filtro;

import com.jean.mecanista.model.ChamadoStatus;
import com.jean.mecanista.model.Prioridade;

public record ChamadaFiltro(ChamadoStatus status, Prioridade prioridade, Long maquinaId) {
}
