package com.gratiStore.api_gratiStore.domain.entities.loja;

import com.gratiStore.api_gratiStore.domain.entities.EntidadeBase;
import com.gratiStore.api_gratiStore.domain.entities.atendente.Atendente;
import com.gratiStore.api_gratiStore.domain.entities.calculadora.Calculadora;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

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

    @OneToMany(mappedBy = "loja")
    private List<Atendente> atendentes;

    @ManyToOne
    private Calculadora calculadora;
}
