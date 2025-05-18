package com.gratiStore.api_gratiStore.domain.entities.resultado;

import com.gratiStore.api_gratiStore.domain.entities.EntidadeBase;
import com.gratiStore.api_gratiStore.domain.entities.atendente.Atendente;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static com.gratiStore.api_gratiStore.domain.validator.negocio.ResultadoHoraExtraValidator.*;

@Entity
@Table(name = "resultados_horas_extras", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"atendente_id", "mes", "ano"})
})
@Getter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResultadoHoraExtra extends EntidadeBase {

    @ManyToOne
    @JoinColumn(name = "atendente_id", nullable = false)
    private Atendente atendente;

    @Column(name = "mes", nullable = false)
    private Integer mes;

    @Column(name = "ano", nullable = false)
    private Integer ano;

    @Column(name = "horas_extras", nullable = false, precision = 5, scale = 2)
    private BigDecimal horasExtras;

    public ResultadoHoraExtra(Atendente atendente, Integer mes, Integer ano, BigDecimal horasExtras) {
        validarAtendente(atendente);
        validarMes(mes);
        validarAno(ano);
        validarHorasExtras(horasExtras);

        this.atendente = atendente;
        this.mes = mes;
        this.ano = ano;
        this.horasExtras = horasExtras.setScale(2, RoundingMode.HALF_UP);
    }
}
