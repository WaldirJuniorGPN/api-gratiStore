package com.gratiStore.api_gratiStore.domain.entities.atendente;

import com.gratiStore.api_gratiStore.domain.entities.EntidadeBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity(name = "Atendente")
@Table(name = "atendentes")
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class Atendente extends EntidadeBase {

    @Column(name = "nome", unique = true, nullable = false)
    private String nome;
}
