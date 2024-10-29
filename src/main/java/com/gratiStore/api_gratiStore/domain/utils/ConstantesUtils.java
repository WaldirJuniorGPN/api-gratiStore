package com.gratiStore.api_gratiStore.domain.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.NONE)
public class ConstantesUtils {

    public static String ENTIDADE_NAO_ENCONTRADA = "Entidade não encontrada";
    public static String PARAMETROS_INVALIDOS = "Parâmetros inválidos na requisição";
    public static String ERRO_INESPERADO = "Erro inesperado. Por favor, tente mais tarde.";
    public static String ERRO_VALIDACAO = "Erro de validação. Verifique os parâmetros da requisição";
    public static String VIOLACAO_INTEGRIDADE = "Violação de integridade de dados: o CNPJ já está cadastrado.";
}
