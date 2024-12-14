package com.gratiStore.api_gratiStore.domain.entities.marca;

import com.gratiStore.api_gratiStore.domain.entities.EntidadeBase;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity(name = "Marca")
@Table(name = "marcas")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Marca extends EntidadeBase {

    private String nome;
}
