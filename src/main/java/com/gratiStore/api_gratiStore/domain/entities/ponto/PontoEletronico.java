package com.gratiStore.api_gratiStore.domain.entities.ponto;

import com.gratiStore.api_gratiStore.domain.entities.EntidadeBase;
import com.gratiStore.api_gratiStore.domain.entities.atendente.Atendente;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "pontos_eletrônicos")
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
    @JoinColumn(name = "atendente-id")
    private Atendente atendente;

    public PontoEletronico(LocalDate data, LocalTime entrada, LocalTime inicioAlmoco, LocalTime fimAlmoco, LocalTime saida) {
        this .setData(data)
                .setEntrada(entrada)
                .setInicioAlmoco(inicioAlmoco)
                .setFimAlmoco(fimAlmoco)
                .setSaida(saida);
    }

    private PontoEletronico setData(LocalDate data) {
        if (data == null) {
            throw new IllegalStateException("A data não pode estar nula");
        }
        this.data = data;
        return this;
    }

    private PontoEletronico setEntrada(LocalTime entrada) {
        if (entrada == null) {
            throw new IllegalStateException("A hora da entrada não pode estar nula");
        }
        this.entrada = entrada;
        return this;
    }

    private PontoEletronico setInicioAlmoco(LocalTime inicioAlmoco) {
        if (inicioAlmoco == null) {
            throw new IllegalStateException("A hora de início do almoço não pode estar nula");
        }

        if (inicioAlmoco.isBefore(this.entrada)) {
            throw new IllegalStateException("A hora de início do almoço não pode ser anterior à hora de entrada");
        }
        this.inicioAlmoco = inicioAlmoco;
        return this;
    }

    private PontoEletronico setFimAlmoco(LocalTime fimAlmoco) {
        if (fimAlmoco == null) {
            throw new IllegalStateException("A hora do fim do almoço não pode estar nula");
        }

        if (fimAlmoco.isBefore(this.inicioAlmoco)) {
            throw new IllegalStateException("A hora do fim do almoço não pode ser anterior a ao início do almoço");
        }
        this.fimAlmoco = fimAlmoco;
        return this;
    }

    private PontoEletronico setSaida(LocalTime saida) {
        if (saida == null) {
            throw new IllegalStateException("A hora de saída não pode ser nula");
        }

        if (saida.isBefore(this.fimAlmoco)) {
            throw new IllegalStateException("A hora de saída não pode ser anteior ao fim do almoço");
        }
        this.saida = saida;
        return this;
    }
}
