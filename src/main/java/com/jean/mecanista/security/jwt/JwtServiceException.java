package com.jean.mecanista.security.jwt;

import org.springframework.http.HttpStatus;

public class JwtServiceException extends RuntimeException{

    private HttpStatus status;

    public JwtServiceException(String message) {
        super(message);
        this.status = HttpStatus.UNAUTHORIZED;
    }

    public JwtServiceException(String message, Throwable cause) {
        super(message, cause);
        this.status = HttpStatus.UNAUTHORIZED;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
