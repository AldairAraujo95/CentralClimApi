package com.centralclim.Centralclim.dto;

import com.centralclim.Centralclim.model.Servico;
import java.math.BigDecimal;
import java.time.LocalDate;

public class ServicoResponse {
    private Long id;
    private String descricao;
    private BigDecimal valor;
    private LocalDate dataAgendamento;
    private String nomeCliente;
    private String nomeFuncionario;
    private String status;

    public ServicoResponse(Servico servico) {
        this.id = servico.getId();
        this.descricao = servico.getDescricao();
        this.valor = servico.getValor();
        this.dataAgendamento = servico.getDataAgendamento();
        this.nomeCliente = servico.getCliente() != null ? servico.getCliente().getNome() : "N/A";
        // ✅ Aqui está a correção principal:
        this.nomeFuncionario = servico.getUsuario() != null ? servico.getUsuario().getNome() : "N/A";
        this.status = servico.getStatus() != null ? servico.getStatus().name() : "AGENDADO";
    }

    public Long getId() { return id; }
    public String getDescricao() { return descricao; }
    public BigDecimal getValor() { return valor; }
    public LocalDate getDataAgendamento() { return dataAgendamento; }
    public String getNomeCliente() { return nomeCliente; }
    public String getNomeFuncionario() { return nomeFuncionario; }
    public String getStatus() { return status; }
}
