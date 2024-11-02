package com.gratiStore.api_gratiStore.domain.entities.calculadora;

import com.gratiStore.api_gratiStore.domain.entities.EntidadeBase;
import com.gratiStore.api_gratiStore.domain.entities.loja.Loja;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;

@Entity(name = "Calculadora")
@Table(name = "calculadoras")
@Data
@EqualsAndHashCode(callSuper = true)
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
    private BigDecimal bonusPrimeiroColocado;

    @Column(name = "bonus-segundo-colocado", nullable = false)
    private BigDecimal bonusSegundoColocado;

    @Column(name = "bonus-terceiro-colocado", nullable = false)
    private BigDecimal bonusTerceiroColocado;

    @OneToMany(mappedBy = "loja")
    private List<Loja> lojas;
}
