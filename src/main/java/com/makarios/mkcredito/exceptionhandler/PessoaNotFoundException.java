package com.makarios.mkcredito.exceptionhandler;

public class PessoaNotFoundException extends RuntimeException {
    public PessoaNotFoundException(Long id) {
        super("Pessoa com ID " + id + " não encontrada.");
    }
}
