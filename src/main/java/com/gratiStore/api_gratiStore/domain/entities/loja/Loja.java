package com.gratiStore.api_gratiStore.domain.entities.loja;

import com.gratiStore.api_gratiStore.domain.entities.EntidadeBase;
import com.gratiStore.api_gratiStore.domain.entities.atendente.Atendente;
import com.gratiStore.api_gratiStore.domain.entities.calculadora.Calculadora;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
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
    private BigDecimal totalVendas;

    @OneToMany(mappedBy = "loja", fetch = FetchType.EAGER)
    private List<Atendente> atendentes;

    @OneToOne(fetch = FetchType.EAGER)
    private Calculadora calculadora;

    public void atribuirVendas(BigDecimal valor) {
        totalVendas = totalVendas.add(valor);
    }
}
