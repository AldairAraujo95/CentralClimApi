package com.centralclim.Centralclim.service;

import com.centralclim.Centralclim.dto.LoginRequest;
import com.centralclim.Centralclim.dto.LoginResponse;
import com.centralclim.Centralclim.model.Perfil;
import com.centralclim.Centralclim.model.Usuario;
import com.centralclim.Centralclim.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // LOGIN
    public LoginResponse autenticar(LoginRequest loginRequest) {
        Usuario usuario = usuarioRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        if (!usuario.getSenha().equals(loginRequest.getSenha())) {
            throw new RuntimeException("Senha inválida!");
        }

        return new LoginResponse(usuario.getId(), usuario.getNome(), usuario.getPerfil());
    }

    // LISTAR TODOS
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    // LISTAR SOMENTE FUNCIONÁRIOS
    public List<Usuario> listarFuncionarios() {
        return usuarioRepository.findByPerfil(Perfil.FUNCIONARIO);
    }

    // BUSCAR POR ID
    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));
    }

    // CRIAR OU ATUALIZAR
    public Usuario salvar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    // DELETAR
    public void deletarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }
}
