package com.centralclim.Centralclim.service;

import com.centralclim.Centralclim.model.Agendamento;
import com.centralclim.Centralclim.repository.AgendamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AgendamentoService {

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    public Agendamento criarAgendamento(Agendamento agendamento) {
        if (agendamento.getId() != null && agendamento.getId() == 0) {
            agendamento.setId(null);
        }
        return agendamentoRepository.save(agendamento);
    }

    public List<Agendamento> listarTodos() {
        return agendamentoRepository.findAll();
    }

    public Optional<Agendamento> buscarPorId(Long id) {
        return agendamentoRepository.findById(id);
    }

    public Agendamento atualizar(Long id, Agendamento agendamentoAtualizado) {
        return agendamentoRepository.findById(id).map(agendamento -> {
            agendamento.setClienteId(agendamentoAtualizado.getClienteId());
            agendamento.setFuncionarioId(agendamentoAtualizado.getFuncionarioId());
            agendamento.setDescricao(agendamentoAtualizado.getDescricao());
            agendamento.setValor(agendamentoAtualizado.getValor());
            agendamento.setDataServico(agendamentoAtualizado.getDataServico());
            return agendamentoRepository.save(agendamento);
        }).orElseThrow(() -> new RuntimeException("Agendamento não encontrado com ID: " + id));
    }

    public void deletar(Long id) {
        if (agendamentoRepository.existsById(id)) {
            agendamentoRepository.deleteById(id);
        } else {
            throw new RuntimeException("Agendamento não encontrado com ID: " + id);
        }
    }
}
