package com.gratiStore.api_gratiStore.domain.validator.pontoEletronico;

import com.gratiStore.api_gratiStore.controller.dto.request.ponto.PontoRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import static com.gratiStore.api_gratiStore.domain.validator.negocio.HorariosValidator.validarHorarios;

public class HorariosCoerentesValidator implements ConstraintValidator<HorariosCoerentes, PontoRequest> {

    @Override
    public boolean isValid(PontoRequest pontoRequest, ConstraintValidatorContext context) {
        return validarHorarios(pontoRequest.entrada(),
                pontoRequest.inicioAlmoco(),
                pontoRequest.fimAlmoco(),
                pontoRequest.saida());
    }
}
