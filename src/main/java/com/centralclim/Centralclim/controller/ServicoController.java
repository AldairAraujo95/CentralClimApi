package com.centralclim.Centralclim.controller;

import com.centralclim.Centralclim.dto.AtualizarRequest;
import com.centralclim.Centralclim.dto.CriarServicoRequest;
import com.centralclim.Centralclim.model.Servico;
import com.centralclim.Centralclim.service.ServicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
            return ResponseEntity.badRequest().build();
        }
    }


    @GetMapping
    public ResponseEntity<List<Servico>> listarServicos() {
        return ResponseEntity.ok(servicoService.listarServicos());
    }


    @GetMapping("/{id}")
    public ResponseEntity<Servico> buscarPorId(@PathVariable Long id) {
        return servicoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PatchMapping("/{id}/status")
    public ResponseEntity<Servico> atualizarStatus(
            @PathVariable Long id,
            @RequestBody AtualizarRequest request) {

        try {
            Servico servicoAtualizado = servicoService.atualizarStatus(id, request.getStatus());
            return ResponseEntity.ok(servicoAtualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Listar serviços por funcionário
    @GetMapping("/funcionario/{idFuncionario}")
    public ResponseEntity<List<Servico>> listarPorFuncionario(@PathVariable Long idFuncionario) {
        List<Servico> servicos = servicoService.listarPorFuncionario(idFuncionario);
        return ResponseEntity.ok(servicos);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        try {
            servicoService.deletarServico(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<Servico> atualizarServico(@PathVariable Long id, @RequestBody CriarServicoRequest request) {
        try {
            Servico atualizado = servicoService.atualizarServico(id, request);
            return ResponseEntity.ok(atualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
