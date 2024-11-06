package com.gratiStore.api_gratiStore.domain.entities.atendente;

import com.gratiStore.api_gratiStore.domain.entities.EntidadeBase;
import com.gratiStore.api_gratiStore.domain.entities.enus.AtrasoStatus;
import com.gratiStore.api_gratiStore.domain.entities.loja.Loja;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity(name = "Atendente")
@Table(name = "atendentes")
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class Atendente extends EntidadeBase {

    @Column(name = "nome", unique = true, nullable = false)
    private String nome;

    @Column(name = "vendas-primeira-semana")
    private BigDecimal vendasPrimeiraSemana;

    @Column(name = "vendas-segunda-semana")
    private BigDecimal vendasSegundaSemana;

    @Column(name = "vendas-terceria-semana")
    private BigDecimal vendasTerceiraSemana;

    @Column(name = "vendas-quarta-semana")
    private BigDecimal vendasQuartaSemana;

    @Column(name = "vendas-quinta-semana")
    private BigDecimal vendasQuintaSemana;

    @Column(name = "vendas-sexta-semana")
    private BigDecimal vendasSextaSemana;

    @Enumerated(EnumType.STRING)
    @Column(name = "atraso-primeira-semana")
    private AtrasoStatus atrasoStatusPrimeiraSemana;

    @Enumerated(EnumType.STRING)
    @Column(name = "atraso-segunda-semana")
    private AtrasoStatus atrasoStatusSegundaSemana;

    @Enumerated(EnumType.STRING)
    @Column(name = "atraso-terceira-semana")
    private AtrasoStatus atrasoStatusTerceiraSemana;

    @Enumerated(EnumType.STRING)
    @Column(name = "atraso-quarta-semana")
    private AtrasoStatus atrasoStatusQuartaSemana;

    @Enumerated(EnumType.STRING)
    @Column(name = "atraso-quinta-semana")
    private AtrasoStatus atrasoStatusQuintaSemana;

    @Enumerated(EnumType.STRING)
    @Column(name = "atraso-sexta-semana")
    private AtrasoStatus atrasoStatusSextaSemana;

    @Column(name = "total-de-vendas")
    private BigDecimal totalVendas;

    @Column(name = "gratificacao")
    private BigDecimal gratificacao;

    @Column(name = "bonus")
    private BigDecimal bonus;

    @Column(name = "atendimentos-primeira-semana")
    private Integer quantidadeAtendimentosPrimeiraSemana;

    @Column(name = "atendimentos-segunda-semana")
    private Integer quantidadeAtendimentosSegundaSemana;

    @Column(name = "atendimentos-terceira-semana")
    private Integer quantidadeAtendimentosTerceiraSemana;

    @Column(name = "atendimentos-quarta-semana")
    private Integer quantidadeAtendimentosQuartaSemana;

    @Column(name = "atendimentos-quinta-semana")
    private Integer quantidadeAtendimentosQuintaSemana;

    @Column(name = "atendimentos-sexta-semana")
    private Integer quantidadeAtendimentosSextaSemana;

    @ManyToOne
    private Loja loja;

    public void atribuirGratificacao(BigDecimal valor) {
        gratificacao = gratificacao.add(valor);
    }

    public void atribuirBonus(BigDecimal valor) {
        bonus = bonus.add(valor);
    }

    public void atribuirVendaTotal(BigDecimal valor) {
        totalVendas = totalVendas.add(valor);
    }
}
