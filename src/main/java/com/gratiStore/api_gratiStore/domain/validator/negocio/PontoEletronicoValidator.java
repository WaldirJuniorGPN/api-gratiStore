package com.gratiStore.api_gratiStore.domain.validator.negocio;

import com.gratiStore.api_gratiStore.domain.entities.atendente.Atendente;
import com.gratiStore.api_gratiStore.domain.exception.ValidacaoNegocioException;

import java.time.LocalDate;
import java.time.LocalTime;

public class PontoEletronicoValidator {

    private PontoEletronicoValidator() {
        throw new UnsupportedOperationException("Classe utilitária, não deve ser instanciada");
    }

    public static void validarPonto(LocalDate data,
                                    LocalTime entrada,
                                    LocalTime inicioAlmoco,
                                    LocalTime fimAlmoco,
                                    LocalTime saida,
                                    Atendente atendente) {

        if (data == null) {
            throw new ValidacaoNegocioException("A data não pode ser nula");
        }
        if (entrada == null) {
            throw new ValidacaoNegocioException("A hora de entrada não pode ser nula");
        }
        if (inicioAlmoco == null) {
            throw new ValidacaoNegocioException("A hora do início do almoço não pode ser nula");
        }
        if (fimAlmoco == null) {
            throw new ValidacaoNegocioException("A hora do fim do almoço não pode ser nula");
        }
        if (saida == null) {
            throw new ValidacaoNegocioException("A hora da saída não pode ser nula");
        }
        if (atendente == null) {
            throw new ValidacaoNegocioException("O atendente não pode ser nulo");
        }

        if (data.isAfter(LocalDate.now())) {
            throw new ValidacaoNegocioException("A data não pode estar no futuro");
        }
        if (inicioAlmoco.isBefore(entrada)) {
            throw new ValidacaoNegocioException("O horário de início do almoço não pode ser anterior ao horário de entrada");
        }
        if (fimAlmoco.isBefore(inicioAlmoco)) {
            throw new ValidacaoNegocioException("O horário de fim de almoço não pode ser anterior ao horário do início do almoço");
        }
        if (saida.isBefore(fimAlmoco)) {
            throw new ValidacaoNegocioException("O horário de saída não pode ser anterior ao fim do almoço");
        }
    }
}
