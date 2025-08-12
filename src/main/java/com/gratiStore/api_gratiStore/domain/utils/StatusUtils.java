package com.gratiStore.api_gratiStore.domain.utils;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.text.Normalizer;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toUnmodifiableMap;

public enum StatusUtils {
    COMUM,
    FERIADO,
    ATESTADO,
    FOLGA,
    DESCONTAR_EM_HORAS;

    private static final Map<String, StatusUtils> LOOKUP =
            Stream.of(values()).collect(toUnmodifiableMap(
                    v -> normalize(v.name()), identity()
            ));

    @JsonCreator
    public static StatusUtils fromJson(String value) {
        return parse(value);
    }

    public static StatusUtils parse(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Status vazio ou nulo");
        }

        var key = normalize(value);
        var hit = LOOKUP.get(key);

        if (hit != null) return hit;

        throw new IllegalArgumentException("Status inválido: '" + value + "'.");

    }

    private static String normalize(String s) {
        var semAcentos = Normalizer.normalize(s, Normalizer.Form.NFD)
                .replace("\\p{M}", "");
        return semAcentos
                .trim()
                .toUpperCase(Locale.ROOT)
                .replaceAll("[^A-Z0-9]+", "_")
                .replaceAll("^_+|_+$", "");
    }
}
