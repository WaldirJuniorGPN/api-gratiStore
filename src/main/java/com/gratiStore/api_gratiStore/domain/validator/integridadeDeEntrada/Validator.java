package com.gratiStore.api_gratiStore.domain.validator.integridadeDeEntrada;

import com.gratiStore.api_gratiStore.controller.dto.request.calculadora.CalculadoraRequest;
import org.springframework.data.domain.Pageable;

public class Validator {

    public Validator() {
        throw new UnsupportedOperationException("Classe utilitária, não deve ser instanciada");
    }

    public static void validarRequisicao(CalculadoraRequest request) {
        if (request == null) {
            throw new IllegalStateException("A requisição não pode ser nula");
        }
    }

    public static void validarId(Long id) {
        if (id == null) {
            throw new IllegalStateException("O ID não pode ser nulo");
        }

        if (id < 1) {
            throw new IllegalStateException("Não é permitido enviar um ID menor do que 1");
        }
    }

    public static void validarPageable(Pageable pageable) {
        if (pageable == null) {
            throw new IllegalStateException("O objeto de paginação não pode ser nulo");
        }

        if (pageable.getPageSize() > 100) {
            throw new IllegalStateException("O tamanho da página execede o limite máximo permitido (100)");
        }
    }
}
