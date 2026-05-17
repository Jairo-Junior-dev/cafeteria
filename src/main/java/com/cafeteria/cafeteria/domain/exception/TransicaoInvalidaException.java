package com.cafeteria.cafeteria.domain.exception;

import com.cafeteria.cafeteria.domain.model.StatusPedido;
import jdk.jshell.Snippet;

public class TransicaoInvalidaException extends DomainException{
    public TransicaoInvalidaException(StatusPedido atual, StatusPedido novo){
        super("Transição invalida %s -> $s".formatted(atual, novo));
    }
}
