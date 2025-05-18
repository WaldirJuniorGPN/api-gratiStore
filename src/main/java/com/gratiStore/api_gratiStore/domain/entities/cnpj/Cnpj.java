package com.gratiStore.api_gratiStore.domain.entities.cnpj;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.gratiStore.api_gratiStore.domain.validator.negocio.Validator.validarCnpj;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cnpj {

    private String cnpj;

    public Cnpj(String cnpj) {
        validarCnpj(cnpj);
        this.cnpj = cnpj.replaceAll("[^\\d]", "");
    }

    public String value() {
        return cnpj;
    }
}
