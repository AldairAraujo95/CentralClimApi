package com.centralclim.Centralclim.controller;

import com.centralclim.Centralclim.dto.CriarServicoRequest;
import com.centralclim.Centralclim.dto.AtualizarRequest;
import com.centralclim.Centralclim.dto.ServicoResponse;
import com.centralclim.Centralclim.model.Servico;
import com.centralclim.Centralclim.model.StatusServico;
import com.centralclim.Centralclim.service.ServicoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/servicos")
@CrossOrigin(origins = "*")
public class ServicoController {

    @Autowired
    private ServicoService servicoService;

    /** 🔹 Criar serviço */
    @PostMapping
    public ResponseEntity<Servico> criarServico(@RequestBody CriarServicoRequest request) {
        try {
            Servico servicoCriado = servicoService.agendarServico(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(servicoCriado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /** 🔹 Listar todos os serviços */
    @GetMapping
    public ResponseEntity<List<ServicoResponse>> listarServicos() {
        List<ServicoResponse> resposta = servicoService.listarServicos()
                .stream()
                .map(ServicoResponse::new)
                .toList();
        return ResponseEntity.ok(resposta);
    }

    /** 🔹 Buscar serviço por ID */
    @GetMapping("/{id}")
    public ResponseEntity<ServicoResponse> buscarPorId(@PathVariable Long id) {
        return servicoService.buscarPorId(id)
                .map(servico -> ResponseEntity.ok(new ServicoResponse(servico)))
                .orElse(ResponseEntity.notFound().build());
    }

    /** 🔹 Atualizar status */
    @PatchMapping("/{id}/status")
    public ResponseEntity<Servico> atualizarStatus(@PathVariable Long id, @RequestBody AtualizarRequest request) {
        try {
            Servico atualizado = servicoService.atualizarStatus(id, request.getStatus());
            return ResponseEntity.ok(atualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /** 🔹 Listar serviços por funcionário */
    @GetMapping("/funcionario/{idFuncionario}")
    public ResponseEntity<List<ServicoResponse>> listarPorFuncionario(@PathVariable Long idFuncionario) {
        try {
            List<ServicoResponse> resposta = servicoService.listarPorFuncionario(idFuncionario)
                    .stream()
                    .map(ServicoResponse::new)
                    .toList();
            return ResponseEntity.ok(resposta);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /** 🔹 Atualizar serviço existente */
    @PutMapping("/{id}")
    public ResponseEntity<Servico> atualizarServico(
            @PathVariable Long id,
            @RequestBody CriarServicoRequest request) {

        try {
            Servico atualizado = servicoService.atualizarServico(id, request);
            return ResponseEntity.ok(atualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /** 🔹 Deletar serviço */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarServico(@PathVariable Long id) {
        try {
            servicoService.deletarServico(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /** 🔹 Listar apenas serviços agendados */
    @GetMapping("/agendados")
    public ResponseEntity<List<ServicoResponse>> listarServicosAgendados() {
        List<ServicoResponse> resposta = servicoService.listarServicosAgendados()
                .stream()
                .map(ServicoResponse::new)
                .toList();
        return ResponseEntity.ok(resposta);
    }

    /** 🔹 Filtro (por data e/ou funcionário) */
    @GetMapping("/filtro")
    public ResponseEntity<List<ServicoResponse>> filtrarServicos(
            @RequestParam(required = false) String data,
            @RequestParam(required = false) String funcionario) {

        List<ServicoResponse> resposta = servicoService.filtrarServicos(data, funcionario)
                .stream()
                .map(ServicoResponse::new)
                .toList();

        return ResponseEntity.ok(resposta);
    }
}
