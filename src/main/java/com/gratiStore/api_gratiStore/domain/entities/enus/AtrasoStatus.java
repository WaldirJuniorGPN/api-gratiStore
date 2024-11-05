package com.gratiStore.api_gratiStore.domain.entities.enus;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum AtrasoStatus {
    SIM,
    NAO;

    @JsonCreator
    public static AtrasoStatus fromString(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Valor não pode ser nulo");
        }

        switch (value.trim().toLowerCase()) {
            case "sim":
                return SIM;
            case "nao":
                return NAO;
            default:
                throw new IllegalArgumentException("Valor desconhecido: " + value);
        }
    }

    @JsonValue
    public String toValue() {
        return name().toLowerCase();
    }
}
