package com.gratiStore.api_gratiStore.domain.validator;

import com.gratiStore.api_gratiStore.controller.dto.request.atendente.AtendenteRequest;
import com.gratiStore.api_gratiStore.domain.entities.enus.AtrasoStatus;
import com.gratiStore.api_gratiStore.domain.exception.ValidacaoNegiocioException;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;

@UtilityClass
public class AtendenteValidator {

    public void validarCriacao(AtendenteRequest request) {
        validarNome(request.nome());
        validarIdLoja(request.lojaId());
    }

    public void validarNome(String nome) {
        if (nome == null) {
            throw new ValidacaoNegiocioException("O nome do Atendente não pode estar nulo");
        }

        if (nome.isBlank()) {
            throw new ValidacaoNegiocioException("O nome do Atendente não pode estar em branco");
        }
    }

    public void validarLoja(Object loja) {
        if (loja == null) {
            throw new ValidacaoNegiocioException("A loja vinculda ao Atendente não pode estar nula");
        }
    }

    public void validarIdLoja(Long id) {
        if (id == null) {
            throw new ValidacaoNegiocioException("O id da loja vinculada ao Atenente não pode estar nulo");
        }
    }

    public void validarValor(BigDecimal vendas) {
        if (vendas == null) {
            throw new ValidacaoNegiocioException("o valor não pode ser nulo");
        }
        if (vendas.compareTo(BigDecimal.ZERO) < 0) {
            throw new ValidacaoNegiocioException("O valor não pode ser negativo");
        }
    }

    public void validarAtendimentos(Integer atendimentos) {
        if (atendimentos == null) {
            throw new ValidacaoNegiocioException("A quantidade de atendimentos não pode ser nula");
        }
        if (atendimentos < 0) {
            throw new ValidacaoNegiocioException("A quantidade de atendimentos não pode ser menor do que zero");
        }
    }

    public void validarAtrasoStatus(AtrasoStatus status) {
        if (status == null) {
            throw new ValidacaoNegiocioException("O status de atraso não pode ser nulo");
        }
    }
}
