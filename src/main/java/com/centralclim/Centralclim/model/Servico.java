package com.centralclim.Centralclim.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "servicos")
public class Servico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Descrição do serviço
    @Column(name = "descricao", nullable = false)
    private String descricao;

    // Data do agendamento (corrigido para aceitar formato yyyy-MM-dd vindo do app)
    @Column(name = "data_agendamento")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataAgendamento;

    // Valor do serviço
    @Column(nullable = false)
    private BigDecimal valor;

    // Funcionário (usuário) responsável pelo serviço
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    // Cliente que solicitou o serviço
    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    // Status do serviço (AGENDADO, CONCLUIDO, CANCELADO, etc.)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusServico status;
}
