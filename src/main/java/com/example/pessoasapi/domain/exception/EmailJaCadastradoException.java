package com.example.pessoasapi.domain.exception;

public class EmailJaCadastradoException extends RuntimeException {

    public EmailJaCadastradoException(String email) {
        super("E-mail ja cadastrado: " + email);
    }
}
