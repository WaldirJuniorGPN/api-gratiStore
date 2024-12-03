package com.gratiStore.api_gratiStore.domain.entities.atendente;

import com.gratiStore.api_gratiStore.domain.entities.EntidadeBase;
import com.gratiStore.api_gratiStore.domain.entities.enus.AtrasoStatus;
import com.gratiStore.api_gratiStore.domain.entities.loja.Loja;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static com.gratiStore.api_gratiStore.domain.entities.enus.AtrasoStatus.NAO;

@Entity(name = "Atendente")
@Table(name = "atendentes")
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class Atendente extends EntidadeBase {

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "vendas-primeira-semana")
    private BigDecimal vendasPrimeiraSemana = BigDecimal.ZERO;

    @Column(name = "vendas-segunda-semana")
    private BigDecimal vendasSegundaSemana = BigDecimal.ZERO;

    @Column(name = "vendas-terceria-semana")
    private BigDecimal vendasTerceiraSemana = BigDecimal.ZERO;

    @Column(name = "vendas-quarta-semana")
    private BigDecimal vendasQuartaSemana = BigDecimal.ZERO;

    @Column(name = "vendas-quinta-semana")
    private BigDecimal vendasQuintaSemana = BigDecimal.ZERO;

    @Column(name = "vendas-sexta-semana")
    private BigDecimal vendasSextaSemana = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    @Column(name = "atraso-primeira-semana")
    private AtrasoStatus atrasoStatusPrimeiraSemana = NAO;

    @Enumerated(EnumType.STRING)
    @Column(name = "atraso-segunda-semana")
    private AtrasoStatus atrasoStatusSegundaSemana = NAO;

    @Enumerated(EnumType.STRING)
    @Column(name = "atraso-terceira-semana")
    private AtrasoStatus atrasoStatusTerceiraSemana = NAO;

    @Enumerated(EnumType.STRING)
    @Column(name = "atraso-quarta-semana")
    private AtrasoStatus atrasoStatusQuartaSemana = NAO;

    @Enumerated(EnumType.STRING)
    @Column(name = "atraso-quinta-semana")
    private AtrasoStatus atrasoStatusQuintaSemana = NAO;

    @Enumerated(EnumType.STRING)
    @Column(name = "atraso-sexta-semana")
    private AtrasoStatus atrasoStatusSextaSemana = NAO;

    @Column(name = "total-de-vendas")
    private BigDecimal totalVendas = BigDecimal.ZERO;

    @Column(name = "gratificacao")
    private BigDecimal gratificacao = BigDecimal.ZERO;

    @Column(name = "bonus")
    private BigDecimal bonus = BigDecimal.ZERO;

    @Column(name = "atendimentos-primeira-semana")
    private Integer quantidadeAtendimentosPrimeiraSemana = 0;

    @Column(name = "atendimentos-segunda-semana")
    private Integer quantidadeAtendimentosSegundaSemana = 0;

    @Column(name = "atendimentos-terceira-semana")
    private Integer quantidadeAtendimentosTerceiraSemana = 0;

    @Column(name = "atendimentos-quarta-semana")
    private Integer quantidadeAtendimentosQuartaSemana = 0;

    @Column(name = "atendimentos-quinta-semana")
    private Integer quantidadeAtendimentosQuintaSemana = 0;

    @Column(name = "atendimentos-sexta-semana")
    private Integer quantidadeAtendimentosSextaSemana = 0;

    @ManyToOne
    @ToString.Exclude
    private Loja loja;

    public void atribuirGratificacao(BigDecimal valor) {
        gratificacao = gratificacao.add(valor);
        gratificacao = gratificacao.setScale(2, RoundingMode.HALF_UP);
    }

    public void atribuirBonus(BigDecimal valor) {
        bonus = bonus.add(valor);
    }

    public void atribuirVendaTotal(BigDecimal valor) {
        totalVendas = totalVendas.add(valor);
        totalVendas = totalVendas.setScale(2, RoundingMode.HALF_UP);
    }
}
