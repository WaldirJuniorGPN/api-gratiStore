package com.gratiStore.api_gratiStore.domain.entities.ponto;

import com.gratiStore.api_gratiStore.domain.entities.EntidadeBase;
import com.gratiStore.api_gratiStore.domain.entities.atendente.Atendente;
import com.gratiStore.api_gratiStore.domain.utils.*;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

import static com.gratiStore.api_gratiStore.domain.validator.negocio.PontoEletronicoValidator.validarPonto;

@Entity
@Table(name = "pontos_eletronicos", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"atendente_id", "data"})
})
@Getter
@EqualsAndHashCode(callSuper = true, exclude = "atendente")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PontoEletronico extends EntidadeBase {

    @Column(name = "data", nullable = false)
    private LocalDate data;

    @Column(name = "entrada")
    private LocalTime entrada;

    @Column(name = "inicio_almoco")
    private LocalTime inicioAlmoco;

    @Column(name = "fim_almoco")
    private LocalTime fimAlmoco;

    @Column(name = "saida")
    private LocalTime saida;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusUtils status;

    @ManyToOne
    @JoinColumn(name = "atendente_id", nullable = false)
    @ToString.Exclude
    private Atendente atendente;

    public PontoEletronico(LocalDate data,
                           LocalTime entrada,
                           LocalTime inicioAlmoco,
                           LocalTime fimAlmoco,
                           LocalTime saida,
                           StatusUtils status,
                           Atendente atendente) {

        validarPonto(data, entrada, inicioAlmoco, fimAlmoco, saida, status, atendente);
        this.data = data;
        this.entrada = entrada;
        this.inicioAlmoco = inicioAlmoco;
        this.fimAlmoco = fimAlmoco;
        this.saida = saida;
        this.status = status;
        this.atendente = atendente;
    }

    public void atualizarParametros(LocalDate data,
                                    LocalTime entrada,
                                    LocalTime inicioAlmoco,
                                    LocalTime fimAlmoco,
                                    LocalTime saida,
                                    StatusUtils status,
                                    Atendente atendente) {
        validarPonto(data, entrada, inicioAlmoco, fimAlmoco, saida, status, atendente);
        this.data = data;
        this.entrada = entrada;
        this.inicioAlmoco = inicioAlmoco;
        this.fimAlmoco = fimAlmoco;
        this.saida = saida;
        this.atendente = atendente;
        this.status = status;
    }
}
