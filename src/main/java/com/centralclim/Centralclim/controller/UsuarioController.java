package com.centralclim.Centralclim.controller;

import com.centralclim.Centralclim.dto.LoginRequest;
import com.centralclim.Centralclim.dto.LoginResponse;
import com.centralclim.Centralclim.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        try {
            LoginResponse response = usuarioService.autenticar(loginRequest);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            // Retorna um erro de "Não Autorizado" se o login falhar
            return ResponseEntity.status(401).build();
        }
    }
}