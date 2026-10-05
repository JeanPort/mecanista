package com.jean.mecanista.dto;

import com.jean.mecanista.model.Chamado;

import java.time.LocalDateTime;

public record ChamadoResponse(Long id,
                              MaquinaResumoResponse maquina,
                              UsuarioResumoResponse solicitante,
                              UsuarioResumoResponse mecanico,
                              String status,
                              String prioridade,
                              String descricao,
                              LocalDateTime criadoEm,
                              LocalDateTime atualizadoEm,
                              LocalDateTime concluidoEm) {

    public static ChamadoResponse de(Chamado chamado) {
        var maquina = MaquinaResumoResponse.de(chamado.getMaquina());
        var solicitante = UsuarioResumoResponse.de(chamado.getSolicitante());
        var mecanico = UsuarioResumoResponse.de(chamado.getMecanico());
        return new ChamadoResponse(
                chamado.getId(),
                maquina,
                solicitante,
                mecanico,
                chamado.getStatus().name(),
                chamado.getPrioridade().name(),
                chamado.getDescricao(),
                chamado.getCriadoEm(),
                chamado.getAtualizadoEm(),
                chamado.getConcluidoEm());
    }
}
