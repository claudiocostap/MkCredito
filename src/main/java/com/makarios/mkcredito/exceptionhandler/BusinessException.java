package com.makarios.mkcredito.exceptionhandler;

public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}
