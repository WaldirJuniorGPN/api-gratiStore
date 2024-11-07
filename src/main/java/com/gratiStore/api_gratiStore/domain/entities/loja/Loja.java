package com.gratiStore.api_gratiStore.domain.entities.loja;

import com.gratiStore.api_gratiStore.domain.entities.EntidadeBase;
import com.gratiStore.api_gratiStore.domain.entities.atendente.Atendente;
import com.gratiStore.api_gratiStore.domain.entities.calculadora.Calculadora;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.List;

@Entity(name = "Loja")
@Table(name = "lojas")
@Data
@EqualsAndHashCode(callSuper = true)
public class Loja extends EntidadeBase {

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "cnpj", nullable = false, unique = true)
    private String cnpj;

    @Column(name = "total-de-vendas")
    private BigDecimal totalVendas = new BigDecimal(BigInteger.ZERO);

    @OneToMany(mappedBy = "loja", fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<Atendente> atendentes;

    @OneToOne(fetch = FetchType.EAGER)
    @ToString.Exclude
    private Calculadora calculadora;

    public void atribuirVendas(BigDecimal valor) {
        totalVendas = totalVendas.add(valor);
        totalVendas = totalVendas.setScale(2, RoundingMode.HALF_UP);
    }
}
