package com.makarios.mkcredito.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RegistroDePontoException extends RuntimeException {
    public RegistroDePontoException(String message) {
        super(message);
    }
}
