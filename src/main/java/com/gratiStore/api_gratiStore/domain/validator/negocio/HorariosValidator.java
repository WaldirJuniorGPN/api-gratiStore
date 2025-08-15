package com.gratiStore.api_gratiStore.domain.validator.negocio;

import java.time.LocalTime;

public class HorariosValidator {

    public HorariosValidator() {
        throw new UnsupportedOperationException("Classe utilitária, não deve ser instanciada");
    }

    public static boolean validarHorarios(LocalTime entrada, LocalTime inicioAlmoco, LocalTime fimAlmoco, LocalTime saida) {
        var entradaPreenchida = entrada != null;
        var inicioAlmocoPreenchido = inicioAlmoco != null;
        var fimAlmocoPreenchido = fimAlmoco != null;
        var saidaPreenchida = saida != null;

        if (!entradaPreenchida && !inicioAlmocoPreenchido && !fimAlmocoPreenchido && !saidaPreenchida) {
            return true;
        }

        if (entradaPreenchida && inicioAlmocoPreenchido && fimAlmocoPreenchido && saidaPreenchida) {
            return true;
        }

        return false;
    }

}
