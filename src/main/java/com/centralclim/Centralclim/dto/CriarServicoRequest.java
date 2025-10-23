package com.centralclim.Centralclim.dto;

import com.centralclim.Centralclim.model.StatusServico;
import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class CriarServicoRequest {

    private String descricao;
    private BigDecimal valor;

    @JsonAlias({"dataAgendamento", "data"})
    private LocalDate dataAgendamento;

    private Long clienteId;

    @JsonAlias({"tecnicoId", "funcionarioId"})
    private Long tecnicoId;

    private StatusServico status;
}
