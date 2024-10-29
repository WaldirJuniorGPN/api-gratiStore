package com.gratiStore.api_gratiStore.domain.exception;

import java.util.ArrayList;
import java.util.List;

public class ValidateError extends StandardError {

    private List<ValidateMessage> mensagens = new ArrayList<>();

    public void addMensagens(String campo, String msg) {
        this.mensagens.add(new ValidateMessage(campo, msg));
    }
}
