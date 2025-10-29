package com.centralclim.Centralclim.dto;

public class CriarServicoRequest {

    private Long clienteId;
    private Long funcionarioId;
    private String descricao;
    private Double valor;
    private String dataServico; // ⚠️ o app Android envia esse nome

    // Getters e Setters
    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }

    public Long getFuncionarioId() { return funcionarioId; }
    public void setFuncionarioId(Long funcionarioId) { this.funcionarioId = funcionarioId; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public Double getValor() { return valor; }
    public void setValor(Double valor) { this.valor = valor; }

    public String getDataServico() { return dataServico; } // ✅ adiciona o getter certo
    public void setDataServico(String dataServico) { this.dataServico = dataServico; }
}
