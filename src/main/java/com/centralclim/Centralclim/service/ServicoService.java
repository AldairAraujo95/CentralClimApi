package com.centralclim.Centralclim.service;

import com.centralclim.Centralclim.dto.CriarServicoRequest;
import com.centralclim.Centralclim.model.Cliente;
import com.centralclim.Centralclim.model.Servico;
import com.centralclim.Centralclim.model.StatusServico;
import com.centralclim.Centralclim.model.Usuario;
import com.centralclim.Centralclim.repository.ClienteRepository;
import com.centralclim.Centralclim.repository.ServicoRepository;
import com.centralclim.Centralclim.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServicoService {

    @Autowired
    private ServicoRepository servicoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // 🔹 Criar novo serviço
    public Servico agendarServico(CriarServicoRequest request) {
        // Busca cliente
        Cliente cliente = clienteRepository.findById(request.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado!"));

        // Busca técnico (usuário)
        Usuario tecnico = usuarioRepository.findById(request.getTecnicoId())
                .orElseThrow(() -> new RuntimeException("Técnico não encontrado!"));

        // Cria novo serviço
        Servico novoServico = new Servico();
        novoServico.setDescricao(request.getDescricao());
        novoServico.setValor(request.getValor());
        novoServico.setDataAgendamento(request.getDataAgendamento());
        novoServico.setStatus(StatusServico.AGENDADO);
        novoServico.setCliente(cliente);
        novoServico.setUsuario(tecnico);

        return servicoRepository.save(novoServico);
    }

    // 🔹 Listar todos os serviços
    public List<Servico> listarServicos() {
        return servicoRepository.findAll();
    }

    // 🔹 Buscar por ID
    public Optional<Servico> buscarPorId(Long id) {
        return servicoRepository.findById(id);
    }

    // 🔹 Atualizar apenas o status
    public Servico atualizarStatus(Long id, StatusServico novoStatus) {
        Servico servico = servicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado!"));

        servico.setStatus(novoStatus);
        return servicoRepository.save(servico);
    }

    // 🔹 Listar serviços por funcionário (usado pelo app Android)
    public List<Servico> listarPorFuncionario(Long idFuncionario) {
        return servicoRepository.findByUsuarioId(idFuncionario);
    }

    // 🔹 Atualizar um serviço completo (descrição, valor, data, cliente, técnico)
    public Servico atualizarServico(Long id, CriarServicoRequest request) {
        Servico servicoExistente = servicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado!"));

        Cliente cliente = clienteRepository.findById(request.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado!"));

        Usuario tecnico = usuarioRepository.findById(request.getTecnicoId())
                .orElseThrow(() -> new RuntimeException("Técnico não encontrado!"));

        servicoExistente.setDescricao(request.getDescricao());
        servicoExistente.setValor(request.getValor());
        servicoExistente.setDataAgendamento(request.getDataAgendamento());
        servicoExistente.setCliente(cliente);
        servicoExistente.setUsuario(tecnico);
        servicoExistente.setStatus(request.getStatus() != null ? request.getStatus() : servicoExistente.getStatus());

        return servicoRepository.save(servicoExistente);
    }

    // 🔹 Excluir serviço
    public void deletarServico(Long id) {
        Servico servico = servicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado!"));
        servicoRepository.delete(servico);
    }
}
