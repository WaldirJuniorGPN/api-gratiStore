package com.gratiStore.api_gratiStore.domain.entities.loja;

import com.gratiStore.api_gratiStore.domain.entities.EntidadeBase;
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

    @Column(name = "cnpj", nullable = false, unique = true)
    private String cnpj;
}