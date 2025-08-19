package com.gratiStore.api_gratiStore.domain.service.horasExtras.vo;

import java.math.BigDecimal;
import java.time.Duration;

public record ResultadoPreliminar(Duration horas50, Duration horas100, BigDecimal valorAReceber50,
                                  BigDecimal valorAReceber100) {

    public static ResultadoPreliminar vazio() {
        return new ResultadoPreliminar(Duration.ZERO, Duration.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
    }

    public ResultadoPreliminar somar(ResultadoPreliminar rp) {
        return new ResultadoPreliminar(this.horas50.plus(rp.horas50),
                this.horas100.plus(rp.horas100),
                this.valorAReceber50.add(rp.valorAReceber50),
                this.valorAReceber100.add(rp.valorAReceber100));
    }
}
