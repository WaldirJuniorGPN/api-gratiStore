package com.gratiStore.api_gratiStore.domain.entities.ponto;

import com.gratiStore.api_gratiStore.domain.entities.EntidadeBase;
import com.gratiStore.api_gratiStore.domain.entities.atendente.Atendente;
import com.gratiStore.api_gratiStore.domain.validator.PontoEletronicoValidator;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

import static com.gratiStore.api_gratiStore.domain.validator.PontoEletronicoValidator.validarPonto;

@Entity
@Table(name = "pontos_eletronicos")
@Getter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
    @JoinColumn(name = "atendente-id", nullable = false)
    private Atendente atendente;

    public PontoEletronico(LocalDate data,
                           LocalTime entrada,
                           LocalTime inicioAlmoco,
                           LocalTime fimAlmoco,
                           LocalTime saida,
                           Atendente atendente) {

        validarPonto(data, entrada, inicioAlmoco, fimAlmoco, saida, atendente);
        this.data = data;
        this.entrada = entrada;
        this.inicioAlmoco = inicioAlmoco;
        this.fimAlmoco = fimAlmoco;
        this.saida = saida;
        this.atendente = atendente;
    }
}
