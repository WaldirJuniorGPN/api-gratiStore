package com.gratiStore.api_gratiStore.domain.validator.negocio;

import com.gratiStore.api_gratiStore.domain.entities.atendente.Atendente;
import com.gratiStore.api_gratiStore.domain.exception.ValidacaoNegocioException;

import java.math.BigDecimal;

public class ResultadoHoraExtraValidator {

    private ResultadoHoraExtraValidator() {
        throw new UnsupportedOperationException("Classe utilitária, não deve ser instanciada");
    }

    public static void validarAtendente(Atendente atendente) {
        if (atendente == null) {
            throw new ValidacaoNegocioException("O atendente não pode estar nulo");
        }
    }

    public static void validarMes(Integer mes) {
        if (mes == null) {
            throw new ValidacaoNegocioException("O mês não pode estar nulo");
        }
        if (mes < 1 || mes > 12) {
            throw new ValidacaoNegocioException("O mês deve estar entre 1 e 12");
        }
    }

    public static void validarAno(Integer ano) {
        if (ano == null) {
            throw new ValidacaoNegocioException("O ano não pode ser nulo");
        }
        if (ano < 2000) {
            throw new ValidacaoNegocioException("O ano deve ser maior que 2000");
        }
    }

    public static void validarHorasExtras(BigDecimal horasExtras) {
        if (horasExtras == null) {
            throw new ValidacaoNegocioException("a hora extra não pode ser nula");
        }
    }
}