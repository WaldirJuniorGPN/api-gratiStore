package com.gratiStore.api_gratiStore.domain.entities.resultado;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gratiStore.api_gratiStore.domain.entities.EntidadeBase;
import com.gratiStore.api_gratiStore.domain.entities.atendente.Atendente;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;

import static com.gratiStore.api_gratiStore.domain.validator.negocio.ResultadoHoraExtraValidator.*;

@Entity
@Table(name = "resultados_horas_extras", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"atendente_id", "mes", "ano"})
})
@Getter
@EqualsAndHashCode(callSuper = true, exclude = "atendente")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResultadoHoraExtra extends EntidadeBase {

    @ManyToOne
    @JoinColumn(name = "atendente_id", nullable = false)
    @JsonIgnore
    @ToString.Exclude
    private Atendente atendente;

    @Column(name = "mes", nullable = false)
    private Integer mes;

    @Column(name = "ano", nullable = false)
    private Integer ano;

    @Column(name = "valor_a_receber_50_por_cento", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorAReceber50PorCento;

    @Column(name = "valor_a_receber_100_por_cento", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorAReceber100PorCento;

    @Column(name = "horas_extras_50_por_cento", nullable = false)
    private Duration horasExtras50PorCento;

    @Column(name = "horas_extras_100_por_cento")
    private Duration horasExtras100PorCento;

    public ResultadoHoraExtra(Atendente atendente,
                              Integer mes,
                              Integer ano,
                              BigDecimal valorAReceber50PorCento,
                              BigDecimal valorAReceber100PorCento,
                              Duration horasExtras50PorCento,
                              Duration horasExtras100PorCento) {
        validarAtendente(atendente);
        validarMes(mes);
        validarAno(ano);
        validarValorAReceber(valorAReceber50PorCento);
        validarValorAReceber(valorAReceber100PorCento);
        validarHorasExtras(horasExtras50PorCento);
        validarHorasExtras(horasExtras100PorCento);

        this.atendente = atendente;
        this.mes = mes;
        this.ano = ano;
        this.horasExtras50PorCento = horasExtras50PorCento;
        this.horasExtras100PorCento = horasExtras100PorCento;
        this.valorAReceber50PorCento = valorAReceber50PorCento.setScale(2, RoundingMode.HALF_UP);
        this.valorAReceber100PorCento = valorAReceber100PorCento.setScale(2, RoundingMode.HALF_UP);
    }

    public void atualizarResultado(BigDecimal valorAReceber50PorCento,
                                   BigDecimal valorAReceber100PorCento,
                                   Duration horasExtras50PorCento,
                                   Duration horasExtras100PorCento) {
        validarValorAReceber(valorAReceber50PorCento);
        validarValorAReceber(valorAReceber100PorCento);
        validarHorasExtras(horasExtras50PorCento);
        validarHorasExtras(horasExtras100PorCento);

        this.valorAReceber50PorCento = valorAReceber50PorCento;
        this.valorAReceber100PorCento = valorAReceber100PorCento;
        this.horasExtras50PorCento = horasExtras50PorCento;
        this.horasExtras100PorCento = horasExtras100PorCento;
    }
}
