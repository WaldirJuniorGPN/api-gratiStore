package com.gratiStore.api_gratiStore.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity(name = "Loja")
@Table(name = "lojas")
@Data
@EqualsAndHashCode(callSuper = true)
public class Loja extends EntidadeBase {

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "cnpj", nullable = false)
    private String cnpj;
}
