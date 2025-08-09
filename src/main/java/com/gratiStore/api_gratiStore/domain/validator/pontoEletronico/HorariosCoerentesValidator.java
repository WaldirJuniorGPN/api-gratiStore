package com.gratiStore.api_gratiStore.domain.validator.pontoEletronico;

import com.gratiStore.api_gratiStore.controller.dto.request.ponto.PontoRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class HorariosCoerentesValidator implements ConstraintValidator<HorariosCoerentes, PontoRequest> {

    @Override
    public boolean isValid(PontoRequest pontoRequest, ConstraintValidatorContext context) {
        var entradaPreenchida = pontoRequest.entrada() != null;
        var inicioAlmocoPreenchido = pontoRequest.inicioAlmoco() != null;
        var fimAlmocoPreenchido = pontoRequest.fimAlmoco() != null;
        var saidaPreenchida = pontoRequest.saida() != null;

        if (!entradaPreenchida && !inicioAlmocoPreenchido && !fimAlmocoPreenchido && !saidaPreenchida) {
            return true;
        }

        if (entradaPreenchida && inicioAlmocoPreenchido && fimAlmocoPreenchido && saidaPreenchida) {
            return true;
        }

        return false;
    }
}
