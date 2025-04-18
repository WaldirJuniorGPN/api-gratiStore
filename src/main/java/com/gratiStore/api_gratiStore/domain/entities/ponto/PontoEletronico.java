package com.gratiStore.api_gratiStore.domain.entities.ponto;

import com.gratiStore.api_gratiStore.domain.entities.EntidadeBase;
import com.gratiStore.api_gratiStore.domain.entities.atendente.Atendente;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "pontos_eletrônicos")
@Getter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PontoEletronico extends EntidadeBase {

    @Column(name = "data", nullable = false)
    private LocalDate data;

    @Column(name = "entrada", nullable = false)
    private LocalTime entrada;

    @Column(name = "inicio-almoco", nullable = false)
    private LocalTime inicioAlmoco;

    @Column(name = "fim-almoco", nullable = false)
    private LocalTime fimAlmoco;

    @Column(name = "saida", nullable = false)
    private LocalTime saida;

    @ManyToOne
    @JoinColumn(name = "atendente-id")
    private Atendente atendente;
}
