package com.jean.mecanista.exception;

public class RecursoNaoEncontradoException extends RuntimeException {


    public RecursoNaoEncontradoException(String message) {
        super(message);
    }

    public static RecursoNaoEncontradoException usuario(Long usuarioId) {
        return new RecursoNaoEncontradoException("Usuario não encotrado: id " + usuarioId);
    }

    public static RecursoNaoEncontradoException chamado(Long chamadoId) {
        return new RecursoNaoEncontradoException("Chamado não encotrado: id " + chamadoId);
    }

    public static RecursoNaoEncontradoException papel(Long id) {
        return new RecursoNaoEncontradoException("Papel não encotrado: id " + id);
    }
}
