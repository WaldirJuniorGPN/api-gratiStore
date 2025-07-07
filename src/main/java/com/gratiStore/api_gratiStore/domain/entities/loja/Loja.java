package com.gratiStore.api_gratiStore.domain.entities.loja;

import com.gratiStore.api_gratiStore.domain.entities.EntidadeBase;
import com.gratiStore.api_gratiStore.domain.entities.atendente.Atendente;
import com.gratiStore.api_gratiStore.domain.entities.calculadora.Calculadora;
import com.gratiStore.api_gratiStore.domain.entities.cnpj.Cnpj;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static com.gratiStore.api_gratiStore.domain.validator.negocio.ResultadoHoraExtraValidator.validarAtendente;
import static com.gratiStore.api_gratiStore.domain.validator.negocio.Validator.*;

@Entity(name = "Loja")
@Table(name = "lojas")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = true)
public class Loja extends EntidadeBase {

    @Column(name = "nome", nullable = false)
    private String nome;

    @Embedded
    @Column(name = "cnpj", nullable = false, unique = true)
    private Cnpj cnpj;

    @Column(name = "total_de_vendas")
    private BigDecimal totalVendas = new BigDecimal(BigInteger.ZERO);

    @OneToMany(mappedBy = "loja", fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Atendente> atendentes = new ArrayList<>();

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "calculadora_id")
    @ToString.Exclude
    private Calculadora calculadora;

    public Loja(String nome, String cnpj) {
        setNome(nome);
        setCnpj(cnpj);
    }

    public void setNome(String nome) {
        validarNome(nome);
        this.nome = nome;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = new Cnpj(cnpj);
    }

    public void atribuirVendas(BigDecimal valor) {
        validarValor(valor);
        totalVendas = totalVendas.add(valor);
        totalVendas = totalVendas.setScale(2, RoundingMode.HALF_UP);
    }

    public void setTotalVendas(BigDecimal valor) {
        validarValor(valor);
        this.totalVendas = valor;
    }

    public void setCalculadora(Calculadora calculadora) {
        validarCalculadora(calculadora);
        this.calculadora = calculadora;
    }

    public void adicionarAtendente(Atendente atendente) {
        validarAtendente(atendente);
        atendentes.add(atendente);
    }
}
