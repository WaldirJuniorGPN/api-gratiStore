package com.gratiStore.api_gratiStore.domain.entities.calculadora;

import com.gratiStore.api_gratiStore.domain.entities.EntidadeBase;
import com.gratiStore.api_gratiStore.domain.entities.loja.Loja;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;

import static com.gratiStore.api_gratiStore.domain.validator.negocio.Validator.*;


@Entity(name = "Calculadora")
@Table(name = "calculadoras")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = true, exclude = "loja")
public class Calculadora extends EntidadeBase {

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "percentual-primeiro-colocado", nullable = false)
    private double percentualPrimeiroColocado;

    @Column(name = "percentual-segundo-colocado", nullable = false)
    private double percentualSegundoColocado;

    @Column(name = "percentual-terceiro-colocado", nullable = false)
    private double percentualTerceiroColocado;

    @Column(name = "percentual-demais-colocados", nullable = false)
    private double percentualDemaisColocados;

    @Column(name = "bonus-primeiro-colocado", nullable = false)
    private BigDecimal bonusPrimeiroColocado = BigDecimal.ZERO;

    @Column(name = "bonus-segundo-colocado", nullable = false)
    private BigDecimal bonusSegundoColocado = BigDecimal.ZERO;

    @Column(name = "bonus-terceiro-colocado", nullable = false)
    private BigDecimal bonusTerceiroColocado = BigDecimal.ZERO;

    @OneToOne(mappedBy = "calculadora")
    @ToString.Exclude
    private Loja loja;

    public Calculadora(String nome,
                       double percentualPrimeiroColocado,
                       double percentualSegundoColocado,
                       double percentualTerceiroColocado,
                       double percentualDemaisColocados,
                       BigDecimal bonusPrimeiroColocado,
                       BigDecimal bonusSegundoColocado,
                       BigDecimal bonusTerceiroColocado,
                       Loja loja) {

        setNome(nome);
        setPercentualPrimeiroColocado(percentualPrimeiroColocado);
        setPercentualSegundoColocado(percentualSegundoColocado);
        setPercentualTerceiroColocado(percentualTerceiroColocado);
        setPercentualDemaisColocados(percentualDemaisColocados);
        setBonusPrimeiroColocado(bonusPrimeiroColocado);
        setBonusSegundoColocado(bonusSegundoColocado);
        setBonusTerceiroColocado(bonusTerceiroColocado);
        setLoja(loja);
    }

    public void setNome(String nome) {
        validarNome(nome);
        this.nome = nome;
    }

    public void setPercentualPrimeiroColocado(double percentualPrimeiroColocado) {
        validarPercentual(percentualPrimeiroColocado);
        this.percentualPrimeiroColocado = percentualPrimeiroColocado / 100;
    }

    public void setPercentualSegundoColocado(double percentualSegundoColocado) {
        validarPercentual(percentualSegundoColocado);
        this.percentualSegundoColocado = percentualSegundoColocado / 100;
    }

    public void setPercentualTerceiroColocado(double percentualTerceiroColocado) {
        validarPercentual(percentualTerceiroColocado);
        this.percentualTerceiroColocado = percentualTerceiroColocado / 100;
    }

    public void setPercentualDemaisColocados(double percentualDemaisColocados) {
        validarPercentual(percentualDemaisColocados);
        this.percentualDemaisColocados = percentualDemaisColocados / 100;
    }

    public void setBonusPrimeiroColocado(BigDecimal bonusPrimeiroColocado) {
        validarValor(bonusPrimeiroColocado);
        this.bonusPrimeiroColocado = bonusPrimeiroColocado;
    }

    public void setBonusSegundoColocado(BigDecimal bonusSegundoColocado) {
        validarValor(bonusSegundoColocado);
        this.bonusSegundoColocado = bonusSegundoColocado;
    }

    public void setBonusTerceiroColocado(BigDecimal bonusTerceiroColocado) {
        validarValor(bonusTerceiroColocado);
        this.bonusTerceiroColocado = bonusTerceiroColocado;
    }

    public void setLoja(Loja loja) {
        validarLoja(loja);
        this.loja = loja;
    }
}
