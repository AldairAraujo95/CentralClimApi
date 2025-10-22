package com.centralclim.Centralclim.service;

import com.centralclim.Centralclim.model.Cliente;
import com.centralclim.Centralclim.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;


    public Cliente criarCliente(Cliente cliente) {

        if (cliente.getId() != null && cliente.getId() == 0) {
            cliente.setId(null);
        }
        return clienteRepository.save(cliente);
    }

    // Lista todos os clientes
    public List<Cliente> listarTodosClientes() {
        return clienteRepository.findAll();
    }

    // Busca cliente pelo ID
    public Optional<Cliente> buscarClientePorId(Long id) {
        return clienteRepository.findById(id);
    }

    // Deleta cliente pelo ID
    public void deletarCliente(Long id) {
        if (clienteRepository.existsById(id)) {
            clienteRepository.deleteById(id);
        } else {
            throw new RuntimeException("Cliente com ID " + id + " não encontrado.");
        }
    }
}
