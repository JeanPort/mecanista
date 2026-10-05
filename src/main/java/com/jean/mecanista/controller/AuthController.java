package com.jean.mecanista.controller;

import com.jean.mecanista.dto.LoginRequest;
import com.jean.mecanista.dto.LoginResponse;
import com.jean.mecanista.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/logar")
    public ResponseEntity<LoginResponse> logar(@RequestBody @Valid LoginRequest request){
        var token = authService.login(request.username(), request.senha());
        return ResponseEntity.ok(new LoginResponse(token));
    }
}
