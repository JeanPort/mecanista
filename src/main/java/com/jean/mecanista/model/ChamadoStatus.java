package com.jean.mecanista.model;

public enum ChamadoStatus {
    ABERTO,
    EM_ANALISE,
    EM_EXECUCAO,
    CONCLUIDO,
    CANCELADO;

    public boolean podeMudarPara(ChamadoStatus novoStatus) {
        return switch (this) {
            case ABERTO ->
                    novoStatus == EM_ANALISE || novoStatus == CANCELADO;

            case EM_ANALISE ->
                    novoStatus == EM_EXECUCAO || novoStatus == CANCELADO;

            case EM_EXECUCAO ->
                    novoStatus == CONCLUIDO || novoStatus == CANCELADO;

            case CONCLUIDO ->
                    false;
            case CANCELADO -> novoStatus == ABERTO;
        };
    }
}
