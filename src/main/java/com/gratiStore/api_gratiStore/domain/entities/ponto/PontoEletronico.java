package com.gratiStore.api_gratiStore.domain.entities.ponto;

import com.gratiStore.api_gratiStore.domain.entities.EntidadeBase;
import com.gratiStore.api_gratiStore.domain.entities.atendente.Atendente;
import com.gratiStore.api_gratiStore.domain.utils.AtestadoUtils;
import com.gratiStore.api_gratiStore.domain.utils.DescontarEmHorasUtils;
import com.gratiStore.api_gratiStore.domain.utils.FeriadoUtils;
import com.gratiStore.api_gratiStore.domain.utils.FolgaUtils;
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

    @Column(name = "status_feriado", nullable = false)
    @Enumerated(EnumType.STRING)
    private FeriadoUtils feriado;

    @Column(name = "status_atestado", nullable = false)
    @Enumerated(EnumType.STRING)
    private AtestadoUtils atestado;

    @Column(name = "status_folga", nullable = false)
    @Enumerated(EnumType.STRING)
    private FolgaUtils folga;

    @Column(name = "status_descontar_em_horas", nullable = false)
    @Enumerated(EnumType.STRING)
    private DescontarEmHorasUtils descontarEmHoras;

    @ManyToOne
    @JoinColumn(name = "atendente_id", nullable = false)
    @ToString.Exclude
    private Atendente atendente;

    public PontoEletronico(LocalDate data,
                           LocalTime entrada,
                           LocalTime inicioAlmoco,
                           LocalTime fimAlmoco,
                           LocalTime saida,
                           FeriadoUtils feriado,
                           AtestadoUtils atestado,
                           FolgaUtils folga,
                           DescontarEmHorasUtils descontarEmHoras,
                           Atendente atendente) {

        validarPonto(data, entrada, inicioAlmoco, fimAlmoco, saida, feriado, atestado, folga, descontarEmHoras, atendente);
        this.data = data;
        this.entrada = entrada;
        this.inicioAlmoco = inicioAlmoco;
        this.fimAlmoco = fimAlmoco;
        this.saida = saida;
        this.feriado = feriado;
        this.atestado = atestado;
        this.folga = folga;
        this.descontarEmHoras = descontarEmHoras;
        this.atendente = atendente;
    }

    public void atualizarParametros(LocalDate data,
                                    LocalTime entrada,
                                    LocalTime inicioAlmoco,
                                    LocalTime fimAlmoco,
                                    LocalTime saida,
                                    FeriadoUtils feriado,
                                    AtestadoUtils atestado,
                                    FolgaUtils folga,
                                    DescontarEmHorasUtils descontarEmHoras,
                                    Atendente atendente) {
        validarPonto(data, entrada, inicioAlmoco, fimAlmoco, saida, feriado, atestado, folga, descontarEmHoras, atendente);
        this.data = data;
        this.entrada = entrada;
        this.inicioAlmoco = inicioAlmoco;
        this.fimAlmoco = fimAlmoco;
        this.saida = saida;
        this.atendente = atendente;
        this.feriado = feriado;
        this.atestado = atestado;
        this.folga = folga;
        this.descontarEmHoras = descontarEmHoras;
    }
}
