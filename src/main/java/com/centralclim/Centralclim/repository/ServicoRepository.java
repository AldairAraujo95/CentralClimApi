package com.centralclim.Centralclim.repository;

import com.centralclim.Centralclim.model.Servico;
import com.centralclim.Centralclim.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServicoRepository extends JpaRepository<Servico, Long> {
    // ✅ Busca todos os serviços de um funcionário específico
    List<Servico> findByUsuario(Usuario usuario);
}
