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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ServicoService {

    @Autowired
    private ServicoRepository servicoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    /** 🔹 Cria/agenda um novo serviço */
    public Servico agendarServico(CriarServicoRequest request) {
        Cliente cliente = clienteRepository.findById(request.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado!"));

        Usuario funcionario = usuarioRepository.findById(request.getFuncionarioId())
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado!"));

        LocalDate dataAgendamento = parseData(request.getDataServico());

        Servico novoServico = new Servico();
        novoServico.setDescricao(request.getDescricao());
        novoServico.setValor(BigDecimal.valueOf(request.getValor()));
        novoServico.setDataAgendamento(dataAgendamento);
        novoServico.setStatus(StatusServico.AGENDADO);
        novoServico.setCliente(cliente);
        novoServico.setUsuario(funcionario);

        return servicoRepository.save(novoServico);
    }

    /** 🔹 Lista todos os serviços */
    public List<Servico> listarServicos() {
        return servicoRepository.findAll();
    }

    /** 🔹 Busca serviço por ID */
    public Optional<Servico> buscarPorId(Long id) {
        return servicoRepository.findById(id);
    }

    /** 🔹 Atualiza o status de um serviço */
    public Servico atualizarStatus(Long id, StatusServico novoStatus) {
        Servico servico = servicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado!"));
        servico.setStatus(novoStatus);
        return servicoRepository.save(servico);
    }

    /** 🔹 Lista serviços de um funcionário específico */
    public List<Servico> listarPorFuncionario(Long funcionarioId) {
        Usuario funcionario = usuarioRepository.findById(funcionarioId)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado!"));
        return servicoRepository.findByUsuario(funcionario);
    }

    /** 🔹 Atualiza um serviço existente */
    public Servico atualizarServico(Long id, CriarServicoRequest request) {
        Servico servicoExistente = servicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado!"));

        Cliente cliente = clienteRepository.findById(request.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado!"));

        Usuario funcionario = usuarioRepository.findById(request.getFuncionarioId())
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado!"));

        servicoExistente.setDescricao(request.getDescricao());
        servicoExistente.setValor(BigDecimal.valueOf(request.getValor()));

        LocalDate dataAgendamento = parseData(request.getDataServico());
        servicoExistente.setDataAgendamento(dataAgendamento);

        servicoExistente.setCliente(cliente);
        servicoExistente.setUsuario(funcionario);
        servicoExistente.setStatus(StatusServico.AGENDADO);

        return servicoRepository.save(servicoExistente);
    }

    /** 🔹 Deleta um serviço */
    public void deletarServico(Long id) {
        Servico servico = servicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado!"));
        servicoRepository.delete(servico);
    }

    /** 🔹 Conversão de data */
    private LocalDate parseData(String dataStr) {
        try {
            if (dataStr == null || dataStr.isBlank()) {
                throw new RuntimeException("Data não informada.");
            }

            if (dataStr.contains("/")) {
                return LocalDate.parse(dataStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            } else {
                return LocalDate.parse(dataStr);
            }
        } catch (Exception e) {
            throw new RuntimeException("Formato de data inválido: " + dataStr);
        }
    }

    /** 🔹 Lista apenas serviços agendados */
    public List<Servico> listarServicosAgendados() {
        return servicoRepository.findAll().stream()
                .filter(s -> s.getStatus() == StatusServico.AGENDADO)
                .collect(Collectors.toList());
    }

    /** 🔹 Filtra serviços por data e/ou funcionário */
    public List<Servico> filtrarServicos(String data, String funcionario) {
        List<Servico> todos = servicoRepository.findAll();

        return todos.stream()
                .filter(servico -> {
                    boolean combinaData = true;
                    boolean combinaFuncionario = true;

                    if (data != null && !data.isBlank()) {
                        try {
                            LocalDate dataFiltro = data.contains("/")
                                    ? LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                                    : LocalDate.parse(data, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                            combinaData = servico.getDataAgendamento() != null &&
                                    servico.getDataAgendamento().isEqual(dataFiltro);
                        } catch (Exception e) {
                            System.out.println("⚠️ Erro ao converter data: " + data);
                        }
                    }

                    if (funcionario != null && !funcionario.isBlank()) {
                        String nomeFuncionario = servico.getUsuario() != null
                                ? servico.getUsuario().getNome().toLowerCase()
                                : "";
                        combinaFuncionario = nomeFuncionario.contains(funcionario.toLowerCase());
                    }

                    return combinaData && combinaFuncionario;
                })
                .collect(Collectors.toList());
    }
}
