package com.gratiStore.api_gratiStore.domain.entities.Cnpj;

import jakarta.persistence.Embeddable;

import static com.gratiStore.api_gratiStore.domain.validator.Validator.validarCnpj;

@Embeddable
public class Cnpj {

    private final String cnpj;

    public Cnpj(String cnpj) {
        validarCnpj(cnpj);
        this.cnpj = cnpj.replaceAll("[^\\d]", "");
    }

    public String value() {
        return cnpj;
    }
}
