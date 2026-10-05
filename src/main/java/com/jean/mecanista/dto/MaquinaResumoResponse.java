package com.jean.mecanista.dto;

import com.jean.mecanista.model.Maquina;

public record MaquinaResumoResponse(Long id, String codigo, String nome) {

    public static MaquinaResumoResponse de(Maquina maquina){
        return new MaquinaResumoResponse(maquina.getId(), maquina.getCodigo(), maquina.getNome());
    }
}
