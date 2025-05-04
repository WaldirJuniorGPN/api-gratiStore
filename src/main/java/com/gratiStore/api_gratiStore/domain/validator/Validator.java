package com.gratiStore.api_gratiStore.domain.validator;

import br.com.caelum.stella.validation.CNPJValidator;
import br.com.caelum.stella.validation.CPFValidator;
import br.com.caelum.stella.validation.InvalidStateException;
import com.gratiStore.api_gratiStore.domain.entities.enus.AtrasoStatus;
import com.gratiStore.api_gratiStore.domain.exception.ValidacaoNegocioException;

import java.math.BigDecimal;

public class Validator {

    private Validator() {
        throw new UnsupportedOperationException("Classe utilitária, não deve ser instanciada");
    }

    public static void validarNome(String nome) {
        if (nome == null) {
            throw new ValidacaoNegocioException("O nome do Atendente não pode estar nulo");
        }

        if (nome.isBlank()) {
            throw new ValidacaoNegocioException("O nome do Atendente não pode estar em branco");
        }
    }

    public static void validarLoja(Object loja) {
        if (loja == null) {
            throw new ValidacaoNegocioException("A loja vinculda ao Atendente não pode estar nula");
        }
    }

    public static void validarCalculadora(Object calculadora) {
        if (calculadora == null) {
            throw new ValidacaoNegocioException("A calculadora vinculada à loja não pode ser nula");
        }
    }

    public static void validarIdLoja(Long id) {
        if (id == null) {
            throw new ValidacaoNegocioException("O id da loja vinculada ao Atenente não pode estar nulo");
        }
    }

    public static void validarValor(BigDecimal vendas) {
        if (vendas == null) {
            throw new ValidacaoNegocioException("o valor não pode ser nulo");
        }
        if (vendas.compareTo(BigDecimal.ZERO) < 0) {
            throw new ValidacaoNegocioException("O valor não pode ser negativo");
        }
    }

    public static void validarAtendimentos(Integer atendimentos) {
        if (atendimentos == null) {
            throw new ValidacaoNegocioException("A quantidade de atendimentos não pode ser nula");
        }
        if (atendimentos < 0) {
            throw new ValidacaoNegocioException("A quantidade de atendimentos não pode ser menor do que zero");
        }
    }

    public static void validarAtrasoStatus(AtrasoStatus status) {
        if (status == null) {
            throw new ValidacaoNegocioException("O status de atraso não pode ser nulo");
        }
    }

    public static void validarPercentual(double percentual) {
        if (percentual < 0) {
            throw new ValidacaoNegocioException("O percentual de gratificação precisa ser um valor maior do que zero");
        }
    }

    public static void validarCpf(String cpf) {
        var validator = new CPFValidator();
        try {
            validator.assertValid(cpf);
        } catch (InvalidStateException e) {
            throw new ValidacaoNegocioException("CPF inválido" + cpf);
        }
    }

    public static void validarCnpj(String cnpj) {
        if (cnpj == null) {
            throw new ValidacaoNegocioException("CNPJ não pode estar nulo");
        }

        var validator = new CNPJValidator();
        try {
            validator.assertValid(cnpj);
        } catch (InvalidStateException e) {
            throw new ValidacaoNegocioException("CNPJ inválido" + cnpj);
        }
    }
}
