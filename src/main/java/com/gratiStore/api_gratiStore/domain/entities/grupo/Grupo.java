package com.gratiStore.api_gratiStore.domain.entities.grupo;

import com.gratiStore.api_gratiStore.domain.entities.EntidadeBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity(name = "Grupo")
@Table(name = "grupos")
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class Grupo extends EntidadeBase {

    @Column(nullable = false, unique = true)
    private String nome;
}
