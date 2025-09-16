package com.centralclim.Centralclim.service;

import com.centralclim.Centralclim.model.Usuario;
import com.centralclim.Centralclim.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;


    public Usuario criarUsuarios(Usuario usuario){
        return usuarioRepository.save(usuario);

    }
    public List<Usuario> listarTodosUsario(){
        return usuarioRepository.findAll();
    }
    public Optional<Usuario> buscarUsuarioPorId(Long id){
        return usuarioRepository.findById(id);

    }
    public void deletarUsuario (Long id){
        usuarioRepository.deleteById(id);
    }
    public Usuario atualizaUsuario(Long id, Usuario usuarioAtualizado){
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Usuario não encontrado com o id: " + id));

        usuarioExistente.setNome(usuarioAtualizado.getNome());
        usuarioExistente.setEmail(usuarioAtualizado.getEmail());
        usuarioExistente.setRole(usuarioAtualizado.getRole());






        return usuarioRepository.save(usuarioExistente);
    }




}
