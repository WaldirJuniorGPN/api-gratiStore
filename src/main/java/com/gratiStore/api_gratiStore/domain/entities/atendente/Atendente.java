package com.gratiStore.api_gratiStore.domain.entities.atendente;

import com.gratiStore.api_gratiStore.domain.entities.EntidadeBase;
import com.gratiStore.api_gratiStore.domain.entities.enus.AtrasoStatus;
import com.gratiStore.api_gratiStore.domain.entities.loja.Loja;
import com.gratiStore.api_gratiStore.domain.entities.ponto.PontoEletronico;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static com.gratiStore.api_gratiStore.domain.entities.enus.AtrasoStatus.NAO;
import static com.gratiStore.api_gratiStore.domain.validator.Validator.*;

@Entity(name = "Atendente")
@Table(name = "atendentes")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
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

    @Column(name = "salario")
    private BigDecimal salario = BigDecimal.ZERO;

    @OneToMany(mappedBy = "atendente")
    private List<PontoEletronico> pontos;

    @ManyToOne
    @ToString.Exclude
    private Loja loja;

    public Atendente(String nome, Loja loja) {
        validarNome(nome);
        validarLoja(loja);
        this.nome = nome;
        this.loja = loja;
    }

    public void setNome(String nome) {
        validarNome(nome);
        this.nome = nome;
    }

    public void setLoja(Loja loja) {
        validarLoja(loja);
        this.loja = loja;
    }

    public void setVendasPrimeiraSemana(BigDecimal vendasPrimeiraSemana) {
        validarValor(vendasPrimeiraSemana);
        this.vendasPrimeiraSemana = vendasPrimeiraSemana;
    }

    public void setVendasSegundaSemana(BigDecimal vendasSegundaSemana) {
        validarValor(vendasSegundaSemana);
        this.vendasSegundaSemana = vendasSegundaSemana;
    }

    public void setVendasTerceiraSemana(BigDecimal vendasTerceiraSemana) {
        validarValor(vendasTerceiraSemana);
        this.vendasTerceiraSemana = vendasTerceiraSemana;
    }

    public void setVendasQuartaSemana(BigDecimal vendasQuartaSemana) {
        validarValor(vendasQuartaSemana);
        this.vendasQuartaSemana = vendasQuartaSemana;
    }

    public void setVendasQuintaSemana(BigDecimal vendasQuintaSemana) {
        validarValor(vendasQuintaSemana);
        this.vendasQuintaSemana = vendasQuintaSemana;
    }

    public void setVendasSextaSemana(BigDecimal vendasSextaSemana) {
        validarValor(vendasSextaSemana);
        this.vendasSextaSemana = vendasSextaSemana;
    }

    public void setQuantidadeAtendimentosPrimeiraSemana(Integer atendimentosPrimeiraSemana) {
        validarAtendimentos(atendimentosPrimeiraSemana);
        this.quantidadeAtendimentosPrimeiraSemana = atendimentosPrimeiraSemana;
    }

    public void setQuantidadeAtendimentosSegundaSemana(Integer atendimentosSegundaSemana) {
        validarAtendimentos(atendimentosSegundaSemana);
        this.quantidadeAtendimentosSegundaSemana = atendimentosSegundaSemana;
    }

    public void setQuantidadeAtendimentosTerceiraSemana(Integer atendimentosTerceiraSemana) {
        validarAtendimentos(atendimentosTerceiraSemana);
        this.quantidadeAtendimentosTerceiraSemana = atendimentosTerceiraSemana;
    }

    public void setQuantidadeAtendimentosQuartaSemana(Integer atendimentosQuartaSemana) {
        validarAtendimentos(atendimentosQuartaSemana);
        this.quantidadeAtendimentosQuartaSemana = atendimentosQuartaSemana;
    }

    public void setQuantidadeAtendimentosQuintaSemana(Integer atendimentosQuintaSemana) {
        validarAtendimentos(atendimentosQuintaSemana);
        this.quantidadeAtendimentosQuintaSemana = atendimentosQuintaSemana;
    }

    public void setQuantidadeAtendimentosSextaSemana(Integer atendimentosSextaSemana) {
        validarAtendimentos(atendimentosSextaSemana);
        this.quantidadeAtendimentosSextaSemana = atendimentosSextaSemana;
    }

    public void setAtrasoStatusPrimeiraSemana(AtrasoStatus status) {
        validarAtrasoStatus(status);
        this.atrasoStatusPrimeiraSemana = status;
    }

    public void setAtrasoStatusSegundaSemana(AtrasoStatus status) {
        validarAtrasoStatus(status);
        this.atrasoStatusSegundaSemana = status;
    }

    public void setAtrasoStatusTerceiraSemana(AtrasoStatus status) {
        validarAtrasoStatus(status);
        this.atrasoStatusTerceiraSemana = status;
    }

    public void setAtrasoStatusQuartaSemana(AtrasoStatus status) {
        validarAtrasoStatus(status);
        this.atrasoStatusQuartaSemana = status;
    }

    public void setAtrasoStatusQuintaSemana(AtrasoStatus status) {
        validarAtrasoStatus(status);
        this.atrasoStatusQuintaSemana = status;
    }

    public void setAtrasoStatusSextaSemana(AtrasoStatus status) {
        validarAtrasoStatus(status);
        this.atrasoStatusSextaSemana = status;
    }

    public void setBonus(BigDecimal bonus) {
        validarValor(bonus);
        this.bonus = bonus;
    }

    public void setGratificacao(BigDecimal gratificacao) {
        validarValor(gratificacao);
        this.gratificacao = gratificacao;
    }

    public void setTotalVendas(BigDecimal totalVendas) {
        validarValor(totalVendas);
        this.totalVendas = totalVendas;
    }

    public void atribuirGratificacao(BigDecimal valor) {
        validarValor(valor);
        gratificacao = gratificacao.add(valor);
        gratificacao = gratificacao.setScale(2, RoundingMode.HALF_UP);
    }

    public void atribuirBonus(BigDecimal valor) {
        validarValor(valor);
        bonus = bonus.add(valor);
    }

    public void atribuirVendaTotal(BigDecimal valor) {
        validarValor(valor);
        totalVendas = totalVendas.add(valor);
        totalVendas = totalVendas.setScale(2, RoundingMode.HALF_UP);
    }
}
