package com.centralclim.Centralclim.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter

public class CriarServicoRequest {
    private String descricao;
    private BigDecimal valor;
    private LocalDateTime dataAgendamento;
    private Long clienteId;
    private Long tecnicoId;

}
