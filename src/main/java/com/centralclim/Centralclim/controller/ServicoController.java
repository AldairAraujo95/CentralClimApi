package com.centralclim.Centralclim.controller;

import com.centralclim.Centralclim.dto.CriarServicoRequest;
import com.centralclim.Centralclim.model.Servico;
import com.centralclim.Centralclim.service.ServicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/servicos")
public class ServicoController {

    @Autowired
    private ServicoService servicoService;

    @PostMapping
    public ResponseEntity<Servico> criarServico(@RequestBody CriarServicoRequest request) {
        try {
            Servico servicoCriado = servicoService.agendarServico(request);
            return new ResponseEntity<>(servicoCriado, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            // Retorna um erro genérico se o cliente ou técnico não forem encontrados
            return ResponseEntity.badRequest().build();
        }
    }
}