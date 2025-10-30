package com.centralclim.Centralclim.controller;

import com.centralclim.Centralclim.model.Usuario;
import com.centralclim.Centralclim.model.Perfil;
import com.centralclim.Centralclim.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/funcionarios")
@CrossOrigin(origins = "*")
public class FuncionarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // GET - lista todos os funcionários
    @GetMapping
    public ResponseEntity<List<Usuario>> listarFuncionarios() {
        List<Usuario> funcionarios = usuarioRepository.findByPerfil(Perfil.FUNCIONARIO);
        return ResponseEntity.ok(funcionarios);
    }

    // POST - cria novo funcionário (usuário com perfil FUNCIONARIO)
    @PostMapping
    public ResponseEntity<Usuario> criarFuncionario(@RequestBody Usuario funcionario) {
        funcionario.setPerfil(Perfil.FUNCIONARIO);
        Usuario novo = usuarioRepository.save(funcionario);
        return ResponseEntity.ok(novo);
    }

    // PUT - atualiza funcionário
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizarFuncionario(@PathVariable Long id, @RequestBody Usuario funcionario) {
        return usuarioRepository.findById(id)
                .map(existing -> {
                    existing.setNome(funcionario.getNome());
                    existing.setEmail(funcionario.getEmail());
                    existing.setSenha(funcionario.getSenha());
                    existing.setPerfil(Perfil.FUNCIONARIO);
                    Usuario atualizado = usuarioRepository.save(existing);
                    return ResponseEntity.ok(atualizado);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE - deleta funcionário
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarFuncionario(@PathVariable Long id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
