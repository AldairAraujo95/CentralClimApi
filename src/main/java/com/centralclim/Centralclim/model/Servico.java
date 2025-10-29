package com.centralclim.Centralclim.model;

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

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "data_agendamento")
    private LocalDate dataAgendamento;

    private BigDecimal valor;

    @ManyToOne
    @JoinColumn(name = "usuario_id") // funcionário
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "cliente_id") // cliente
    private Cliente cliente;

    @Enumerated(EnumType.STRING)
    private StatusServico status;
}
